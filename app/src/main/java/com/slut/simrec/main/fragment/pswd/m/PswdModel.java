package com.slut.simrec.main.fragment.pswd.m;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public interface PswdModel {

    interface OnPassLoadListener {

        void onLoadSuccess(int type, List<Password> passwordList);

        void onLoadError(String msg);

    }

    void loadPass(int sortType,long pageNo, long pageSize, OnPassLoadListener onPassLoadListener);

    interface OnPassCatLoadListener {

        void onPassCatLoadSuccess(int type, List<PassCat> passCatList, List<List<Password>> passwordList);

        void onPassCatLoadError(String msg);

    }

    void loadPassCat(long pageNo, long pageSize, OnPassCatLoadListener onPassCatLoadListener);

}
