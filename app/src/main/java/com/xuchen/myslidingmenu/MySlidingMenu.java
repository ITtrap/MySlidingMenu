package com.xuchen.myslidingmenu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by xuchen on 2016/8/17.
 */
public class MySlidingMenu extends FrameLayout {
    private LinearLayout toplayout,bottomlayout;//嵌套内容的两个子布局
    private Point p = new Point();//记录当前的坐标位置
    private int  maxWidth ;
    private boolean isFirst = true;//表示是否第一次

    public MySlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    //初始化的方法
    private void initViews() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        maxWidth = (int) (manager.getDefaultDisplay().getWidth()*0.7);
        toplayout = new LinearLayout(getContext());
        //指定屏幕的宽和高等参数----LayoutParams是父类的类型
        toplayout.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        bottomlayout = new LinearLayout(getContext());
        bottomlayout.setLayoutParams(new FrameLayout.LayoutParams(maxWidth, -1));
        //添加到frameLayout中
        addView(bottomlayout);
        addView(toplayout);

    }
    //添加底部的方法
    public void addBottom(View bottom){
        //因为映射出来的view没有指定任何的父布局，所以没有给定具体的LayoutParams,需要自己手动给定，否则无法呈现对应布局的属性
        bottom.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        bottomlayout.addView(bottom);

    }
    //添加top的方法
    public void addTop(View top){
        //因为映射出来的view没有指定任何的父布局，所以没有给定具体的LayoutParams,需要自己手动给定，否则无法呈现对应布局的属性
        top.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        toplayout.addView(top);
    }

    //移动需要处理touch
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN://按下
                //记录下当前按下的位置
                p.x = (int) ev.getX();
                p.y = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                float x = ev.getX();
                float y = ev.getY();
                //获取偏移量作用在toplayout中
                int disX = (int) (x-p.x);
                int disY = (int) (y-p.y);
                if (disX>0){//表示从左往右进行滑动
                    //首先获取topLayout的layotuParams才能进行margin 的设定
                    FrameLayout.LayoutParams lp = (LayoutParams) toplayout.getLayoutParams();
                    lp.leftMargin = lp.leftMargin+disX;
                    lp.rightMargin = lp.rightMargin-disX;
                    //处理边界范围
                    if (lp.leftMargin>=maxWidth){
                        lp.leftMargin = maxWidth;
                        lp.rightMargin = -maxWidth;
                    }
                    //从新将lp设置给topLayout
                    toplayout.setLayoutParams(lp);
                    //请求从新布局
                    requestLayout();

                }else if (disX<0){//从右向左滑动
                    FrameLayout.LayoutParams lp = (LayoutParams) toplayout.getLayoutParams();
                    lp.leftMargin = lp.leftMargin+disX;
                    lp.rightMargin = lp.rightMargin-disX;
                    //处理边界范围
                    if (lp.leftMargin<=0){
                        lp.leftMargin = 0;
                        lp.rightMargin = 0;
                    }
                    //从新将lp设置给topLayout
                    toplayout.setLayoutParams(lp);
                    //请求从新布局
                    requestLayout();
                }
                //将点从新定义
                p.x = (int) x;
                p.y = (int) y;
                break;
            case MotionEvent.ACTION_UP://抬起
                //处理惯性 如果滑动的时候已经达到一半以上就直接滑动到最大  如果是一半一下就直接归位0
                FrameLayout.LayoutParams lp = (LayoutParams) toplayout.getLayoutParams();
                //判断是否滑动到最大限度的一半
                if (lp.leftMargin-maxWidth/2>0){
                    lp.leftMargin = maxWidth;
                    lp.rightMargin = -maxWidth;
                }else{
                    lp.leftMargin = 0;
                    lp.rightMargin = 0;
                }
                toplayout.setLayoutParams(lp);
                requestLayout();;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
