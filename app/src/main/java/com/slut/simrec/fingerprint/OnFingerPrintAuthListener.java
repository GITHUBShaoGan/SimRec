package com.slut.simrec.fingerprint;

import android.hardware.fingerprint.FingerprintManager;

/**
 * Created by 七月在线科技 on 2016/12/12.
 */

public interface OnFingerPrintAuthListener {

    void onAuthenticationError(int errorCode, CharSequence errString);

    void onAuthenticationHelp(int helpCode, CharSequence helpString);

    void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result);

    void onAuthenticationFailed();

    void onAuthDialogCancel();
}
