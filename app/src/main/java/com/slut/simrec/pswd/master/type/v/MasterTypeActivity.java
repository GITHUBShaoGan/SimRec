package com.slut.simrec.pswd.master.type.v;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.fingerprint.CryptoObjectHelper;
import com.slut.simrec.fingerprint.MyAuthCallback;
import com.slut.simrec.fingerprint.OnFingerPrintAuthListener;
import com.slut.simrec.fingerprint.Status;
import com.slut.simrec.fingerprint.Utils;
import com.slut.simrec.pswd.master.grid.v.GridPassActivity;
import com.slut.simrec.pswd.master.pattern.PatternPassActivity;
import com.slut.simrec.pswd.master.text.v.TextPassActivity;
import com.slut.simrec.pswd.master.type.bean.LockType;
import com.slut.simrec.pswd.master.type.adapter.LockTypeAdapter;
import com.slut.simrec.pswd.master.type.p.MasterTypePresenter;
import com.slut.simrec.pswd.master.type.p.MasterTypePresenterImpl;
import com.slut.simrec.utils.ResUtils;
import com.slut.simrec.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterTypeActivity extends AppCompatActivity implements LockTypeAdapter.OnItemClickListener, MasterTypeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LockTypeAdapter adapter;
    private LinearLayoutManager layoutManager;

    private CancellationSignal cancellationSignal;
    private FingerprintManager.CryptoObject cryptoObject;

    private static final int REQUEST_SET_MASTER = 10000;
    private static final int REQUEST_GET_PERMISSION = 1000;

    private MasterTypePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_type);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        adapter = new LockTypeAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setLockTypeList(initLockTypeList());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        cancellationSignal = new CancellationSignal();

        presenter = new MasterTypePresenterImpl(this);
    }

    private List<LockType> initLockTypeList() {
        List<LockType> lockTypes = new ArrayList<>();
        String[] titleArr = ResUtils.getStringArray(R.array.title_lock_type);
        String[] descArr = ResUtils.getStringArray(R.array.description_lock_type);
        int[] imageArr = {R.drawable.ic_fp_40px, R.drawable.ic_fp_40px, R.drawable.ic_fp_40px, R.drawable.ic_fp_40px};
        for (int i = 0; i < titleArr.length; i++) {
            LockType lockType = new LockType(titleArr[i], descArr[i], imageArr[i]);
            lockTypes.add(lockType);
        }
        return lockTypes;
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SET_MASTER:
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }

    private void validate(final TextView message) {
        FingerprintManager manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            cryptoObject = new CryptoObjectHelper().buildCryptoObject();
        } catch (Exception e) {

        }
        cancellationSignal = new CancellationSignal();
        manager.authenticate(cryptoObject, cancellationSignal, 0, new MyAuthCallback(new OnFingerPrintAuthListener() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                message.setTextColor(Color.RED);
                message.setText(errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                message.setTextColor(Color.parseColor("#2A3245"));
                message.setText(helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                message.setTextColor(Color.parseColor("#2A3245"));
                message.setText(R.string.fingerprint_validate_success);
                presenter.setPass();
            }

            @Override
            public void onAuthenticationFailed() {
                message.setTextColor(Color.RED);
                message.setText(R.string.fingerprint_validate_failed);
            }
        }), null);
    }

    private void showFingerprintValidateDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.view_dialog_fingerprint, new LinearLayout(this), false);
        TextView message = (TextView) v.findViewById(R.id.message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_fp_40px);
        builder.setTitle(R.string.title_dialog_fingerprint);
        builder.setView(v);
        builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancellationSignal.cancel();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                cancellationSignal.cancel();
            }
        });
        validate(message);
        builder.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //指纹识别
                int status = Utils.getCurrentFingerPrintStatus();
                if (status == Status.HARDWARE_NOT_DETECT || status == Status.PACKAGE_NOT_EXIST) {
                    //不支持指纹识别
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.title_dialog_not_support);
                    builder.setMessage(R.string.msg_dialog_not_support);
                    builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
                if (status == Status.SUPPORT) {
                    showFingerprintValidateDialog();
                }
                if (status == Status.PERMISSION_DENY) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, REQUEST_GET_PERMISSION);
                }
                if (status == Status.FINGERPRINT_NONE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.title_dialog_no_fingerprint);
                    builder.setMessage(R.string.msg_dialog_no_fingerprint);
                    builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
                if (status == Status.KEYGUARD_NOT_SECURE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.title_dialog_keyguard_notsecure);
                    builder.setMessage(R.string.msg_dialog_keyguard_notsecure);
                    builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
                break;
            case 1:
                Intent intentOpenGrid = new Intent(this, GridPassActivity.class);
                startActivityForResult(intentOpenGrid, REQUEST_SET_MASTER);
                break;
            case 2:
                Intent intentOpenPattern = new Intent(this, PatternPassActivity.class);
                startActivityForResult(intentOpenPattern, REQUEST_SET_MASTER);
                break;
            case 3:
                Intent intentOpenText = new Intent(this, TextPassActivity.class);
                startActivityForResult(intentOpenText, REQUEST_SET_MASTER);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GET_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showFingerprintValidateDialog();
            } else {
                // Permission Denied
                ToastUtils.showShort(R.string.permission_denied);
            }
        }
    }

    @Override
    public void onSetPassSuccess() {
        App.setIsPswdFunctionLocked(false);
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onSetPassError(String msg) {
        ToastUtils.showShort(msg);
    }
}
