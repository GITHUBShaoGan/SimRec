package com.slut.simrec.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.slut.simrec.App;

import static android.R.attr.id;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class ToastUtils {

    public static void showShort(int id) {
        Toast.makeText(App.getContext(), id, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
