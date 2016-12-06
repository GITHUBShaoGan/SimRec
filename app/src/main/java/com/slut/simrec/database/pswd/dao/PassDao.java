package com.slut.simrec.database.pswd.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.pswd.bean.PassConfig;
import com.slut.simrec.database.pswd.bean.Password;

import java.sql.SQLException;

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

    public void deleteAll() throws SQLException {
        DeleteBuilder<Password, Integer> builder = dao.deleteBuilder();
        for (Password password : dao) {
            dao.delete(password);
        }
    }

}
