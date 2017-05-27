package com.example.hbl.kotlin.coderead;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.example.hbl.kotlin.R;
import com.example.hbl.kotlin.utils.ProgressLoading;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class BaseFragment extends Fragment {
    private final ArrayList<Disposable> disposables=new ArrayList<>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearSubscription();
        if (isProgressShow() && mProgressLoading != null) {
            dismissProgressLoading();
            mProgressLoading = null;
        }
    }

    private ProgressLoading mProgressLoading;
    private ProgressLoading mUnBackProgressLoading;
    private boolean progressShow;

    public ProgressLoading getProgressLoading() {
        return mProgressLoading;
    }

    public void showProgressLoading(int resId) {
        showProgressLoading(getString(resId));
    }

    public void showProgressLoading(String message) {
        if (mProgressLoading == null) {
            mProgressLoading = new ProgressLoading(getActivity(), R.style.ProgressLoadingTheme);
            mProgressLoading.setCanceledOnTouchOutside(true);
            mProgressLoading.setOnCancelListener(dialog -> progressShow = false);
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
        if (mProgressLoading != null && isVisible()) {
            progressShow = false;
            mProgressLoading.dismiss();
        }
    }

    public void showUnBackProgressLoading(int resId) {
        showUnBackProgressLoading(getString(resId));
    }

    public void showUnBackProgressLoading(String message) {
        if (mUnBackProgressLoading == null) {
            mUnBackProgressLoading = new ProgressLoading(getActivity(), R.style.ProgressLoadingTheme) {
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
        if (mUnBackProgressLoading != null && isVisible()) {
            mUnBackProgressLoading.dismiss();
        }
    }

}
