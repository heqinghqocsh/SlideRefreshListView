package com.heqing.sliderefreshlistview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by 何清 on 2016/7/22.
 *
 * @description  向左滑动显示菜单
 */
public class LeftDragViewGroup extends FrameLayout{
    private static final String TAG = "LeftDragViewGroup";

//    private ViewDragHelper mViewDragHelper;
    private View mContentView;
    private View mMenuView;
    private int mMaxDrag;
    private float mLastX = 0;
    private float mLastY = 0;
    private float mDeltaX = 0;
    private float mDeltaY = 0;

    private Scroller mScroller;

    public LeftDragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LeftDragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    public void offsetLeftAndRight(int offset) {
        int left = mContentView.getLeft();

        if (left <= 0){
            if (left + offset >= 0){
                offset = -left;
            }else if (left + offset <= -mMaxDrag){
                offset = -(left + mMaxDrag);
            }
            mContentView.offsetLeftAndRight(offset);
            setTag(1,mContentView.getLeft());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMaxDrag = mMenuView.getMeasuredWidth();
    }

    public void smoothSlideViewTo(int destX,int destY){
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX,0,delta,0,Math.abs(delta)*3);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
