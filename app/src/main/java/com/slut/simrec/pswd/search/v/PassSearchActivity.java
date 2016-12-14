package com.slut.simrec.pswd.search.v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.detail.v.PassDetailActivity;
import com.slut.simrec.pswd.search.adapter.PassSearchAdapter;
import com.slut.simrec.pswd.search.p.PassSearchPresenter;
import com.slut.simrec.pswd.search.p.PassSearchPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassSearchActivity extends AppCompatActivity implements PassSearchView, PassSearchAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private LinearLayoutManager layoutManager;
    private PassSearchAdapter adapter;

    private SearchView mSearchView;

    private PassSearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_search);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new PassSearchPresenterImpl(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PassSearchAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.pass_search, menu);
        final MenuItem item = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchSuccess(List<Password> passwordList, List<PassCat> passCatList) {
        adapter.setPasswordList(passwordList);
        adapter.setPassCatList(passCatList);
        adapter.notifyDataSetChanged();

        adapter.addFooter();

        switchUI();
    }

    private void switchUI() {
        if (adapter != null) {
            List<Password> passwordList = adapter.getPasswordList();
            if (passwordList == null || passwordList.isEmpty()) {
                recyclerView.setVisibility(View.INVISIBLE);
                empty.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                empty.setVisibility(View.INVISIBLE);
            }
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSearchError(String msg) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Password password = adapter.getPasswordList().get(position);
        PassCat passCat = adapter.getPassCatList().get(position);
        Intent intentForPassDetail = new Intent(this, PassDetailActivity.class);
        intentForPassDetail.putExtra(PassDetailActivity.EXTRA_PASSWORD, password);
        intentForPassDetail.putExtra(PassDetailActivity.EXTRA_CAT, passCat);
        startActivity(intentForPassDetail);
    }
}
