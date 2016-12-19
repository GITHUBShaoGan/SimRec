package com.slut.simrec.pswd.unlock.grid.v;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.fingerprint.Status;
import com.slut.simrec.fingerprint.Utils;
import com.slut.simrec.pswd.unlock.grid.p.GridUnlockPresenter;
import com.slut.simrec.pswd.unlock.grid.p.GridUnlockPresenterImpl;
import com.slut.simrec.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridUnlockActivity extends AppCompatActivity implements GridUnlockView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.showorhide)
    CheckBox showOrHide;
    @BindView(R.id.passWordView)
    GridPasswordView passwordView;

    private GridUnlockPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_unlock);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        passwordView.setPasswordType(PasswordType.NUMBER);

        presenter = new GridUnlockPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent != null) {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
            }
        });
        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() < 6) {
                    tips.setText(R.string.tips_grid_unlock);
                    tips.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onInputFinish(String psw) {
                presenter.unlock(psw);
            }
        });
        showOrHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    passwordView.setPasswordVisibility(true);
                } else {
                    passwordView.setPasswordVisibility(false);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = getIntent();
            if (intent != null) {
                setResult(RESULT_CANCELED, intent);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_unlock, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                passwordView.clearPassword();
                tips.setText(R.string.tips_grid_unlock);
                tips.setTextColor(Color.BLACK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUnlockSuccess() {
        App.setIsPswdFunctionLocked(false);
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onUnlockFailed() {
        tips.setText(R.string.pass_validate_failed);
        tips.setTextColor(Color.RED);
    }

    @Override
    public void onUnlockError(String msg) {
        tips.setText(msg);
        tips.setTextColor(Color.RED);
    }

}
