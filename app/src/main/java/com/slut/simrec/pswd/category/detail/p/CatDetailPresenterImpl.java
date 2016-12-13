package com.slut.simrec.pswd.category.detail.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.category.detail.m.CatDetailModel;
import com.slut.simrec.pswd.category.detail.m.CatDetailModelImpl;
import com.slut.simrec.pswd.category.detail.v.CatDetailView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/9.
 */

public class CatDetailPresenterImpl implements CatDetailPresenter, CatDetailModel.OnDataLoadListener, CatDetailModel.OnDeleteListener, CatDetailModel.OnEditListener {

    private CatDetailModel catDetailModel;
    private CatDetailView catDetailView;

    public CatDetailPresenterImpl(CatDetailView catDetailView) {
        this.catDetailView = catDetailView;
        this.catDetailModel = new CatDetailModelImpl();
    }

    @Override
    public void onLoadSuccess(int type, List<Password> passwordList) {
        catDetailView.onLoadSuccess(type, passwordList);
    }

    @Override
    public void onLoadError(String msg) {
        catDetailView.onLoadError(msg);
    }

    @Override
    public void loadData(PassCat passCat) {
        catDetailModel.loadData(passCat, this);
    }

    @Override
    public void delete(int deleteType,PassCat passCat) {
        catDetailModel.delete(deleteType,passCat, this);
    }

    @Override
    public void edit(PassCat passCat, String title, String url, String iconURL) {
        catDetailModel.edit(passCat, title, url, iconURL, this);
    }

    @Override
    public void onDeleteSuccess() {
        catDetailView.onDeleteSuccess();
    }

    @Override
    public void onDeleteError(String msg) {
        catDetailView.onDeleteError(msg);
    }

    @Override
    public void onEditSuccess(PassCat passCat) {
        catDetailView.onEditSuccess(passCat);
    }

    @Override
    public void onEditError(String msg) {
        catDetailView.onEditError(msg);
    }
}
