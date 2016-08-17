package com.xuchen.myslidingmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * z整体的侧滑菜单的实现是王一个帧布局中放置两个子布局。分别是bottom和top，我们只需要控制top的位移来显示bottom的
 * 内容就呈现了侧滑的效果。
 */
public class MainActivity extends AppCompatActivity {

    private MySlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        slidingMenu = (MySlidingMenu) findViewById(R.id.mysliding);
        //映射view
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View top = inflater.inflate(R.layout.top, null);
        View bottom = View.inflate(this, R.layout.bottom, null);
        //给SlidingMenu添加两个View
        slidingMenu.addBottom(bottom);
        slidingMenu.addTop(top);
    }
}
