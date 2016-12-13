package com.slut.simrec.pswd.detail.v;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.select.v.CategoryOptionsActivity;
import com.slut.simrec.pswd.detail.p.PassDetailPresenter;
import com.slut.simrec.pswd.detail.p.PassDetailPresenterImpl;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.utils.SystemUtils;
import com.slut.simrec.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.webkit.WebSettings.PluginState.ON;
import static com.slut.simrec.pswd.create.v.PswdNewActivity.EXTRA_DEFAULT_CAT;

public class PassDetailActivity extends AppCompatActivity implements PassDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cat_avatar)
    ImageView catAvatar;
    @BindView(R.id.cat_title)
    TextView catTitle;
    @BindView(R.id.cat_url)
    TextView catURL;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.til_url)
    TextInputLayout tilURL;
    @BindView(R.id.til_remark)
    TextInputLayout tilRemark;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.title_copy)
    Button copyTitle;
    @BindView(R.id.account_copy)
    Button copyAccount;
    @BindView(R.id.password_copy)
    Button passwordCopy;
    @BindView(R.id.browser)
    ImageView browser;

    private Password password;
    private PassCat passCat;

    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_CAT = "category";
    private static final int REQUEST_PASSCAT_UUID = 1000;
    private Menu menu;

    private String originPassUUID = CategoryConst.UUID_UNSPECIFIC;
    private PassDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_CAT)) {
                passCat = intent.getParcelableExtra(EXTRA_CAT);
                if (passCat != null) {
                    ImageLoader.getInstance().displayImage(RSAUtils.decrypt(passCat.getCatIconUrl()), catAvatar, ImgLoaderOptions.init404Options());
                    catTitle.setText(RSAUtils.decrypt(passCat.getCatTitle()));
                    catURL.setText(RSAUtils.decrypt(passCat.getCatUrl()));

                    originPassUUID = passCat.getUuid();
                }
            }
            if (intent.hasExtra(EXTRA_PASSWORD)) {
                password = intent.getParcelableExtra(EXTRA_PASSWORD);
                if (password != null) {
                    String title = RSAUtils.decrypt(password.getTitle());
                    String account = RSAUtils.decrypt(password.getAccount());
                    if (TextUtils.isEmpty(title)) {
                        toolbar.setTitle(account);
                    } else {
                        toolbar.setTitle(title);
                    }
                    tilAccount.getEditText().setText(account);
                    tilPassword.getEditText().setText(RSAUtils.decrypt(password.getPassword()));
                    tilRemark.getEditText().setText(RSAUtils.decrypt(password.getRemark()));
                    tilURL.getEditText().setText(RSAUtils.decrypt(password.getWebsiteUrl()));
                    tilTitle.getEditText().setText(RSAUtils.decrypt(password.getTitle()));
                }
            }
        }
        presenter = new PassDetailPresenterImpl(this);
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

    @OnClick(R.id.title_copy)
    void onTitleCopy() {
        String title = tilTitle.getEditText().getText().toString().trim();
        SystemUtils.copy("account", title);
    }

    @OnClick(R.id.account_copy)
    void onAccountCopy() {
        String account = tilAccount.getEditText().getText().toString().trim();
        SystemUtils.copy("account", account);
    }

    @OnClick(R.id.password_copy)
    void onPasswordCopy() {
        String password = tilPassword.getEditText().getText().toString().trim();
        SystemUtils.copy("account", password);
    }

    @OnClick(R.id.browser)
    void onBrowserClick() {
        String url = tilURL.getEditText().getText().toString().trim();
        SystemUtils.openWebsite(this, url);
    }

    private void update() {
        String title = tilTitle.getEditText().getText().toString().trim();
        String account = tilAccount.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        String website = tilURL.getEditText().getText().toString().trim();
        String remark = tilRemark.getEditText().getText().toString().trim();
        presenter.update(title, account, password, website, remark, this.password, passCat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_detail, menu);
        menu.getItem(0).setEnabled(false);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category:
                Intent intent = new Intent(this, CategoryOptionsActivity.class);
                startActivityForResult(intent, REQUEST_PASSCAT_UUID);
                break;
            case R.id.edit:
                if (!tilTitle.getEditText().isEnabled()) {
                    this.menu.getItem(0).setEnabled(true);
                    item.setIcon(R.drawable.ic_done_white_24dp);
                    tilTitle.getEditText().setEnabled(true);
                    tilAccount.getEditText().setEnabled(true);
                    tilPassword.getEditText().setEnabled(true);
                    tilURL.getEditText().setEnabled(true);
                    tilRemark.getEditText().setEnabled(true);
                    tilTitle.getEditText().requestFocus();
                    tilTitle.getEditText().setSelection(tilTitle.getEditText().getText().length());
                    InputMethodManager inputManager =
                            (InputMethodManager) tilTitle.getEditText().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(tilTitle.getEditText(), 0);
                } else {
                    update();
                    this.menu.getItem(0).setEnabled(false);
                    item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                    tilTitle.getEditText().setEnabled(false);
                    tilAccount.getEditText().setEnabled(false);
                    tilPassword.getEditText().setEnabled(false);
                    tilURL.getEditText().setEnabled(false);
                    tilRemark.getEditText().setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // 得到InputMethodManager的实例
                    if (imm.isActive() && getCurrentFocus() != null) {
                        if (getCurrentFocus().getWindowToken() != null) {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_dialog_delete);
                builder.setMessage(R.string.msg_pass_detail_delete);
                builder.setIcon(R.drawable.ic_warning_amber_24dp);
                builder.setPositiveButton(R.string.action_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            PassDao.getInstances().deleteByUUID(password.getUuid());
                            finish();
                        } catch (Exception e) {
                            ToastUtils.showShort(R.string.delete_failed);
                        }
                    }
                });
                builder.setNegativeButton(R.string.action_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
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
                        passCat = data.getParcelableExtra(EXTRA_DEFAULT_CAT);
                        if (!TextUtils.equals(passCat.getUuid(), originPassUUID)) {
                            //如果选择的
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle(R.string.title_dialog_tips);
                            builder.setIcon(R.drawable.ic_warning_amber_24dp);
                            builder.setMessage(R.string.msg_pswd_new_category_change);
                            builder.setPositiveButton(R.string.action_dialog_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    toolbar.setTitle(RSAUtils.decrypt(passCat.getCatTitle()) + "");
                                    tilTitle.getEditText().setText(RSAUtils.decrypt(passCat.getCatTitle()));
                                    tilURL.getEditText().setText(RSAUtils.decrypt(passCat.getCatUrl()));
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
                        catTitle.setText(RSAUtils.decrypt(passCat.getCatTitle()));
                        catURL.setText(RSAUtils.decrypt(passCat.getCatUrl()));
                        ImageLoader.getInstance().displayImage(RSAUtils.decrypt(passCat.getCatIconUrl()), catAvatar, ImgLoaderOptions.init404Options());
                    }
                    break;
            }
        }
    }

    @Override
    public void onUpdateSuccess(Password password) {
        ToastUtils.showShort(R.string.update_success);
    }

    @Override
    public void onUpdateError(String msg) {
        ToastUtils.showShort(msg);
    }
}
