package com.slut.simrec.main.fragment.pswd.m;

import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.database.pswd.dao.PassConfigDao;
import com.slut.simrec.database.pswd.dao.PassDao;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.category.detail.v.CatDeleteType;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

import static com.slut.simrec.rsa.RSAUtils.encrypt;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class PswdModelImpl implements PswdModel {

    @Override
    public void loadPassCat(long pageNo, long pageSize, OnPassCatLoadListener onPassCatLoadListener) {
        if (pageNo < 1 || pageSize < 1) {
            onPassCatLoadListener.onPassCatLoadError(ResUtils.getString(R.string.error_size));
            return;
        }
        List<PassCat> passCatList = null;
        try {
            passCatList = PassCatDao.getInstances().queryByPage(pageNo, pageSize);
        } catch (Exception e) {

        }
        if (passCatList == null) {
            onPassCatLoadListener.onPassCatLoadError(ResUtils.getString(R.string.error_query_failed));
        } else {
            if (pageNo == 1) {
                PassConfig passConfig = null;
                try {
                    passConfig = PassConfigDao.getInstances().querySingleConfig();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (passConfig != null) {
                    if (!passCatList.isEmpty()) {
                        PassCat unspecificCat = new PassCat(CategoryConst.UUID_UNSPECIFIC, CategoryConst.TITLE_UNSPECIFIC, CategoryConst.URL_UNSPECIFIC, CategoryConst.ICONURL_UNSPECIFIC, true, System.currentTimeMillis(), System.currentTimeMillis());
                        passCatList.add(0, unspecificCat);
                    } else {
                        //判断others目录下是否还有课程
                        try {
                            List<Password> passwords = PassDao.getInstances().queryByCatUUID(CategoryConst.UUID_UNSPECIFIC);
                            if (passwords != null && !passwords.isEmpty()) {
                                PassCat unspecificCat = new PassCat(CategoryConst.UUID_UNSPECIFIC, CategoryConst.TITLE_UNSPECIFIC, CategoryConst.URL_UNSPECIFIC, CategoryConst.ICONURL_UNSPECIFIC, true, System.currentTimeMillis(), System.currentTimeMillis());
                                passCatList.add(0, unspecificCat);
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            }
            List<List<Password>> listList = new ArrayList<>();
            for (PassCat passCat : passCatList) {
                List<Password> passwordList = null;
                try {
                    passwordList = PassDao.getInstances().queryByCatUUID(passCat.getUuid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (passwordList == null) {
                    listList.add(new ArrayList<Password>());
                } else {
                    listList.add(passwordList);
                }
            }
            if (passCatList.size() < pageSize) {
                onPassCatLoadListener.onPassCatLoadSuccess(DataLoadType.END, passCatList, listList);
            } else {
                onPassCatLoadListener.onPassCatLoadSuccess(DataLoadType.FINISH, passCatList, listList);
            }
        }
    }

    @Override
    public void insertSingleCat(PassCat passCat, List<PassCat> passCatList, List<List<Password>> passwords, InsertSingleCatListener insertSingleCatListener) {
        if (passCatList == null || passCat == null) {
            return;
        }
        boolean isDefaultExists = false;
        for (PassCat cat : passCatList) {
            if (cat.getUuid().equals(CategoryConst.UUID_UNSPECIFIC)) {
                isDefaultExists = true;
                break;
            }
        }
        List<PassCat> newList = new ArrayList<>(passCatList);
        List<List<Password>> newPasswordList = new ArrayList<>(passwords);
        if (!isDefaultExists) {
            newPasswordList.add(0, new ArrayList<Password>());
            newPasswordList.add(0, new ArrayList<Password>());
            newList.add(0, passCat);
            newList.add(0, new PassCat(CategoryConst.UUID_UNSPECIFIC, CategoryConst.TITLE_UNSPECIFIC, CategoryConst.URL_UNSPECIFIC, CategoryConst.ICONURL_UNSPECIFIC, true, System.currentTimeMillis(), System.currentTimeMillis()));
        } else {
            newList.add(1, passCat);
            newPasswordList.add(1, new ArrayList<Password>());
        }
        insertSingleCatListener.onInsertSingleCatSuccess(newList, newPasswordList);
    }

    @Override
    public void deleteSingleCat(PassCat passCat, int deleteType, List<PassCat> passCatList, List<List<Password>> passwords, DeleteSingleCatListener deleteSingleCatListener) {
        if (passCat == null || passCatList == null || passwords == null) {
            return;
        }
        List<PassCat> newCatList = new ArrayList<>(passCatList);
        List<List<Password>> newPasswordList = new ArrayList<>(passwords);
        int deletePosition = -1;
        for (int i = 0; i < passCatList.size(); i++) {
            if (passCat.getUuid().equals(passCatList.get(i).getUuid())) {
                deletePosition = i;
                break;
            }
        }
        if (deletePosition != -1) {
            newPasswordList.remove(deletePosition);
            newCatList.remove(deletePosition);
            if (deleteType == CatDeleteType.DELETE_CAT_ONLY) {
                for (int i = passwords.get(deletePosition).size() - 1; i >= 0; i--) {
                    newPasswordList.get(0).add(0, passwords.get(deletePosition).get(i));
                }
            }
        }
        deleteSingleCatListener.onDeleteSingleCatSuccess(newCatList, newPasswordList, deletePosition);
    }

    @Override
    public void updateSingleCat(PassCat passCat, List<PassCat> passCatList, UpdateSingleCatListener updateSingleCatListener) {
        if (passCat == null || passCatList == null) {
            return;
        }
        int index = -1;
        for (int i = 0; i < passCatList.size(); i++) {
            if (passCatList.get(i).getUuid().equals(passCat.getUuid())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            passCatList.set(index, passCat);
            updateSingleCatListener.onUpdateSingleCatSuccess(index);
        }
    }

    @Override
    public void updateSinglePass(Password password, List<PassCat> passCatList, List<List<Password>> passwordList, UpdateSinglePassListener updateSinglePassListener) {
        if (password == null || passCatList == null || passwordList == null) {
            return;
        }
        boolean isFound = false;
        int oldCatPosition = -1;
        int oldPassPosition = -1;
        for (int i = 0; i < passCatList.size() && !isFound; i++) {
            List<Password> childPassList = passwordList.get(i);
            for (int j = 0; j < childPassList.size(); j++) {
                if (childPassList.get(j).getUuid().equals(password.getUuid())) {
                    oldCatPosition = i;
                    oldPassPosition = j;
                    isFound = true;
                    break;
                }
            }
        }
         int currentCatPosition = -1;
        for (int i = 0; i < passCatList.size(); i++) {
            if (password.getPassCatUUID().equals(passCatList.get(i).getUuid())) {
                currentCatPosition = i;
                break;
            }
        }
        if (isFound) {
            //以前有这个密码，说明只是更新数据
            if (oldCatPosition != -1 && oldPassPosition != -1) {
                passwordList.get(oldCatPosition).remove(oldPassPosition);
                passwordList.get(currentCatPosition).add(0, password);
                updateSinglePassListener.onUpdateSinglePassSuccess();
            }
        } else {
            //以前没有这个密码，说明新添加的
            if (currentCatPosition != -1) {
                passwordList.get(currentCatPosition).add(0, password);
                updateSinglePassListener.onUpdateSinglePassSuccess();
            }
        }
    }
}
