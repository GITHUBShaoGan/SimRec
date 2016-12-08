package com.slut.simrec.main.fragment.pswd.v;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.main.fragment.pswd.adapter.PswdAdapter;
import com.slut.simrec.main.fragment.pswd.adapter.PswdCatAdapter;
import com.slut.simrec.main.fragment.pswd.m.DataLoadType;
import com.slut.simrec.main.fragment.pswd.m.PassSortType;
import com.slut.simrec.main.fragment.pswd.p.PswdPresenter;
import com.slut.simrec.main.fragment.pswd.p.PswdPresenterImpl;
import com.slut.simrec.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A simple {@link Fragment} subclass.
 */
public class PswdFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PswdView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private View rootView;
    private static volatile PswdFragment instances;

    private LinearLayoutManager layoutManager;

    private static final long PAGE_SIZE = 10;
    private long pageNo = 1;

    private PswdPresenter presenter;
    private PswdAdapter pswdAdapter;
    private PswdCatAdapter pswdCatAdapter;

    private int sortType = PassSortType.CATEGORY;

    private List<Password> passwordList;
    private List<PassCat> passCatList;
    private List<List<Password>> passwordLists;

    public static PswdFragment getInstances() {
        if (instances == null) {
            synchronized (PswdFragment.class) {
                if (instances == null) {
                    instances = new PswdFragment();
                }
            }
        }
        return instances;
    }

    public PswdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passCatList = new ArrayList<>();
        passwordLists = new ArrayList<>();
        passwordList = new ArrayList<>();
        presenter = new PswdPresenterImpl(this);

        layoutManager = new LinearLayoutManager(getActivity());
        pswdAdapter = new PswdAdapter();
        pswdCatAdapter = new PswdCatAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pswd, container, false);
        }
        ButterKnife.bind(this, rootView);
        initView();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                PswdFragment.this.onRefresh();
            }
        }, 200);
    }

    public void switchSortType(int sortType) {
        this.sortType = sortType;
        refreshLayout.setRefreshing(true);
        PswdFragment.this.onRefresh();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        passwordList.clear();
        passCatList.clear();
        passwordLists.clear();
        if (sortType == PassSortType.CATEGORY) {
            recyclerView.setAdapter(pswdCatAdapter);
            presenter.loadPassCat(pageNo, PAGE_SIZE);
        } else {
            recyclerView.setAdapter(pswdAdapter);
            presenter.loadPass(sortType, pageNo, PAGE_SIZE);
        }
    }

    @Override
    public void onLoadSuccess(int type, List<Password> passwordList) {
        this.passwordList.addAll(passwordList);
        pswdAdapter.setPasswordList(this.passwordList);
        switch (type) {
            case DataLoadType.END:
                //没有更多数据了
                pswdAdapter.addFooterView();
                break;
            case DataLoadType.FINISH:
                //加载完成
                pswdAdapter.removeFooterView();
                break;
        }
        pswdAdapter.notifyDataSetChanged();
        pageNo++;
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadError(String msg) {
        ToastUtils.showShort(msg);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPassCatLoadSuccess(int type, List<PassCat> passCatList, List<List<Password>> passwordList) {
        this.passCatList.addAll(passCatList);
        this.passwordLists.addAll(passwordList);
        pswdCatAdapter.setPassCatList(this.passCatList);
        pswdCatAdapter.setPasswordList(this.passwordLists);
        switch (type) {
            case DataLoadType.END:
                pswdCatAdapter.addFooterView();
                break;
            case DataLoadType.FINISH:
                pswdCatAdapter.removeFooterView();
                break;
        }
        pswdCatAdapter.notifyDataSetChanged();
        pageNo++;
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPassCatLoadError(String msg) {
        ToastUtils.showShort(msg);
        refreshLayout.setRefreshing(false);
    }
}
