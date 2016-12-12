package com.slut.simrec.main.fragment.pswd.p;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.main.fragment.pswd.m.PswdModel;
import com.slut.simrec.main.fragment.pswd.m.PswdModelImpl;
import com.slut.simrec.main.fragment.pswd.v.PswdView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class PswdPresenterImpl implements PswdPresenter, PswdModel.OnPassCatLoadListener {

    private PswdModel pswdModel;
    private PswdView pswdView;

    public PswdPresenterImpl(PswdView pswdView) {
        this.pswdView = pswdView;
        this.pswdModel = new PswdModelImpl();
    }

    @Override
    public void onPassCatLoadSuccess(int type, List<PassCat> passCatList, List<List<Password>> passwordList) {
        pswdView.onPassCatLoadSuccess(type, passCatList, passwordList);
    }

    @Override
    public void onPassCatLoadError(String msg) {
        pswdView.onPassCatLoadError(msg);
    }

    @Override
    public void loadPassCat(long pageNo, long pageSize) {
        pswdModel.loadPassCat(pageNo, pageSize, this);
    }

}
