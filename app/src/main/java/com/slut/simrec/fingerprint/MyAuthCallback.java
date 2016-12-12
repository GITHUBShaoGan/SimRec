package com.slut.simrec.fingerprint;

import android.app.Dialog;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v7.app.AlertDialog;

import com.slut.simrec.utils.ToastUtils;

/**
 * Created by 七月在线科技 on 2016/12/12.
 */

public class MyAuthCallback extends FingerprintManager.AuthenticationCallback {

    private OnFingerPrintAuthListener onFingerPrintAuthListener;

    public MyAuthCallback(OnFingerPrintAuthListener onFingerPrintAuthListener) {
        super();
        this.onFingerPrintAuthListener = onFingerPrintAuthListener;
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        onFingerPrintAuthListener.onAuthenticationError(errorCode,errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        onFingerPrintAuthListener.onAuthenticationHelp(helpCode,helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        onFingerPrintAuthListener.onAuthenticationSucceeded(result);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        onFingerPrintAuthListener.onAuthenticationFailed();
    }
}
