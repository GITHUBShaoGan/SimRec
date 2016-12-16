package com.slut.simrec.pswd.unlock.text.v;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.pswd.unlock.text.p.TextUnlockPresenter;
import com.slut.simrec.pswd.unlock.text.p.TextUnlockPresenterImpl;
import com.slut.simrec.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextUnlockActivity extends AppCompatActivity implements TextUnlockView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    private TextUnlockPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_unlock);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new TextUnlockPresenterImpl(this);
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
        tilPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 4 && editable.length() <= 128) {
                    submit.setClickable(true);
                    submit.setEnabled(true);
                } else {
                    submit.setClickable(false);
                    submit.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.submit)
    void onSubmitClick() {
        String password = tilPassword.getEditText().getText().toString().trim();
        presenter.validate(password);
    }

    @Override
    public void onValidateSuccess() {
        App.setIsPswdFunctionLocked(false);
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
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
    public void onValidateFailed() {
        ToastUtils.showShort("failed");
    }

    @Override
    public void onValidateError(String msg) {
        ToastUtils.showShort(msg);
    }
}
