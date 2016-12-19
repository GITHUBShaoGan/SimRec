package com.slut.simrec.pswd;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;

import com.slut.simrec.App;
import com.slut.simrec.base.CommonFatherActivity;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.fingerprint.FingerprintHelper;
import com.slut.simrec.fingerprint.OnFingerPrintAuthListener;
import com.slut.simrec.pswd.unlock.grid.v.GridUnlockActivity;
import com.slut.simrec.pswd.unlock.pattern.v.PatternUnlockActivity;
import com.slut.simrec.pswd.unlock.text.v.TextUnlockActivity;

/**
 * Created by 七月在线科技 on 2016/12/16.
 */

public class PassFatherActivity extends CommonFatherActivity {

    public static final int REQUEST_UNLOCK_GRID = 1050;
    public static final int REQUEST_UNLOCK_PATTERN = 2050;
    public static final int REQUEST_UNLOCK_TEXT = 3050;

    @Override
    protected void onStart() {
        super.onStart();
            check();
    }

    private void check() {
        if (App.isPswdFunctionLocked()) {
            try {
                PassConfig passConfig = PassConfigDao.getInstances().querySingleConfig();
                if (passConfig != null) {
                    boolean isFingerPrintAgree = passConfig.isFingerPrintAgreed();
                    if (isFingerPrintAgree) {
                        FingerprintHelper.getInstances().validate(this, new OnFingerPrintAuthListener() {
                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                finish();
                            }

                            @Override
                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

                            }

                            @Override
                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

                            }

                            @Override
                            public void onAuthenticationFailed() {
                                finish();
                            }

                            @Override
                            public void onAuthDialogCancel() {
                                finish();
                            }
                        });
                    } else {
                        int lockType = passConfig.getPreferLockType();
                        switch (lockType) {
                            case PassConfig.LockType.GRID:
                                Intent openGridUnlock = new Intent(this, GridUnlockActivity.class);
                                startActivityForResult(openGridUnlock, REQUEST_UNLOCK_GRID);
                                break;
                            case PassConfig.LockType.PATTERN:
                                Intent openPatternUnlock = new Intent(this, PatternUnlockActivity.class);
                                startActivityForResult(openPatternUnlock, REQUEST_UNLOCK_PATTERN);
                                break;
                            case PassConfig.LockType.TEXT:
                                Intent openTextUnlock = new Intent(this, TextUnlockActivity.class);
                                startActivityForResult(openTextUnlock, REQUEST_UNLOCK_TEXT);
                                break;
                        }
                    }
                } else {
                    finish();
                }
            } catch (Exception e) {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_UNLOCK_GRID:
                    finish();
                    break;
                case REQUEST_UNLOCK_PATTERN:
                    finish();
                    break;
                case REQUEST_UNLOCK_TEXT:
                    finish();
                    break;
            }
        }
    }
}
