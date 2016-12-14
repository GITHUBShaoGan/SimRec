package com.slut.simrec.pswd.category.detail.v;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.main.fragment.pswd.v.PswdFragment;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.detail.adapter.PswdAdapter;
import com.slut.simrec.pswd.category.detail.p.CatDetailPresenter;
import com.slut.simrec.pswd.category.detail.p.CatDetailPresenterImpl;
import com.slut.simrec.pswd.create.v.PswdNewActivity;
import com.slut.simrec.pswd.detail.v.PassDetailActivity;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.utils.TimeUtils;
import com.slut.simrec.utils.ToastUtils;
import com.slut.simrec.widget.CircleTextImageView;

import java.sql.Time;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassCatDetailActivity extends AppCompatActivity implements CatDetailView, SwipeRefreshLayout.OnRefreshListener, PswdAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.avatar)
    CircleTextImageView avatar;
    @BindView(R.id.website)
    TextView website;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private LinearLayoutManager layoutManager;
    private PswdAdapter pswdAdapter;

    private CatDetailPresenter presenter;

    private PassCat passCat;
    public static final String EXTRA_PASS_CAT = "pass_cat";
    private static final int REQUEST_CREATE_PSWD = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_cat_detail);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_PASS_CAT)) {
                passCat = intent.getParcelableExtra(EXTRA_PASS_CAT);
                if (passCat != null) {
                    toolbar.setTitle(passCat.getCatTitle());
                    ImageLoader.getInstance().displayImage(passCat.getCatIconUrl(), avatar, ImgLoaderOptions.init404Options());
                    website.setText(passCat.getCatUrl());
                    title.setText(passCat.getCatTitle());
                }
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pswdAdapter = new PswdAdapter();
        pswdAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(pswdAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                PassCatDetailActivity.this.onRefresh();
            }
        }, 200);

        presenter = new CatDetailPresenterImpl(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pswd_cat_detail, menu);
        if (passCat != null) {
            if (passCat.getUuid().equals(CategoryConst.UUID_UNSPECIFIC)) {
                menu.removeItem(R.id.delete);
                menu.removeItem(R.id.edit);
            }
        } else {
            menu.removeItem(R.id.add);
            menu.removeItem(R.id.delete);
            menu.removeItem(R.id.edit);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                if (passCat != null) {
                    Intent intent = new Intent(this, PswdNewActivity.class);
                    if (!passCat.getUuid().equals(CategoryConst.UUID_UNSPECIFIC)) {
                        intent.putExtra(PswdNewActivity.EXTRA_DEFAULT_CAT, passCat);
                    }
                    startActivityForResult(intent, REQUEST_CREATE_PSWD);
                }
                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.ic_warning_amber_24dp);
                builder.setTitle(R.string.title_dialog_delete);
                builder.setMessage(R.string.msg_dialog_delete);
                builder.setPositiveButton(R.string.action_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.delete(CatDeleteType.DELETE_CAT_AND_PASSWORD, passCat);
                    }
                });
                builder.setNegativeButton(R.string.action_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.delete(CatDeleteType.DELETE_CAT_ONLY, passCat);
                    }
                });
                builder.setNeutralButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
            case R.id.edit:
                if (passCat != null) {
                    View newDialogView = LayoutInflater.from(this).inflate(R.layout.view_dialog_add_category, new LinearLayout(this), false);
                    final TextInputLayout tilTitle = (TextInputLayout) newDialogView.findViewById(R.id.til_title);
                    final TextInputLayout tilUrl = (TextInputLayout) newDialogView.findViewById(R.id.til_url);
                    final TextInputLayout tilIconUrl = (TextInputLayout) newDialogView.findViewById(R.id.til_iconurl);
                    tilTitle.getEditText().setText(passCat.getCatTitle() + "");
                    tilUrl.getEditText().setText(passCat.getCatUrl() + "");
                    tilIconUrl.getEditText().setText(passCat.getCatIconUrl() + "");
                    final AlertDialog.Builder editBuilder = new AlertDialog.Builder(this);
                    editBuilder.setView(newDialogView);
                    editBuilder.setTitle(R.string.title_dialog_edit);
                    editBuilder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String title = tilTitle.getEditText().getText().toString().trim();
                            String url = tilUrl.getEditText().getText().toString().trim();
                            String iconUrl = tilIconUrl.getEditText().getText().toString().trim();
                            presenter.edit(passCat, title, url, iconUrl);
                        }
                    });
                    editBuilder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    Dialog dialog = editBuilder.create();
                    dialog.show();
                    final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                    tilTitle.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String name = editable.toString().trim();
                            if (name.isEmpty())
                                positiveButton.setEnabled(false);
                            else
                                positiveButton.setEnabled(true);
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadSuccess(int type, List<Password> passwordList) {
        pswdAdapter.setPasswordList(passwordList);
        pswdAdapter.addFooter();
        pswdAdapter.notifyDataSetChanged();

        refreshLayout.setRefreshing(false);

        switchUI();
    }

    @Override
    public void onLoadError(String msg) {
        ToastUtils.showShort(msg);
        refreshLayout.setRefreshing(false);

        switchUI();
    }

    @Override
    public void onDeleteSuccess(PassCat passCat, int deleteType) {
        finish();
        PswdFragment.getInstances().deleteSingleCat(passCat, deleteType);
    }

    @Override
    public void onDeleteError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onEditSuccess(PassCat passCat) {
        if (passCat != null) {
            this.passCat = passCat;
            toolbar.setTitle(passCat.getCatTitle());
            ImageLoader.getInstance().displayImage(passCat.getCatIconUrl(), avatar, ImgLoaderOptions.init404Options());
            website.setText(passCat.getCatUrl());
            title.setText(passCat.getCatTitle());
        }
        PswdFragment.getInstances().updateSingleCat(passCat);
    }

    @Override
    public void onEditError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onRefresh() {
        presenter.loadData(passCat);
    }

    private void switchUI() {
        if (pswdAdapter != null) {
            List<Password> passwordList = pswdAdapter.getPasswordList();
            if (passwordList != null && !passwordList.isEmpty()) {
                empty.setVisibility(View.INVISIBLE);
                refreshLayout.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.INVISIBLE);
            }
        } else {
            empty.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_PSWD:
                    onRefresh();
                    break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Password password = pswdAdapter.getPasswordList().get(position);
        Intent intentForPassDetail = new Intent(this, PassDetailActivity.class);
        intentForPassDetail.putExtra(PassDetailActivity.EXTRA_PASSWORD, password);
        intentForPassDetail.putExtra(PassDetailActivity.EXTRA_CAT, passCat);
        startActivity(intentForPassDetail);
    }
}
