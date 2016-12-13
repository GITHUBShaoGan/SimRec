package com.slut.simrec.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class FingerprintHelper {

    private static volatile FingerprintHelper instances;
    private AlertDialog alertDialog;
    private TextView textView;

    private FingerprintHelper() {

    }

    public static FingerprintHelper getInstances() {
        if (instances == null) {
            synchronized (FingerprintHelper.class) {
                if (instances == null) {
                    instances = new FingerprintHelper();
                }
            }
        }
        return instances;
    }

    public void validate(Context context, OnFingerPrintAuthListener onFingerPrintAuthListener) {
        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        FingerprintManager fingerprintManager = (FingerprintManager) App.getContext().getSystemService(Context.FINGERPRINT_SERVICE);
        FingerprintManager.CryptoObject cryptoObject = null;
        try {
            cryptoObject = new CryptoObjectHelper().buildCryptoObject();
        } catch (Exception e) {

        }
        CancellationSignal cancellationSignal = new CancellationSignal();
        showDialog(context, cancellationSignal);
        MyAuthCallback myAuthCallback = new MyAuthCallback(alertDialog,textView, onFingerPrintAuthListener);
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, myAuthCallback, null);
    }

    private void showDialog(Context context, final CancellationSignal cancellationSignal) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_dialog_fingerprint, new LinearLayout(App.getContext()), false);
        textView = (TextView) itemView.findViewById(R.id.message);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.title_dialog_fingerprint);
        builder.setIcon(R.drawable.ic_fp_40px);
        builder.setView(itemView);
        builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
                    cancellationSignal.cancel();
                }
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
                    cancellationSignal.cancel();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
