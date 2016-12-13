package com.slut.simrec.utils;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

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

    public static void openWebsite(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(Intent.createChooser(intent, ResUtils.getString(R.string.title_select_browser)));
    }

}
