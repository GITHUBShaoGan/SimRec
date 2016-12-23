package com.slut.simrec.pswd.category.defaultcat.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.pswd.category.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.pswd.category.defaultcat.v.DefaultCatView;
import com.slut.simrec.pswd.category.defaultcat.m.DefaultCatModel;
import com.slut.simrec.pswd.category.defaultcat.m.DefaultCatModelImpl;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class DefaultCatPresenterImpl implements DefaultCatPresenter, DefaultCatModel.OnDataLoadListener,DefaultCatModel.OnItemClickListener {

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

    @Override
    public void insertCat(int position, DefaultCatBean defaultCatBean) {
        defaultCatModel.insertCat(position,defaultCatBean,this);
    }

    @Override
    public void onItemClick(boolean isExists,PassCat passCat, int position) {
        defaultCatView.onItemClick(isExists,passCat,position);
    }
}
