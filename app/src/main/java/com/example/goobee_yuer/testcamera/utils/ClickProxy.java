package com.example.goobee_yuer.testcamera.utils;

import android.view.View;

/**防止重复点击的代理类*/
public class ClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastclick = 0;
    private long timems = 1500; //ms
    private IAgain mIAgain;

    public ClickProxy(View.OnClickListener origin, long timems, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.timems = timems;
    }

    public ClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}

