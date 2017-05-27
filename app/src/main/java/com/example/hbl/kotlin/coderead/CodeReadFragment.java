package com.example.hbl.kotlin.coderead;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hbl.kotlin.R;
import com.example.hbl.kotlin.coderead.loader.CodeFragmentContentLoader;
import com.example.hbl.kotlin.coderead.loader.ILoadHelper;
import com.example.hbl.kotlin.utils.BrushMap;
import com.example.hbl.kotlin.utils.ColorUtils;
import com.example.hbl.kotlin.utils.DeviceUtils;
import com.example.hbl.kotlin.utils.FileTypeUtils;
import com.example.hbl.kotlin.utils.HtmlParser;
import com.todou.markdownj.MarkdownProcessor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CodeReadFragment extends BaseFullscreenFragment implements NestedScrollWebView.ScrollChangeListener {
    private static final String TAG = "CodeReadFragment";

    @BindView(R.id.web_code_read)
    NestedScrollWebView mWebCodeRead;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private DirectoryNode mNode;
    private DirectoryNode mRootNode;
    private Disposable scrollFinishDelaySubscription;
    private boolean mScrollDown = false;
    private boolean mOpenFileAfterLoadFinish = false;
    private ILoadHelper mCodeContentLoader;

    private boolean mOrientationChange;

    public static CodeReadFragment newInstance(DirectoryNode node, DirectoryNode root) {
        CodeReadFragment codeReadFragment = new CodeReadFragment();
        codeReadFragment.mNode = node;
        codeReadFragment.mRootNode = root;
        return codeReadFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code_read, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCodeContentLoader = new CodeFragmentContentLoader(view);

        setupToolbar();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(getContext()
                , R.drawable.ic_view_list_white));
        mWebCodeRead.setScrollChangeListener(this);
        mWebCodeRead.getSettings().setJavaScriptEnabled(true);
        mWebCodeRead.getSettings().setSupportZoom(true);
        mWebCodeRead.getSettings().setBuiltInZoomControls(true);
        mWebCodeRead.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mCodeContentLoader.showContent();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Navigator.startWebActivity(getContext(), url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Navigator.startWebActivity(getContext(), String.valueOf(request.getUrl()));
                return true;
            }
        });

        mWebCodeRead.setWebChromeClient(new WebChromeClient() {

        });
        if (Build.VERSION.SDK_INT >= 11) {
            ((Runnable) () -> mWebCodeRead.getSettings().setDisplayZoomControls(false)).run();
        }
        openFile();
    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        params.height = (int) (DeviceUtils.dpToPx(getActivity(), 56)
                + DeviceUtils.getStatusBarHeight());
        mToolbar.setLayoutParams(params);
        mToolbar.setPadding(0, DeviceUtils.getStatusBarHeight(), 0, 0);
    }

    private void openFile() {
        if (mCodeContentLoader == null) return;
        mCodeContentLoader.showProgress();
        mWebCodeRead.clearHistory();
        if (mNode == null) {
            if (mOpenFileAfterLoadFinish)
                mCodeContentLoader.showEmpty("No File Open");
        } else if (FileTypeUtils.isImageFileType(mNode.absolutePath)) {
            openImageFile();
        } else if (FileTypeUtils.isMdFileType(mNode.absolutePath)) {
            openMdShowFile();
        } else {
            openCodeFile();
        }
    }

    private void openImageFile() {
        String string = "<html>" +
                "<body style=\"background-color:"
                + ColorUtils.getColorString(getContext(), R.color.code_read_background_color)
                + ";margin-top: 40px; margin-bottom: 40px; text-align: center; vertical-align: center;\">"
                + "<img src='file:///" + mNode.absolutePath + "'>"
                + "</body></html>";
        mWebCodeRead.loadDataWithBaseURL(null, string
                , "text/html"
                , "utf-8"
                , null);
    }

    public void openFile(DirectoryNode node) {
        mOpenFileAfterLoadFinish = true;
        mNode = node;
        openFile();
    }

    protected void openCodeFile() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> subscriber) throws Exception {
                InputStream stream = null;
                try {
                    stream = new FileInputStream(mNode.absolutePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (stream == null) {
                    subscriber.onComplete();
                    return;
                }
                final InputStream finalStream = stream;
                String[] names = mNode.name.split("\\.");
                String fileTypeName = names[names.length - 1];//文件后缀名 
                if (BrushMap.isBlackFile(fileTypeName)) {
                    subscriber.onError(new Throwable("Can not open this file!"));
                    subscriber.onComplete();
                    return;
                }

                StringBuilder localStringBuilder = new StringBuilder();
                try {
                    BufferedReader localBufferedReader = new BufferedReader(
                            new InputStreamReader(finalStream, "UTF-8"));
                    for (; ; ) {
                        String str = localBufferedReader.readLine();
                        if (str == null) {
                            break;
                        }
                        localStringBuilder.append(str);
                        localStringBuilder.append("\n");
                    }

                    localBufferedReader.close();

                    String fielCode = TextUtils.htmlEncode(localStringBuilder.toString());
                    subscriber.onNext(HtmlParser.buildHtmlContent(getActivity(), fileTypeName
                            , fielCode, mNode.name));
                } catch (OutOfMemoryError e) {
                    subscriber.onError(e);
                } catch (FileNotFoundException e) {
                    subscriber.onError(e);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(o -> mWebCodeRead.loadDataWithBaseURL("file:///android_asset/",o,"text/html","UTF-8",""))
                .doOnError(e -> mCodeContentLoader.showEmpty(e.getMessage()))
                .onErrorResumeNext(Observable.empty())
                .subscribe();


    }

    protected void openMdShowFile() {
        registerSubscription(
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> subscriber) throws Exception {
                        InputStream stream = null;
                        try {
                            stream = new FileInputStream(mNode.absolutePath);
                        } catch (FileNotFoundException e) {
                            subscriber.onError(e);
                        }
                        if (stream == null)
                            return;
                        final InputStream finalStream = stream;
                        StringBuilder localStringBuilder = new StringBuilder();
                        try {
                            BufferedReader localBufferedReader = new BufferedReader(
                                    new InputStreamReader(finalStream, "UTF-8"));
                            for (; ; ) {
                                String str = localBufferedReader.readLine();
                                if (str == null) {
                                    break;
                                }
                                localStringBuilder.append(str);
                                localStringBuilder.append("\n");
                            }
                            String textString = localStringBuilder.toString();

                            if (textString != null) {
                                MarkdownProcessor m = new MarkdownProcessor(mRootNode.absolutePath);
                                m.setTextColorString(ColorUtils.getColorString(getContext()
                                        , R.color.text_color_primary));
                                m.setBackgroundColorString(ColorUtils.getColorString(getContext()
                                        , R.color.code_read_background_color));
                                m.setCodeBlockColor(ColorUtils.getColorString(getContext()
                                        , R.color.code_block_color));
                                m.setTableBorderColor(ColorUtils.getColorString(getContext()
                                        , R.color.table_block_border_color));
                                String html = m.markdown(textString);
                                subscriber.onNext(html);
                            }
                            subscriber.onComplete();
                        } catch (OutOfMemoryError e) {
                            subscriber.onError(e);
                        } catch (FileNotFoundException e) {
                            subscriber.onError(e);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(s -> mWebCodeRead.loadDataWithBaseURL("fake://", s, "text/html"
                                , "UTF-8", ""))
                        .doOnError(e -> mCodeContentLoader.showEmpty(e.getMessage()))
                        .onErrorResumeNext(Observable.empty())
                        .subscribe()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mWebCodeRead.destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mOrientationChange = true;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mOrientationChange) {
            mOrientationChange = false;
            return;
        }

        if (scrollFinishDelaySubscription != null && !scrollFinishDelaySubscription.isDisposed()) {
            scrollFinishDelaySubscription.dispose();
        }
        if (t - oldt > 70) {
            if (mScrollDown)
                return;

            mScrollDown = true;
        } else if (t - oldt < 0) {
            if (!mScrollDown)
                return;

            mScrollDown = false;
            closeFullScreen();
        }
        if (mScrollDown) {
            scrollFinishDelaySubscription = Observable
                    .timer(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(lo -> openFullScreen())
                    .subscribe();
            registerSubscription(scrollFinishDelaySubscription);
        }
    }

    public void updateRootNode(DirectoryNode directoryNode) {
        mRootNode = directoryNode;
    }

    public ILoadHelper getCodeContentLoader() {
        return mCodeContentLoader;
    }

    private void openFullScreen() {
        hide();
    }

    private void closeFullScreen() {
        show();
    }

}
