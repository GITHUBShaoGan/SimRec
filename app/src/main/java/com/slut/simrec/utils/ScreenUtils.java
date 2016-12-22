package com.slut.simrec.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.slut.simrec.App;

/**
 * Created by 七月在线科技 on 2016/12/20.
 */

public class ScreenUtils {

    public static int getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)App.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
