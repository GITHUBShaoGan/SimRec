package com.slut.simrec.pswd.master.text.v;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.slut.simrec.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextPassActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.til_confirm)
    TextInputLayout tilConfirm;
    @BindView(R.id.submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pass);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                if (editable.length() >= 4 && editable.length() <= 128) {
                } else {

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
                if (password.equals(confirm)) {
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

    }
}
