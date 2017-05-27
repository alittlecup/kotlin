package com.example.hbl.kotlin.coderead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.example.hbl.kotlin.R;
import com.example.hbl.kotlin.event.DownloadRepoMessageEvent;
import com.example.hbl.kotlin.event.ThemeRecreateEvent;
import com.example.hbl.kotlin.utils.ProgressLoading;
import com.example.hbl.kotlin.utils.RxBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @Nullable
    @BindView(R.id.view_coordinator_container)
    CoordinatorLayout mCoordinatorContainer;

    private final ArrayList<Disposable>disposables=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerSubscription(
                RxBus.getInstance()
                        .toObservable()
                        .filter(o -> o instanceof DownloadRepoMessageEvent)
                        .map(o -> (DownloadRepoMessageEvent) o)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(o -> showMessage(o.getMessage()))
                        .subscribe());

        registerSubscription(
                RxBus.getInstance()
                        .toObservable()
                        .filter(o -> o instanceof ThemeRecreateEvent)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(o -> recreate())
                        .subscribe());
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            onSetupActionBar(getSupportActionBar());
        }

        String title = getIntent().getStringExtra(Intent.EXTRA_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
    }

    protected void onSetupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void registerSubscription(Disposable subscription) {
        disposables.add(subscription);
    }

    protected void unregisterSubscription(Disposable subscription) {
        disposables.remove(subscription);
    }

    protected void clearSubscription() {
        for(Disposable d:disposables){
            d.dispose();
        }
        disposables.clear();
    }

    protected  void reCreateRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearSubscription();
        if (isProgressShow() && mProgressLoading != null) {
            dismissProgressLoading();
            mProgressLoading = null;
        }
    }

    private ProgressLoading mProgressLoading;
    private ProgressLoading mUnBackProgressLoading;
    private boolean progressShow;

    public void showProgressLoading(int resId) {
        showProgressLoading(getString(resId));
    }

    public void showProgressLoading(String message) {
        if (mProgressLoading == null) {
            mProgressLoading = new ProgressLoading(this, R.style.ProgressLoadingTheme);
            mProgressLoading.setCanceledOnTouchOutside(true);
            mProgressLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressShow = false;
                }
            });
        }
        if (!TextUtils.isEmpty(message)) {
            mProgressLoading.setMessage(message);
        } else {
            mProgressLoading.setMessage(null);
        }
        progressShow = true;
        mProgressLoading.show();
    }

    public boolean isProgressShow() {
        return progressShow;
    }

    public void dismissProgressLoading() {
        if (mProgressLoading != null && !isFinishing()) {
            progressShow = false;
            mProgressLoading.dismiss();
        }
    }

    public void showUnBackProgressLoading(int resId) {
        showUnBackProgressLoading(getString(resId));
    }

    // 按返回键不可撤销的
    public void showUnBackProgressLoading(String message) {
        if (mUnBackProgressLoading == null) {
            mUnBackProgressLoading = new ProgressLoading(this, R.style.ProgressLoadingTheme) {
                @Override
                public void onBackPressed() {
                }
            };
        }
        if (!TextUtils.isEmpty(message)) {
            mUnBackProgressLoading.setMessage(message);
        } else {
            mUnBackProgressLoading.setMessage(null);
        }
        mUnBackProgressLoading.show();
    }

    public void dismissUnBackProgressLoading() {
        if (mUnBackProgressLoading != null && !isFinishing()) {
            mUnBackProgressLoading.dismiss();
        }
    }

    public void hideSoftInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showMessage(String message) {
        if (mCoordinatorContainer != null)
            Snackbar.make(mCoordinatorContainer, message, Snackbar.LENGTH_SHORT)
                    .setAction("Sure", view -> {})
                    .show();
    }
}
