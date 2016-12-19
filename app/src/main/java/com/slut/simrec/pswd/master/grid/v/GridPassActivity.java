package com.slut.simrec.pswd.master.grid.v;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.pswd.master.grid.p.GridPassPresenter;
import com.slut.simrec.pswd.master.grid.p.GridPassPresenterImpl;
import com.slut.simrec.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridPassActivity extends AppCompatActivity implements GridPassView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.passview)
    GridPasswordView gridPasswordView;
    @BindView(R.id.showorhide)
    CheckBox showOrHide;

    private int count = 0;
    private GridPassPresenter presenter;
    private String firstPsw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_pass);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridPasswordView.setPasswordType(PasswordType.NUMBER);

        presenter = new GridPassPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() < 6) {
                    tips.setTextColor(Color.BLACK);
                    if (count == 1) {
                        tips.setText(R.string.tips_grid_pass_confirm_input);
                    }
                    if (count == 0) {
                        tips.setText(R.string.tips_grid_pass_first_input);
                    }
                }
            }

            @Override
            public void onInputFinish(String psw) {
                if (count == 1) {
                    //第二次输入完成
                    if (psw.equals(firstPsw)) {
                        presenter.createPass(psw);
                    } else {
                        tips.setTextColor(Color.RED);
                        tips.setText(R.string.error_pass_not_equal);
                    }
                }
                if (count == 0) {
                    firstPsw = psw;
                    gridPasswordView.clearPassword();
                    tips.setTextColor(Color.BLACK);
                    tips.setText(R.string.tips_grid_pass_confirm_input);
                    count++;
                }
            }
        });
        showOrHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    gridPasswordView.setPasswordVisibility(true);
                } else {
                    gridPasswordView.setPasswordVisibility(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_pass, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                //重置
                gridPasswordView.clearPassword();
                count = 0;
                tips.setTextColor(Color.BLACK);
                tips.setText(R.string.tips_grid_pass_first_input);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreatePassSuccess() {
        //设置密码成功,解锁密码功能
        App.setIsPswdFunctionLocked(false);
        ToastUtils.showShort(R.string.toast_master_set_successfully);
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCreatePassError(String msg) {
        tips.setText(msg);
        tips.setTextColor(Color.RED);
    }
}
