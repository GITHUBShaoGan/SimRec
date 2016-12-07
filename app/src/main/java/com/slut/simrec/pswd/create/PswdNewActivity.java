package com.slut.simrec.pswd.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.slut.simrec.R;
import com.slut.simrec.pswd.defaultcat.bean.DefaultCatBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PswdNewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.til_website)
    TextInputLayout tilWebSite;
    @BindView(R.id.til_remark)
    TextInputLayout tilRemark;

    public static final String EXTRA_DEFAULT_CAT = "default_cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd_new);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_DEFAULT_CAT)) {
                //传过来有目录信息
                DefaultCatBean defaultCatBean = intent.getParcelableExtra(EXTRA_DEFAULT_CAT);
                toolbar.setTitle(defaultCatBean.getTitle() + "");
                tilTitle.getEditText().setText(defaultCatBean.getTitle());
                tilWebSite.getEditText().setText(defaultCatBean.getWebsite());
                tilAccount.requestFocus();
            } else {
                //传过来没有目录信息
            }
        }
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
    }
}
