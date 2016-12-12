package com.slut.simrec.database.pswd.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.pswd.bean.PassConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class PassConfigDao {

    private static volatile PassConfigDao instances;
    private Dao<PassConfig, Integer> dao;

    public static PassConfigDao getInstances() {
        if (instances == null) {
            synchronized (PassConfigDao.class) {
                if (instances == null) {
                    instances = new PassConfigDao();
                }
            }
        }
        return instances;
    }

    private PassConfigDao() {

    }

    public void update(PassConfig passConfig) throws SQLException {
        UpdateBuilder<PassConfig, Integer> builder = dao.updateBuilder();
        builder.where().eq("uuid", passConfig.getUuid());
        builder.updateColumnValue("isFingerPrintAgreed", true);
        builder.updateColumnValue("createStamp", passConfig.getCreateStamp());
        builder.updateColumnValue("updateStamp", passConfig.getUpdateStamp());
        builder.update();
    }

    public void init() {
        dao = App.getDbHelper().getDao(PassConfig.class);
    }

    public void insertSingle(PassConfig passConfig) throws SQLException {
        dao.create(passConfig);
    }

    public void updateSingle(PassConfig passConfig) throws SQLException {
        dao.update(passConfig);
    }

    public int queryLockType() {
        try {
            PassConfig passConfig = querySingleConfig();
            if (passConfig == null) {
                return PassConfig.LockType.NOT_SET;
            }
            if (passConfig.isFingerPrintAgreed()) {
                return PassConfig.LockType.FINGERPRINT;
            }
            return passConfig.getPreferLockType();
        } catch (Exception e) {
            return PassConfig.LockType.NOT_SET;
        }
    }

    public PassConfig querySingleConfig() throws SQLException {
        PassConfig passConfig = null;
        List<PassConfig> passConfigs = queryAll();
        if (passConfigs != null) {
            if (passConfigs.size() == 1) {
                passConfig = passConfigs.get(0);
            }
        }
        return passConfig;
    }

    public void deleteAll() throws SQLException {
        List<PassConfig> passConfigs = queryAll();
        if (passConfigs != null) {
            for (PassConfig passConfig : passConfigs) {
                dao.delete(passConfig);
            }
        }
    }

    public List<PassConfig> queryAll() throws SQLException {
        List<PassConfig> passConfigList = dao.queryForAll();
        return passConfigList;
    }
}
