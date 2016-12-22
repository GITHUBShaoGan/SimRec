package com.slut.simrec.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.slut.simrec.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/21.
 */

public class AppHorizontalScrollView extends HorizontalScrollView {

    private int screenSize = ScreenUtils.getScreenWidth();
    private int currentPage = 0;
    private float downX = 0;

    public AppHorizontalScrollView(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
    }

    public AppHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppHorizontalScrollView(Context context) {
        super(context);
    }

    /**
     * 触摸监听时间
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
            case MotionEvent.ACTION_UP:
                if (Math.abs((ev.getX() - downX)) > getWidth() / 5) {
                    if (ev.getX() - downX > 0) {
                        smoothScrollToPrePage();
                    } else {
                        smoothScrollToNextPage();
                    }
                } else {
                    smoothScrollToCurrent();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 滑动到当前页
     *
     * @author caizhiming
     */
    private void smoothScrollToCurrent() {
        if (currentPage == 0) {
            smoothScrollTo(0, 0);
        } else {
            smoothScrollTo(screenSize, 0);
        }
    }

    private void smoothScrollToPrePage() {
        currentPage = 0;
        smoothScrollTo(0, 0);
    }

    private void smoothScrollToNextPage() {
        currentPage = 1;
        smoothScrollTo(screenSize, 0);
    }

}