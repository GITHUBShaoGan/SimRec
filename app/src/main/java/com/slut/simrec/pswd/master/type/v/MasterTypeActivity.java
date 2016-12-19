package com.slut.simrec.pswd.master.type.v;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.fingerprint.FingerprintHelper;
import com.slut.simrec.fingerprint.OnFingerPrintAuthListener;
import com.slut.simrec.fingerprint.Status;
import com.slut.simrec.fingerprint.Utils;
import com.slut.simrec.pswd.master.grid.v.GridPassActivity;
import com.slut.simrec.pswd.master.pattern.v.PatternPassActivity;
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

public class MasterTypeActivity extends AppCompatActivity implements LockTypeAdapter.OnItemClickListener, MasterTypeView, OnFingerPrintAuthListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LockTypeAdapter adapter;
    private LinearLayoutManager layoutManager;

    private static final int REQUEST_SET_MASTER = 10000;
    private static final int REQUEST_GET_PERMISSION = 1000;

    private MasterTypePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_type);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
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

        presenter = new MasterTypePresenterImpl(this);
    }

    private List<LockType> initLockTypeList() {
        List<LockType> lockTypes = new ArrayList<>();
        String[] titleArr = ResUtils.getStringArray(R.array.title_lock_type);
        String[] descArr = ResUtils.getStringArray(R.array.description_lock_type);
        int[] imageArr = {R.drawable.ic_fp_40px, R.drawable.ic_grid, R.drawable.ic_pattern, R.drawable.ic_text};
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
                    builder.setIcon(R.drawable.ic_error_red_24dp);
                    builder.setMessage(R.string.msg_dialog_not_support);
                    builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
                if (status == Status.SUPPORT) {
                    FingerprintHelper.getInstances().validate(this, this);
                }
                if (status == Status.PERMISSION_DENY) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, REQUEST_GET_PERMISSION);
                }
                if (status == Status.FINGERPRINT_NONE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.title_dialog_no_fingerprint);
                    builder.setIcon(R.drawable.ic_warning_amber_24dp);
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
                    builder.setIcon(R.drawable.ic_warning_amber_24dp);
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
                FingerprintHelper.getInstances().validate(this, this);
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

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        presenter.setPass();
    }

    @Override
    public void onAuthenticationFailed() {

    }

    @Override
    public void onAuthDialogCancel() {

    }
}
