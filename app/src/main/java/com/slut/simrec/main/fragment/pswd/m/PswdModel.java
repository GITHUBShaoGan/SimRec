package com.slut.simrec.main.fragment.pswd.m;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public interface PswdModel {

    interface OnPassCatLoadListener {

        void onPassCatLoadSuccess(int type, List<PassCat> passCatList, List<List<Password>> passwordList);

        void onPassCatLoadError(String msg);

    }

    void loadPassCat(long pageNo, long pageSize, OnPassCatLoadListener onPassCatLoadListener);

    interface InsertSingleCatListener {

        void onInsertSingleCatSuccess(List<PassCat> passCatList, List<List<Password>> passwords);

        void onInsertSingleCatError(String msg);

    }

    void insertSingleCat(PassCat passCat, List<PassCat> passCatList, List<List<Password>> passwords, InsertSingleCatListener insertSingleCatListener);

    interface DeleteSingleCatListener {

        void onDeleteSingleCatSuccess(List<PassCat> passCatList, List<List<Password>> passwords, int deletePosition);

        void onDeleteSingleCatError(String msg);

    }

    void deleteSingleCat(PassCat passCat, int deleteType, List<PassCat> passCatList, List<List<Password>> passwords, DeleteSingleCatListener deleteSingleCatListener);

    interface UpdateSingleCatListener {

        void onUpdateSingleCatSuccess(int position);

        void onUpdateSingleCatError(String msg);

    }

    void updateSingleCat(PassCat passCat, List<PassCat> passCatList, UpdateSingleCatListener updateSingleCatListener);

    interface UpdateSinglePassListener {

        void onUpdateSinglePassSuccess();

        void onUpdateSinglePassError(String msg);

    }

    void updateSinglePass(Password password, List<PassCat> passCatList, List<List<Password>> passwordList, UpdateSinglePassListener updateSinglePassListener);
}
