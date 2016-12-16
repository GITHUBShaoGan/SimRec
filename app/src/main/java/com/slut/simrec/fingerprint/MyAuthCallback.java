package com.slut.simrec.fingerprint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.slut.simrec.R;
import com.slut.simrec.utils.ResUtils;
import com.slut.simrec.utils.ToastUtils;

/**
 * Created by 七月在线科技 on 2016/12/12.
 */

public class MyAuthCallback extends FingerprintManager.AuthenticationCallback {

    private OnFingerPrintAuthListener onFingerPrintAuthListener;
    private TextView dialogMessage;
    private AlertDialog dialog;

    public MyAuthCallback(AlertDialog dialog, TextView textView, final OnFingerPrintAuthListener onFingerPrintAuthListener) {
        super();
        this.onFingerPrintAuthListener = onFingerPrintAuthListener;
        this.dialogMessage = textView;
        this.dialog = dialog;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onFingerPrintAuthListener.onAuthDialogCancel();
            }
        });
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        onFingerPrintAuthListener.onAuthenticationError(errorCode, errString);
        this.dialogMessage.setText(errString.toString() + "");
        this.dialogMessage.setTextColor(Color.RED);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        onFingerPrintAuthListener.onAuthenticationHelp(helpCode, helpString);
        this.dialogMessage.setText(helpString.toString() + "");
        this.dialogMessage.setTextColor(Color.RED);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        onFingerPrintAuthListener.onAuthenticationSucceeded(result);
        this.dialogMessage.setText(ResUtils.getString(R.string.fingerprint_validate_success));
        this.dialogMessage.setTextColor(Color.parseColor("#2A3245"));
        this.dialog.dismiss();
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        onFingerPrintAuthListener.onAuthenticationFailed();
        this.dialogMessage.setText(ResUtils.getString(R.string.fingerprint_validate_failed));
        this.dialogMessage.setTextColor(Color.RED);
    }
}
