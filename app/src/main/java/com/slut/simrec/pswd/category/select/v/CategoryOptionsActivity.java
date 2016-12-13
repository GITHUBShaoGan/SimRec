package com.slut.simrec.pswd.category.select.v;

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

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.pswd.category.select.adapter.OptionAdapter;
import com.slut.simrec.pswd.category.select.m.LoadMoreType;
import com.slut.simrec.pswd.category.select.p.OptionPresenter;
import com.slut.simrec.pswd.category.select.p.OptionPresenterImpl;
import com.slut.simrec.pswd.create.v.PswdNewActivity;
import com.slut.simrec.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryOptionsActivity extends AppCompatActivity implements OptionView, SwipeRefreshLayout.OnRefreshListener, OptionAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private OptionPresenter presenter;
    private OptionAdapter adapter;

    private List<PassCat> passCatList;
    private long pageNo = 1;
    private static final long pageSize = 10;

    private int lastVisibleItem = 0;

    public static final String EXTRA_ROOT_PATH = "root_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_options);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new OptionPresenterImpl(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OptionAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        passCatList = new ArrayList<>();

        presenter = new OptionPresenterImpl(this);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                CategoryOptionsActivity.this.onRefresh();
            }
        }, 200);

    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    refreshLayout.setRefreshing(true);
                    presenter.loadMore(pageNo, pageSize);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                View newDialogView = LayoutInflater.from(this).inflate(R.layout.view_dialog_add_category, new LinearLayout(this), false);
                final TextInputLayout tilTitle = (TextInputLayout) newDialogView.findViewById(R.id.til_title);
                final TextInputLayout tilUrl = (TextInputLayout) newDialogView.findViewById(R.id.til_url);
                final TextInputLayout tilIconUrl = (TextInputLayout) newDialogView.findViewById(R.id.til_iconurl);
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(newDialogView);
                builder.setTitle(R.string.title_dialog_add);
                builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = tilTitle.getEditText().getText().toString().trim();
                        String url = tilUrl.getEditText().getText().toString().trim();
                        String iconUrl = tilIconUrl.getEditText().getText().toString().trim();
                        presenter.create(title, url, iconUrl);
                    }
                });
                builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setEnabled(false);
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateSuccess(PassCat passCat) {
        this.passCatList.add(0, passCat);
        adapter.setPassCatList(this.passCatList);
        adapter.notifyItemInserted(0);
    }

    @Override
    public void onCreateError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onLoadMoreSuccess(int type, List<PassCat> passCatList) {
        this.passCatList.addAll(passCatList);
        adapter.setPassCatList(this.passCatList);
        if (type == LoadMoreType.TYPE_END) {
            //没有数据了
            adapter.addFooter();
        } else {
            adapter.removeFooter();
        }
        adapter.notifyDataSetChanged();
        pageNo++;
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        passCatList.clear();
        presenter.loadMore(pageNo, pageSize);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = getIntent();
        if (intent != null) {
            intent.putExtra(PswdNewActivity.EXTRA_DEFAULT_CAT, adapter.getPassCatList().get(position));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
