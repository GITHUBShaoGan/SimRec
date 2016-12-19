package com.slut.simrec.pswd.create.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.main.fragment.pswd.v.PswdFragment;
import com.slut.simrec.pswd.PassFatherActivity;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.select.v.CategoryOptionsActivity;
import com.slut.simrec.pswd.create.p.PswdNewPresenter;
import com.slut.simrec.pswd.create.p.PswdNewPresenterImpl;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PswdNewActivity extends PassFatherActivity implements PswdNewView {

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
    @BindView(R.id.cat_title)
    TextView catTitle;
    @BindView(R.id.cat_url)
    TextView catURL;
    @BindView(R.id.cat_avatar)
    ImageView catAvatar;

    public static final String EXTRA_DEFAULT_CAT = "default_cat";
    private PassCat originPassCat = null;

    private String originPassUUID = CategoryConst.UUID_UNSPECIFIC;

    private PswdNewPresenter presenter;

    private static final int REQUEST_PASSCAT_UUID = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswd_new);
        ButterKnife.bind(this);
        App.getInstances().addActivity(this);
        initView();
        initListener();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_DEFAULT_CAT)) {
                //传过来有目录信息
                originPassCat = intent.getParcelableExtra(EXTRA_DEFAULT_CAT);
                if (originPassCat != null) {
                    toolbar.setTitle(originPassCat.getCatTitle() + "");
                    tilTitle.getEditText().setText(originPassCat.getCatTitle());
                    tilWebSite.getEditText().setText(originPassCat.getCatUrl());
                    tilAccount.requestFocus();
                    catTitle.setText(originPassCat.getCatTitle());
                    catURL.setText(originPassCat.getCatUrl());
                    ImageLoader.getInstance().displayImage(originPassCat.getCatIconUrl(), catAvatar, ImgLoaderOptions.init404Options());

                    originPassUUID = originPassCat.getUuid();
                } else {
                    originPassUUID = CategoryConst.UUID_UNSPECIFIC;

                    catTitle.setText(CategoryConst.TITLE_UNSPECIFIC);
                    catURL.setText(CategoryConst.URL_UNSPECIFIC);
                    ImageLoader.getInstance().displayImage(CategoryConst.ICONURL_UNSPECIFIC, catAvatar, ImgLoaderOptions.init404Options());
                }
            } else {
                //传过来没有目录信息
                originPassUUID = CategoryConst.UUID_UNSPECIFIC;

                catTitle.setText(CategoryConst.TITLE_UNSPECIFIC);
                catURL.setText(CategoryConst.URL_UNSPECIFIC);
                ImageLoader.getInstance().displayImage(CategoryConst.ICONURL_UNSPECIFIC, catAvatar, ImgLoaderOptions.init404Options());
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new PswdNewPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClick();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackClick();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onBackClick() {
        String title = tilTitle.getEditText().getText().toString().trim();
        String account = tilAccount.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        String website = tilWebSite.getEditText().getText().toString().trim();
        String remark = tilRemark.getEditText().getText().toString().trim();
        presenter.onBackClick(title, account, password, website, remark, originPassCat, originPassUUID);
    }

    private void save() {
        String title = tilTitle.getEditText().getText().toString().trim();
        String account = tilAccount.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        String website = tilWebSite.getEditText().getText().toString().trim();
        String remark = tilRemark.getEditText().getText().toString().trim();
        presenter.save(title, account, password, website, remark, originPassUUID);
    }

    @Override
    public void onUIChange() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_pswd_new_exitwithoutsave);
        builder.setMessage(R.string.msg_pswd_new_exitwithoutsave);
        builder.setIcon(R.drawable.ic_warning_amber_24dp);
        builder.setPositiveButton(R.string.action_dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //保存数据
                save();
            }
        });
        builder.setNegativeButton(R.string.action_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onUINotChange() {
        finish();
    }

    @Override
    public void onPswdSaveSuccess(Password password) {
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
        PswdFragment.getInstances().updateSinglePass(password);
    }

    @Override
    public void onPswdSaveError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pswd_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                save();
                break;
            case R.id.category:
                Intent intent = new Intent(this, CategoryOptionsActivity.class);
                startActivityForResult(intent, REQUEST_PASSCAT_UUID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PASSCAT_UUID:
                    if (data != null) {
                        final PassCat passCat = data.getParcelableExtra(EXTRA_DEFAULT_CAT);
                        if (!TextUtils.equals(passCat.getUuid(), originPassUUID)) {
                            //如果选择的
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle(R.string.title_dialog_tips);
                            builder.setIcon(R.drawable.ic_warning_amber_24dp);
                            builder.setMessage(R.string.msg_pswd_new_category_change);
                            builder.setPositiveButton(R.string.action_dialog_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    toolbar.setTitle(passCat.getCatTitle() + "");
                                    tilTitle.getEditText().setText(passCat.getCatTitle());
                                    tilWebSite.getEditText().setText(passCat.getCatUrl());
                                    tilAccount.getEditText().requestFocus();
                                }
                            });
                            builder.setNegativeButton(R.string.action_dialog_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                        originPassUUID = passCat.getUuid();
                        catTitle.setText(passCat.getCatTitle());
                        catURL.setText(passCat.getCatUrl());
                        ImageLoader.getInstance().displayImage(passCat.getCatIconUrl(), catAvatar, ImgLoaderOptions.init404Options());
                    }
                    break;
            }
        }
    }
}
