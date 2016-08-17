package com.heqing.sliderefreshlistview.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 何清 on 2016/7/22.
 *
 * @description  向左滑动显示菜单
 */
public class LeftDragViewGroup extends FrameLayout{
    private static final String TAG = "LeftDragViewGroup";

    private ViewDragHelper mViewDragHelper;
    private View mContentView;
    private View mMenuView;
    private int mMaxDrag;
    private float mLastX;
    private float mLastY;
    private float mDeltaX;
    private float mDeltaY;

    public LeftDragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeftDragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("tag", TAG + ":onTouchEvent");
        mViewDragHelper.processTouchEvent(event);
        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.i("tag",TAG + ":onTouchEvent:ACTION_DOWN");
//                mLastX = event.getX();
//                mLastY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("tag",TAG + ":onTouchEvent:ACTION_MOVE");
//                mDeltaX = event.getX() - mLastX;
//                mDeltaY = event.getY() - mLastY;
//                Log.i("tag",TAG + ":mDeltaX:"+mDeltaX+"---mDeltaY:"+mDeltaY);
//                if (Math.abs(mDeltaX) > Math.abs(mDeltaY)){
//                    mViewDragHelper.processTouchEvent(event);
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                requestDisallowInterceptTouchEvent(false);
                return false;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("tag", TAG + ":onInterceptTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mDeltaX = event.getX() - mLastX;
                mDeltaY = event.getY() - mLastY;
                Log.i("tag","mDeltaX:"+mDeltaX+"---mDeltaY:"+mDeltaY);
                if (Math.abs(mDeltaX) > Math.abs(mDeltaY)){
                    return true;
                }
                break;
        }
//        return true;
        return false;
//        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMaxDrag = mMenuView.getMeasuredWidth();
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if(mContentView.getLeft() > -mMaxDrag / 2){
                mViewDragHelper.smoothSlideViewTo(mContentView,0,0);
                ViewCompat.postInvalidateOnAnimation(LeftDragViewGroup.this);
            }else{
                mViewDragHelper.smoothSlideViewTo(mContentView,-mMaxDrag,0);
                ViewCompat.postInvalidateOnAnimation(LeftDragViewGroup.this);
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > 0){
                return 0;
            }
            if (left < -mMaxDrag){
                return -mMaxDrag;
            }
            return left;
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
