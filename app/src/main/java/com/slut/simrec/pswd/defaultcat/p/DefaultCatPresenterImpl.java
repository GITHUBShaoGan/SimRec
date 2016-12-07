package com.slut.simrec.pswd.defaultcat.p;

import com.slut.simrec.pswd.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.pswd.defaultcat.m.DefaultCatModel;
import com.slut.simrec.pswd.defaultcat.m.DefaultCatModelImpl;
import com.slut.simrec.pswd.defaultcat.v.DefaultCatView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class DefaultCatPresenterImpl implements DefaultCatPresenter, DefaultCatModel.OnDataLoadListener {

    private DefaultCatModel defaultCatModel;
    private DefaultCatView defaultCatView;

    public DefaultCatPresenterImpl(DefaultCatView defaultCatView) {
        this.defaultCatView = defaultCatView;
        this.defaultCatModel = new DefaultCatModelImpl();
    }

    @Override
    public void onDataLoadSuccess(List<DefaultCatBean> defaultCatBeanList) {
        defaultCatView.onDataLoadSuccess(defaultCatBeanList);
    }

    @Override
    public void onDataLoadError(String msg) {
        defaultCatView.onDataLoadError(msg);
    }

    @Override
    public void loadData() {
        defaultCatModel.loadData(this);
    }
}
