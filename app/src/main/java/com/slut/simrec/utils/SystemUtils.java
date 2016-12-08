package com.slut.simrec.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.slut.simrec.App;
import com.slut.simrec.R;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class SystemUtils {

    public static void copy(String label, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(label, content);
        clipboardManager.setPrimaryClip(clipData);
        ToastUtils.showShort(R.string.copy_success);
    }

}
