package com.slut.simrec.pswd.category.defaultcat.v;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.main.fragment.pswd.v.PswdFragment;
import com.slut.simrec.pswd.category.defaultcat.adapter.DefaultCatAdapter;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.pswd.category.defaultcat.p.DefaultCatPresenter;
import com.slut.simrec.pswd.category.defaultcat.p.DefaultCatPresenterImpl;
import com.slut.simrec.pswd.create.v.PswdNewActivity;
import com.slut.simrec.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefaultCatActivity extends AppCompatActivity implements DefaultCatView, DefaultCatAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private DefaultCatAdapter adapter;

    private DefaultCatPresenter presenter;
    private static final int REQUEST_CREATE_PSWD = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_cat);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        adapter = new DefaultCatAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new DefaultCatPresenterImpl(this);
        presenter.loadData();
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
    public void onDataLoadSuccess(List<DefaultCatBean> defaultCatBeanList) {
        adapter.setDefaultCatBeanList(defaultCatBeanList);
        adapter.notifyDataSetChanged();

        adapter.addFooterView();
    }

    @Override
    public void onDataLoadError(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 用户点击列表，此时应该刷新主界面PswdFragment
     *
     * @param passCat
     * @param position
     */
    @Override
    public void onItemClick(PassCat passCat, int position) {
        PswdFragment.getInstances().insertSingleCat(passCat);
        Intent intent = new Intent(this, PswdNewActivity.class);
        intent.putExtra(PswdNewActivity.EXTRA_DEFAULT_CAT, passCat);
        startActivityForResult(intent, REQUEST_CREATE_PSWD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.default_cat, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip:
                Intent intent = new Intent(this, PswdNewActivity.class);
                startActivityForResult(intent, REQUEST_CREATE_PSWD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.insertCat(position, adapter.getDefaultCatBeanList().get(position));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_PSWD:
                    Intent intent = getIntent();
                    if (intent != null) {
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
            }
        }
    }
}
