package com.heqing.sliderefreshlistview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.heqing.sliderefreshlistview.R;

/**
 * Created by 何清 on 2016/7/22.
 *
 * @description  向左滑动显示菜单
 */
public class LeftDragViewGroup extends FrameLayout{
    private static final String TAG = "LeftDragViewGroup";

    private View mContentView;
    private View mMenuView;
    private int mMaxDrag;

    private Scroller mScroller;
    private boolean mMenuShow = false;

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
            setTag(R.id.content_left, mContentView.getLeft());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                int left = mContentView.getLeft();
                if (left <= -mMaxDrag/2){
                    smoothSlideViewTo(-mMaxDrag-left, 0);
                    mMenuShow = true;
                }else{
                    mMenuShow = false;
                    smoothSlideViewTo(-left,0);
                }
                break;
        }
        return false;
    }

    public boolean isMenuShow() {
        return mMenuShow;
    }

    public void setMenuShow(boolean mMenuShow) {
        this.mMenuShow = mMenuShow;
    }

    public void turnNormal(){
        smoothSlideViewTo(-mContentView.getLeft(), 0);
        mMenuShow = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMaxDrag = mMenuView.getMeasuredWidth();
    }

    public void smoothSlideViewTo(int destX,int destY){
        mScroller.startScroll(mContentView.getLeft(),0,destX,0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            mContentView.offsetLeftAndRight(mScroller.getCurrX()-mContentView.getLeft());
            postInvalidate();
        }
    }
}
