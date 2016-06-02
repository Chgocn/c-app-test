package io.chgocn.plug.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.RotateAnimation;

import io.chgocn.plug.R;

public class PtrClassicDefaultFooter extends PtrClassicDefaultHeader {

    public PtrClassicDefaultFooter(Context context) {
        super(context);
    }

    public PtrClassicDefaultFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrClassicDefaultFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void buildAnimation() {
        super.buildAnimation();
        RotateAnimation tmp = mFlipAnimation;
        mFlipAnimation = mReverseFlipAnimation;
        mReverseFlipAnimation = tmp;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        super.onUIRefreshPrepare(frame);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up_to_load));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up));
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        super.onUIRefreshPrepare(frame);
        mTitleTextView.setText(R.string.cube_ptr_loading);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        super.onUIRefreshComplete(frame);
        mTitleTextView.setText(getResources().getString(R.string.cube_ptr_load_complete));
    }

    @Override
    protected void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView.setVisibility(VISIBLE);
            mTitleTextView.setText(R.string.cube_ptr_release_to_load);
        }
    }

    @Override
    protected void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_release_to_load));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up));
        }
    }
}
