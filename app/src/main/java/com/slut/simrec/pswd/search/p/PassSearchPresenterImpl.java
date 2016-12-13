package com.slut.simrec.pswd.search.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.search.m.PassSearchModel;
import com.slut.simrec.pswd.search.m.PassSearchModelImpl;
import com.slut.simrec.pswd.search.v.PassSearchView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class PassSearchPresenterImpl implements PassSearchPresenter, PassSearchModel.OnSearchListener {

    private PassSearchModel passSearchModel;
    private PassSearchView passSearchView;

    public PassSearchPresenterImpl(PassSearchView passSearchView) {
        this.passSearchView = passSearchView;
        this.passSearchModel = new PassSearchModelImpl();
    }

    @Override
    public void onSearchSuccess(List<Password> passwordList, List<PassCat> passCatList) {
        passSearchView.onSearchSuccess(passwordList, passCatList);
    }

    @Override
    public void onSearchError(String msg) {
        passSearchView.onSearchError(msg);
    }

    @Override
    public void search(String regex) {
        passSearchModel.search(regex, this);
    }
}
