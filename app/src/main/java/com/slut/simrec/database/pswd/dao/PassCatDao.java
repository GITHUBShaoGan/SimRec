package com.slut.simrec.database.pswd.dao;

import android.content.Intent;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class PassCatDao {

    private static volatile PassCatDao instances;
    private Dao<PassCat, Integer> dao;

    public static PassCatDao getInstances() {
        if (instances == null) {
            synchronized (PassCatDao.class) {
                if (instances == null) {
                    instances = new PassCatDao();
                }
            }
        }
        return instances;
    }

    private PassCatDao() {

    }

    public void init() {
        dao = App.getDbHelper().getDao(PassCat.class);
    }

    public void insertSingle(PassCat passCat) throws Exception {
        dao.create(passCat);
    }

    public List<PassCat> queryByTitle(String title) throws SQLException {
        QueryBuilder<PassCat, Integer> builder = dao.queryBuilder();
        builder.where().eq("catTitle", title);
        return builder.query();
    }

    public void deleteByTitle(String title) throws SQLException {
        DeleteBuilder<PassCat, Integer> builder = dao.deleteBuilder();
        builder.where().eq("catTitle", title);
        builder.delete();
    }

    public List<PassCat> queryByPage(long pageNo, long pageSize) throws Exception {
        QueryBuilder<PassCat, Integer> builder = dao.queryBuilder();
        long offset = (pageNo - 1) * pageSize;
        builder.offset(offset);
        builder.limit(pageSize);
        builder.orderBy("createStamp", false);
        return builder.query();
    }

}
