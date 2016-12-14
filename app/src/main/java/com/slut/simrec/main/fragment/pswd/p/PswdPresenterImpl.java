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

public class PswdPresenterImpl implements PswdPresenter, PswdModel.OnPassCatLoadListener, PswdModel.InsertSingleCatListener, PswdModel.DeleteSingleCatListener, PswdModel.UpdateSingleCatListener, PswdModel.UpdateSinglePassListener {

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

    @Override
    public void insertSingleCat(PassCat passCat, List<PassCat> passCatList, List<List<Password>> passwords) {
        pswdModel.insertSingleCat(passCat, passCatList, passwords, this);
    }

    @Override
    public void deleteSingleCat(PassCat passCat, int deleteType, List<PassCat> passCatList, List<List<Password>> passwords) {
        pswdModel.deleteSingleCat(passCat, deleteType, passCatList, passwords, this);
    }

    @Override
    public void updateSingleCat(PassCat passCat, List<PassCat> passCatList) {
        pswdModel.updateSingleCat(passCat, passCatList, this);
    }

    @Override
    public void updateSinglePass(Password password, List<PassCat> passCatList, List<List<Password>> passwordList) {
        pswdModel.updateSinglePass(password, passCatList, passwordList, this);
    }

    @Override
    public void onInsertSingleCatSuccess(List<PassCat> passCatList, List<List<Password>> passwords) {
        pswdView.onInsertSingleCatSuccess(passCatList, passwords);
    }

    @Override
    public void onInsertSingleCatError(String msg) {
        pswdView.onInsertSingleCatError(msg);
    }

    @Override
    public void onDeleteSingleCatSuccess(List<PassCat> passCatList, List<List<Password>> passwords, int deletePosition) {
        pswdView.onDeleteSingleCatSuccess(passCatList, passwords, deletePosition);
    }

    @Override
    public void onDeleteSingleCatError(String msg) {
        pswdView.onDeleteSingleCatError(msg);
    }

    @Override
    public void onUpdateSingleCatSuccess(int position) {
        pswdView.onUpdateSingleCatSuccess(position);
    }

    @Override
    public void onUpdateSingleCatError(String msg) {
        pswdView.onUpdateSingleCatError(msg);
    }

    @Override
    public void onUpdateSinglePassSuccess() {
        pswdView.onUpdateSinglePassSuccess();
    }

    @Override
    public void onUpdateSinglePassError(String msg) {
        pswdView.onUpdateSinglePassError(msg);
    }
}
