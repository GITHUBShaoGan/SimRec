package com.slut.simrec.database.pswd.dao;

import android.content.Intent;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.main.fragment.pswd.m.PassSortType;
import com.slut.simrec.rsa.RSAUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class PassDao {

    private static volatile PassDao instances;
    private Dao<Password, Integer> dao;

    public static PassDao getInstances() {
        if (instances == null) {
            synchronized (PassDao.class) {
                if (instances == null) {
                    instances = new PassDao();
                }
            }
        }
        return instances;
    }

    private PassDao() {

    }

    public void init() {
        dao = App.getDbHelper().getDao(Password.class);
    }

    public void update(Password password) throws SQLException {
        if (password == null) {
            return;
        }
        UpdateBuilder<Password, Integer> builder = dao.updateBuilder();
        builder.where().eq("uuid", password.getUuid());
        builder.updateColumnValue("title", password.getTitle());
        builder.updateColumnValue("account", password.getAccount());
        builder.updateColumnValue("password", password.getPassword());
        builder.updateColumnValue("websiteUrl", password.getWebsiteUrl());
        builder.updateColumnValue("remark", password.getRemark());
        builder.updateColumnValue("passCatUUID", password.getPassCatUUID());
        builder.updateColumnValue("updateStamp", password.getUpdateStamp());
        builder.update();
    }

    public void deleteByCatUUID(String catUUID) throws SQLException {
        DeleteBuilder<Password, Integer> builder = dao.deleteBuilder();
        builder.where().eq("passCatUUID", catUUID);
        builder.delete();
    }

    public void deleteByUUID(String uuid) throws SQLException {
        DeleteBuilder<Password, Integer> builder = dao.deleteBuilder();
        builder.where().eq("uuid", uuid);
        builder.delete();
    }

    public void insertSingle(Password password) throws Exception {
        if (password != null) {
            dao.create(password);
        }
    }

    public void updateCat(String passUUID, String newCatUUID) throws SQLException {
        UpdateBuilder<Password, Integer> builder = dao.updateBuilder();
        builder.where().eq("uuid", passUUID);
        builder.updateColumnValue("passCatUUID", newCatUUID);
        builder.update();
    }

    public List<Password> queryByCatUUID(String cat_id) throws Exception {
        QueryBuilder<Password, Integer> builder = dao.queryBuilder();
        builder.where().eq("passCatUUID", cat_id);
        builder.orderBy("updateStamp", false);
        return builder.query();
    }

    public List<Password> queryByPage(int sortType, long pageNo, long pageSize) throws Exception {
        QueryBuilder<Password, Integer> builder = dao.queryBuilder();
        long offSet = (pageNo - 1) * pageSize;
        builder.offset(offSet);
        if (sortType == PassSortType.CREATE_TIME) {
            builder.orderBy("createStamp", false);
        } else if (sortType == PassSortType.UPDATE_TIME) {
            builder.orderBy("updateStamp", false);
        } else {
            builder.orderBy("createStamp", false);
        }
        builder.limit(pageSize);
        return builder.query();
    }

    public List<Password> queryByRegex(String regex) throws SQLException {
        List<Password> passwordList = new ArrayList<>();
        regex = "%" + regex + "%";
        QueryBuilder<Password, Integer> builderTitle = dao.queryBuilder();
        builderTitle.where().like("title", regex);
        passwordList.addAll(builderTitle.query());

        QueryBuilder<Password, Integer> builderAccount = dao.queryBuilder();
        builderAccount.where().like("account", regex);
        List<Password> accountList = builderAccount.query();
        for (Password password : accountList) {
            boolean flag = false;
            for (Password password1 : passwordList) {
                if (password1.getUuid().equals(password.getUuid())) {
                    flag = true;
                }
            }
            if (!flag) {
                passwordList.add(password);
            }
        }

        QueryBuilder<Password, Integer> builderURL = dao.queryBuilder();
        builderURL.where().like("websiteUrl", regex);
        List<Password> accountURL = builderURL.query();
        for (Password password : accountURL) {
            boolean flag = false;
            for (Password password1 : passwordList) {
                if (password1.getUuid().equals(password.getUuid())) {
                    flag = true;
                }
            }
            if (!flag) {
                passwordList.add(password);
            }
        }

        QueryBuilder<Password, Integer> builderRemark = dao.queryBuilder();
        builderRemark.where().like("remark", regex);
        List<Password> remarkList = builderRemark.query();
        for (Password password : remarkList) {
            boolean flag = false;
            for (Password password1 : passwordList) {
                if (password1.getUuid().equals(password.getUuid())) {
                    flag = true;
                }
            }
            if (!flag) {
                passwordList.add(password);
            }
        }
        return passwordList;
    }

    public void deleteAll() throws SQLException {
        DeleteBuilder<Password, Integer> builder = dao.deleteBuilder();
        for (Password password : dao) {
            dao.delete(password);
        }
    }

}
