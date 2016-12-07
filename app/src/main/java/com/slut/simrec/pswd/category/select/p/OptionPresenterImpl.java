package com.slut.simrec.pswd.category.select.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.pswd.category.select.m.OptionModel;
import com.slut.simrec.pswd.category.select.m.OptionModelImpl;
import com.slut.simrec.pswd.category.select.v.OptionView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class OptionPresenterImpl implements OptionPresenter, OptionModel.OnCreateCatListener, OptionModel.OnLoadMoreListener {

    private OptionModel optionModel;
    private OptionView optionView;

    public OptionPresenterImpl(OptionView optionView) {
        this.optionView = optionView;
        this.optionModel = new OptionModelImpl();
    }

    @Override
    public void onCreateSuccess(PassCat passCat) {
        optionView.onCreateSuccess(passCat);
    }

    @Override
    public void onCreateError(String msg) {
        optionView.onCreateError(msg);
    }

    @Override
    public void create(String title, String url, String iconUrl) {
        optionModel.create(title, url, iconUrl, this);
    }

    @Override
    public void loadMore(long pageNo, long pageSize) {
        optionModel.loadMore(pageNo, pageSize, this);
    }

    @Override
    public void onLoadMoreSuccess(int type, List<PassCat> passCatList) {
        optionView.onLoadMoreSuccess(type, passCatList);
    }

    @Override
    public void onLoadMoreError(String msg) {
        optionView.onLoadMoreError(msg);
    }
}
