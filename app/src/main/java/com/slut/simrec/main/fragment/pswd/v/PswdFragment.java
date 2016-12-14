package com.slut.simrec.main.fragment.pswd.v;


import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.fingerprint.FingerprintHelper;
import com.slut.simrec.fingerprint.OnFingerPrintAuthListener;
import com.slut.simrec.main.fragment.pswd.adapter.PswdCatAdapter;
import com.slut.simrec.main.fragment.pswd.m.DataLoadType;
import com.slut.simrec.main.fragment.pswd.p.PswdPresenter;
import com.slut.simrec.main.fragment.pswd.p.PswdPresenterImpl;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.detail.v.PassCatDetailActivity;
import com.slut.simrec.pswd.create.v.PswdNewActivity;
import com.slut.simrec.pswd.detail.v.PassDetailActivity;
import com.slut.simrec.pswd.unlock.grid.v.GridUnlockActivity;
import com.slut.simrec.pswd.unlock.pattern.PatternUnlockActivity;
import com.slut.simrec.pswd.unlock.text.v.TextUnlockActivity;
import com.slut.simrec.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PswdFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PswdView, PswdCatAdapter.OnItemClickListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private View rootView;
    private static volatile PswdFragment instances;

    private LinearLayoutManager layoutManager;

    private static final long PAGE_SIZE = 10;
    private long pageNo = 1;

    private PswdPresenter presenter;
    private PswdCatAdapter pswdCatAdapter;

    private List<PassCat> passCatList;
    private List<List<Password>> passwordLists;

    private PassCat selectAddPassCat = null;
    private PassCat selectItemPassCat = null;
    private Password selectPassword = null;
    private PassCat selectPassItemCat = null;

    private static final int REQUEST_CREATE_PASSWORD = 1000;
    private static final int REQUEST_ADD_UNLOCK = 2000;
    private static final int REQUEST_CAT_ITEM_UNLOCK = 3000;
    private static final int REQUEST_PASS_ITEM_UNLOCK = 4000;

    private int lastVisibleItem = 0;

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
        presenter = new PswdPresenterImpl(this);

        layoutManager = new LinearLayoutManager(getActivity());
        pswdCatAdapter = new PswdCatAdapter(getActivity());
        pswdCatAdapter.setOnItemClickListener(this);
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
        initListener();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        recyclerView.setAdapter(pswdCatAdapter);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                PswdFragment.this.onRefresh();
            }
        }, 200);
    }

    private void initListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == pswdCatAdapter.getItemCount()) {
                        refreshLayout.setRefreshing(true);
                        presenter.loadPassCat(pageNo, PAGE_SIZE);
                    }
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
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        pageNo = 1;
        passCatList.clear();
        passwordLists.clear();
        presenter.loadPassCat(pageNo, PAGE_SIZE);
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

        switchUI();
    }

    @Override
    public void onPassCatLoadError(String msg) {
        ToastUtils.showShort(msg);
        refreshLayout.setRefreshing(false);

        switchUI();
    }

    public void insertSingleCat(PassCat passCat) {
        presenter.insertSingleCat(passCat, pswdCatAdapter.getPassCatList(),pswdCatAdapter.getPasswordList());
    }

    @Override
    public void onInsertSingleCatSuccess(List<PassCat> passCatList,List<List<Password>> passwords) {
        List<PassCat> passCats = pswdCatAdapter.getPassCatList();
        if (passCats != null && passCatList != null) {
            int count = passCatList.size() - passCats.size();
            this.passCatList = passCatList;
            pswdCatAdapter.setPassCatList(this.passCatList);
            switch (count) {
                case 1:
                    pswdCatAdapter.notifyItemInserted(1);
                    break;
                case 2:
                    pswdCatAdapter.notifyItemInserted(0);
                    pswdCatAdapter.notifyItemInserted(1);
                    break;
            }
        }
        switchUI();
    }

    @Override
    public void onInsertSingleCatError(String msg) {

    }

    /**
     * 删除单条分类
     *
     * @param passCat
     * @param deleteType
     */
    public void deleteSingleCat(PassCat passCat, int deleteType) {
        presenter.deleteSingleCat(passCat, deleteType, pswdCatAdapter.getPassCatList(), pswdCatAdapter.getPasswordList());
    }

    @Override
    public void onDeleteSingleCatSuccess(List<PassCat> passCatList, List<List<Password>> passwords, int deletePosition) {
        this.passwordLists = passwords;
        this.passCatList = passCatList;
        pswdCatAdapter.setPassCatList(this.passCatList);
        pswdCatAdapter.setPasswordList(this.passwordLists);
        pswdCatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteSingleCatError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onAddClick(View view, int position) {
        PassCat passCat = pswdCatAdapter.getPassCatList().get(position);
        selectAddPassCat = passCat;
        if (!App.isPswdFunctionLocked()) {
            if (passCat != null) {
                if (passCat.getUuid().equals(CategoryConst.UUID_UNSPECIFIC)) {
                    Intent intent = new Intent(getActivity(), PswdNewActivity.class);
                    startActivityForResult(intent, REQUEST_CREATE_PASSWORD);
                } else {
                    Intent intent = new Intent(getActivity(), PswdNewActivity.class);
                    intent.putExtra(PswdNewActivity.EXTRA_DEFAULT_CAT, passCat);
                    startActivityForResult(intent, REQUEST_CREATE_PASSWORD);
                }
            }
        } else {
            startUnlock(REQUEST_ADD_UNLOCK);
        }
    }

    @Override
    public void onCatItemClick(View view, int position) {
        PassCat passCat = pswdCatAdapter.getPassCatList().get(position);
        selectItemPassCat = passCat;
        if (!App.isPswdFunctionLocked()) {
            if (passCat != null) {
                Intent intent = new Intent(getActivity(), PassCatDetailActivity.class);
                intent.putExtra(PassCatDetailActivity.EXTRA_PASS_CAT, passCat);
                startActivity(intent);
            }
        } else {
            startUnlock(REQUEST_CAT_ITEM_UNLOCK);
        }
    }

    @Override
    public void onPasswordItemClick(View view, int catPosition, int passPos) {
        Password password = pswdCatAdapter.getPasswordList().get(catPosition).get(passPos);
        selectPassword = password;
        selectPassItemCat = pswdCatAdapter.getPassCatList().get(catPosition);
        if (!App.isPswdFunctionLocked()) {
            if (password != null) {
                Intent intent = new Intent(getActivity(), PassDetailActivity.class);
                intent.putExtra(PassDetailActivity.EXTRA_PASSWORD, password);
                intent.putExtra(PassDetailActivity.EXTRA_CAT, selectPassItemCat);
                startActivity(intent);
            }
        } else {
            startUnlock(REQUEST_PASS_ITEM_UNLOCK);
        }
    }

    /**
     * 开始进行解锁
     *
     * @param requestCode
     */
    private void startUnlock(final int requestCode) {
        Intent intent = null;
        int lockType = PassConfigDao.getInstances().queryLockType();
        switch (lockType) {
            case PassConfig.LockType.GRID:
                intent = new Intent(getActivity(), GridUnlockActivity.class);
                startActivityForResult(intent, requestCode);
                break;
            case PassConfig.LockType.PATTERN:
                intent = new Intent(getActivity(), PatternUnlockActivity.class);
                startActivityForResult(intent, requestCode);
                break;
            case PassConfig.LockType.TEXT:
                intent = new Intent(getActivity(), TextUnlockActivity.class);
                startActivityForResult(intent, requestCode);
                break;
            case PassConfig.LockType.FINGERPRINT:
                FingerprintHelper.getInstances().validate(getActivity(), new OnFingerPrintAuthListener() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {

                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

                    }

                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        App.setIsPswdFunctionLocked(false);
                        switch (requestCode) {
                            case REQUEST_CREATE_PASSWORD:
                                onRefresh();
                                break;
                            case REQUEST_ADD_UNLOCK:
                                Intent intent = new Intent(getActivity(), PswdNewActivity.class);
                                if (selectAddPassCat != null && selectAddPassCat.getUuid() != CategoryConst.UUID_UNSPECIFIC) {
                                    intent.putExtra(PswdNewActivity.EXTRA_DEFAULT_CAT, selectAddPassCat);
                                }
                                startActivityForResult(intent, REQUEST_CREATE_PASSWORD);
                                break;
                            case REQUEST_CAT_ITEM_UNLOCK:
                                Intent intentForCatDetail = new Intent(getActivity(), PassCatDetailActivity.class);
                                intentForCatDetail.putExtra(PassCatDetailActivity.EXTRA_PASS_CAT, selectItemPassCat);
                                startActivity(intentForCatDetail);
                                break;
                            case REQUEST_PASS_ITEM_UNLOCK:
                                Intent intentForPassDetail = new Intent(getActivity(), PassDetailActivity.class);
                                intentForPassDetail.putExtra(PassDetailActivity.EXTRA_PASSWORD, selectPassword);
                                intentForPassDetail.putExtra(PassDetailActivity.EXTRA_CAT, selectPassItemCat);
                                startActivity(intentForPassDetail);
                                break;
                        }
                    }

                    @Override
                    public void onAuthenticationFailed() {

                    }
                });
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_PASSWORD:
                    onRefresh();
                    break;
                case REQUEST_ADD_UNLOCK:
                    Intent intent = new Intent(getActivity(), PswdNewActivity.class);
                    if (selectAddPassCat != null && selectAddPassCat.getUuid() != CategoryConst.UUID_UNSPECIFIC) {
                        intent.putExtra(PswdNewActivity.EXTRA_DEFAULT_CAT, selectAddPassCat);
                    }
                    startActivityForResult(intent, REQUEST_CREATE_PASSWORD);
                    break;
                case REQUEST_CAT_ITEM_UNLOCK:
                    Intent intentForCatDetail = new Intent(getActivity(), PassCatDetailActivity.class);
                    intentForCatDetail.putExtra(PassCatDetailActivity.EXTRA_PASS_CAT, selectItemPassCat);
                    startActivity(intentForCatDetail);
                    break;
                case REQUEST_PASS_ITEM_UNLOCK:
                    Intent intentForPassDetail = new Intent(getActivity(), PassDetailActivity.class);
                    intentForPassDetail.putExtra(PassDetailActivity.EXTRA_PASSWORD, selectPassword);
                    intentForPassDetail.putExtra(PassDetailActivity.EXTRA_CAT, selectPassItemCat);
                    startActivity(intentForPassDetail);
                    break;
            }
        }
    }

    private void switchUI() {
        if (pswdCatAdapter != null) {
            List<PassCat> passCatList = pswdCatAdapter.getPassCatList();
            if (passCatList == null || passCatList.isEmpty()) {
                refreshLayout.setVisibility(View.INVISIBLE);
                empty.setVisibility(View.VISIBLE);
            } else {
                refreshLayout.setVisibility(View.VISIBLE);
                empty.setVisibility(View.INVISIBLE);
            }
        } else {
            refreshLayout.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
        }
    }

}
