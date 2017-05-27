package com.example.hbl.kotlin.coderead.loader;

import android.widget.ViewAnimator;

public class RecyclerLoader implements ILoadHelper {

    ViewAnimator mViewAnimator;

    public RecyclerLoader(ViewAnimator continer) {
        mViewAnimator = continer;
    }

    @Override
    public void showProgress() {
        mViewAnimator.setDisplayedChild(2);
    }

    @Override
    public void showContent() {
        mViewAnimator.setDisplayedChild(0);
    }

    @Override
    public void showEmpty(String message) {
        mViewAnimator.setDisplayedChild(1);
    }
}
