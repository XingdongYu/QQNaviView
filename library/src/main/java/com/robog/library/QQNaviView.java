package com.robog.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by yxd on 2017/5/31.
 */

public class QQNaviView extends FrameLayout {

    private View view;

    /* 外层图标/拖动幅度较小图标 */
    private ImageView mBigIcon;

    /* 里面图标/拖动幅度较大图标 */
    private ImageView mSmallIcon;

    /* 外层图标资源 */
    private int mBigIconSrc;

    /* 里面图标资源 */
    private int mSmallIconSrc;

    /* 拖动幅度较大半径 */
    private float mBigRadius;

    /* 拖动幅度小半径 */
    private float mSmallRadius;

    private float mRange;

    private float lastX;

    private float lastY;

    public QQNaviView(@NonNull Context context) {
        this(context, null);
    }

    public QQNaviView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQNaviView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FaceView, defStyleAttr, 0);
        mBigIconSrc = ta.getResourceId(R.styleable.FaceView_bigIcon, R.drawable.big);
        mSmallIconSrc = ta.getResourceId(R.styleable.FaceView_smallIcon, R.drawable.small);
        mRange = ta.getFloat(R.styleable.FaceView_range, 1);
        init(context);
    }

    private void init(Context context) {
        view = inflate(context, R.layout.view_icon, null);
        mBigIcon = (ImageView) view.findViewById(R.id.iv_out);
        mSmallIcon = (ImageView) view.findViewById(R.id.iv_in);
        mBigIcon.setImageResource(mBigIconSrc);
        mSmallIcon.setImageResource(mSmallIconSrc);

        this.addView(view);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSmallRadius = 0.1f * Math.min(mBigIcon.getWidth(), mBigIcon.getHeight());
        mBigRadius = 1.5f * mSmallRadius * mRange;
        int padding = (int) mSmallRadius;
        mBigIcon.setPadding(padding, padding, padding, padding);
        mSmallIcon.setPadding(padding, padding, padding, padding);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        View child = getChildAt(0);
        int width = Math.max(mBigIcon.getMeasuredWidth(), mSmallIcon.getMeasuredWidth());
        int height = Math.max(mBigIcon.getMeasuredHeight(), mSmallIcon.getMeasuredHeight());
        int childLeft = (getWidth() - width) / 2;
        int childTop = (getHeight() - height) / 2;
        child.layout(childLeft, childTop, childLeft + width, childTop + height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - lastX;
                float deltaY = y - lastY;
                moveEvent(mBigIcon, deltaX, deltaY, mSmallRadius);
                moveEvent(mSmallIcon, 1.5f * deltaX, 1.5f * deltaY, mBigRadius);
                break;
            case MotionEvent.ACTION_UP:
                mBigIcon.setX(0);
                mBigIcon.setY(0);
                mSmallIcon.setX(0);
                mSmallIcon.setY(0);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void moveEvent(View view, float deltaX, float deltaY, float radius){
        float distance = getDistance(deltaX, deltaY);
        double degree = Math.atan2(deltaY, deltaX);
        if (distance > radius){
            view.setX(view.getLeft() + (float) (radius * Math.cos(degree)));
            view.setY(view.getTop() + (float) (radius * Math.sin(degree)));
        }else {
            view.setX(view.getLeft() + deltaX);
            view.setY(view.getTop() + deltaY);
        }

    }

    private float getDistance(float x, float y){
        return (float) Math.sqrt(x * x + y * y);
    }

    public void setBigIcon(int res){
        mBigIcon.setImageResource(res);
    }

    public void setSmallIcon(int res){
        mSmallIcon.setImageResource(res);
    }
}
