package com.slut.simrec.pswd.search.m;

import android.text.TextUtils;

import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.database.pswd.dao.PassDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class PassSearchModelImpl implements PassSearchModel {

    @Override
    public void search(String regex, OnSearchListener onSearchListener) {
        if (TextUtils.isEmpty(regex)) {
            onSearchListener.onSearchError("");
            return;
        }
        List<Password> passwordList = null;
        try {
            passwordList = PassDao.getInstances().queryByRegex(regex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PassCat> passCatList = new ArrayList<>();
        for (Password password : passwordList) {
            try {
                PassCat passCat = PassCatDao.getInstances().querySingleByUUID(password.getPassCatUUID());
                passCatList.add(passCat);
            } catch (Exception e) {
                passCatList.add(new PassCat());
            }
        }
        onSearchListener.onSearchSuccess(passwordList, passCatList);
    }

}
