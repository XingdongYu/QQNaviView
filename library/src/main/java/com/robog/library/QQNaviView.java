package com.robog.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by yxd on 2017/5/31.
 */

public class QQNaviView extends LinearLayout {

    private static final String TAG = "QQNaviView";

    private Context mContext;

    /* 主view */
    private View mView;

    /* 外层icon/拖动幅度较小icon */
    private ImageView mBigIcon;

    /* 里层icon/拖动幅度较大icon */
    private ImageView mSmallIcon;

    /* 外层icon资源 */
    private int mBigIconSrc;

    /* 里面icon资源 */
    private int mSmallIconSrc;

    /* icon宽度 */
    private float mIconWidth;

    /* icon高度 */
    private float mIconHeight;

    /* 拖动幅度较大半径 */
    private float mBigRadius;

    /* 拖动幅度小半径 */
    private float mSmallRadius;

    /* 拖动范围 可调 */
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

        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QQNaviView, defStyleAttr, 0);
        mBigIconSrc = ta.getResourceId(R.styleable.QQNaviView_bigIconSrc, R.drawable.big);
        mSmallIconSrc = ta.getResourceId(R.styleable.QQNaviView_smallIconSrc, R.drawable.small);
        mIconWidth = ta.getDimension(R.styleable.QQNaviView_iconWidth, dp2px(context, 60));
        mIconHeight = ta.getDimension(R.styleable.QQNaviView_iconHeight, dp2px(context, 60));
        mRange = ta.getFloat(R.styleable.QQNaviView_range, 1);
        ta.recycle();

        //默认垂直排列
        setOrientation(LinearLayout.VERTICAL);

        init(context);
    }

    private void init(Context context) {
        mView = inflate(context, R.layout.view_icon, null);
        mBigIcon = (ImageView) mView.findViewById(R.id.iv_big);
        mSmallIcon = (ImageView) mView.findViewById(R.id.iv_small);

        mBigIcon.setImageResource(mBigIconSrc);
        mSmallIcon.setImageResource(mSmallIconSrc);

        setWidthAndHeight(mBigIcon);
        setWidthAndHeight(mSmallIcon);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        mView.setLayoutParams(lp);
        addView(mView);
    }

    /**
     * 设置icon宽高
     * @param view
     */
    private void setWidthAndHeight(View view){
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        lp.width = (int) mIconWidth;
        lp.height = (int) mIconHeight;
        view.setLayoutParams(lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setupView();
        measureDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 确定view以及拖动相关参数
     */
    private void setupView() {
        //根据view的宽高确定可拖动半径的大小
        mSmallRadius = 0.1f * Math.min(mView.getMeasuredWidth(), mView.getMeasuredHeight()) * mRange;
        mBigRadius = 1.5f * mSmallRadius;
        //设置imageview的padding，不然拖动时图片边缘部分会消失
        int padding = (int) mBigRadius;
        mBigIcon.setPadding(padding, padding, padding, padding);
        mSmallIcon.setPadding(padding, padding, padding, padding);
    }

    private void measureDimension(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++){
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE){
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                final int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                final int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                width += childWidth;
                height += childHeight;
            }
        }
        width += (getPaddingLeft() + getPaddingRight());
        height += (getPaddingTop() + getPaddingBottom());
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft;
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i ++){
            final View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() != GONE){
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                //水平居中显示
                childLeft = (getWidth() - childWidth) / 2;
                //当前子view的top
                childTop += lp.topMargin;
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                //下一个view的top是当前子view的top + height + bottomMargin
                childTop += childHeight + lp.bottomMargin;
            }
        }
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
                //因为可拖动大半径是小半径的1.5倍， 因此这里x,y也相应乘1.5
                moveEvent(mSmallIcon, 1.5f * deltaX, 1.5f * deltaY, mBigRadius);
                break;
            case MotionEvent.ACTION_UP:
                //抬起时复位
                mBigIcon.setX(0);
                mBigIcon.setY(0);
                mSmallIcon.setX(0);
                mSmallIcon.setY(0);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 拖动事件
     * @param view
     * @param deltaX
     * @param deltaY
     * @param radius
     */
    private void moveEvent(View view, float deltaX, float deltaY, float radius){

        //先计算拖动距离
        float distance = getDistance(deltaX, deltaY);

        //拖动的方位角，atan2出来的角度是带正负号的
        double degree = Math.atan2(deltaY, deltaX);

        //如果大于临界半径就不能再往外拖了
        if (distance > radius){
            view.setX(view.getLeft() + (float) (radius * Math.cos(degree)));
            view.setY(view.getTop() + (float) (radius * Math.sin(degree)));
        }else {
            view.setX(view.getLeft() + deltaX);
            view.setY(view.getTop() + deltaY);
        }

    }

    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
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

    public void setIconWidthAndHeight(float width, float height){
        mIconWidth = dp2px(mContext, width);
        mIconHeight = dp2px(mContext, height);
        setWidthAndHeight(mBigIcon);
        setWidthAndHeight(mSmallIcon);
    }

    public void setRange(float range){
        mRange = range;
    }

}
