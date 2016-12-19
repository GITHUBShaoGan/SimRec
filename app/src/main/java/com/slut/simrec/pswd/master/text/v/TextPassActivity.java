package com.slut.simrec.pswd.master.text.v;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.pswd.master.text.p.TextPassPresenter;
import com.slut.simrec.pswd.master.text.p.TextPassPresenterImpl;
import com.slut.simrec.utils.ResUtils;
import com.slut.simrec.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.password;

public class TextPassActivity extends AppCompatActivity implements TextPassView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.til_confirm)
    TextInputLayout tilConfirm;
    @BindView(R.id.submit)
    Button submit;

    private TextPassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pass);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new TextPassPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tilPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = tilConfirm.getEditText().getText().toString();
                String confirm = editable.toString();
                if (editable.length() >= 4 && editable.length() <= 128) {
                    tilPassword.setError("");
                    if (password.equals(confirm)) {
                        submit.setClickable(true);
                        submit.setEnabled(true);
                    } else {
                        submit.setClickable(false);
                        submit.setEnabled(false);
                    }
                } else {
                    tilPassword.setError(ResUtils.getString(R.string.error_password_length));
                    submit.setClickable(false);
                    submit.setEnabled(false);
                }

            }
        });
        tilConfirm.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = tilPassword.getEditText().getText().toString();
                String confirm = editable.toString();
                if (password.equals(confirm) && editable.length() >= 4 && editable.length() <= 128) {
                    submit.setClickable(true);
                    submit.setEnabled(true);
                    tilConfirm.setError("");
                } else {
                    submit.setClickable(false);
                    submit.setEnabled(false);
                    tilConfirm.setError(ResUtils.getString(R.string.error_two_input_notequal));
                }
            }
        });
    }

    @OnClick(R.id.submit)
    void onSubmitClick() {
        String password = tilPassword.getEditText().getText().toString().trim();
        presenter.createPass(password);
    }

    @Override
    public void onCreateSuccess() {
        App.setIsPswdFunctionLocked(false);
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onCreateError(String msg) {
        ToastUtils.showShort(msg);
    }
}
