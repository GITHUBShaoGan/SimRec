package com.slut.simrec.fingerprint;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.app.ActivityCompat;

import com.slut.simrec.App;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class Utils {

    /**
     * 获取当前指纹识别状态
     *
     * @return
     */
    public static int getCurrentFingerPrintStatus() {
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager");
        } catch (Exception e) {
            return Status.PACKAGE_NOT_EXIST;
        }
        FingerprintManager manager = (FingerprintManager) App.getContext().getSystemService(Context.FINGERPRINT_SERVICE);
        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
            if (manager.isHardwareDetected()) {
                KeyguardManager keyguardManager = (KeyguardManager) App.getContext().getSystemService(Context.KEYGUARD_SERVICE);
                if (keyguardManager.isKeyguardSecure()) {
                    if (manager.hasEnrolledFingerprints()) {
                        return Status.SUPPORT;
                    } else {
                        return Status.FINGERPRINT_NONE;
                    }
                } else {
                    return Status.KEYGUARD_NOT_SECURE;
                }
            } else {
                return Status.HARDWARE_NOT_DETECT;
            }
        } else {
            return Status.PERMISSION_DENY;
        }
    }

}
