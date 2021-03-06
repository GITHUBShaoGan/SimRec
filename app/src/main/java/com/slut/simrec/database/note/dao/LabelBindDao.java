package com.slut.simrec.database.note.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.note.bean.LabelBind;
import com.slut.simrec.database.note.bean.Note;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public class LabelBindDao {

    private static volatile LabelBindDao instances;
    private Dao<LabelBind, Integer> dao;

    public static LabelBindDao getInstances() {
        if (instances == null) {
            synchronized (LabelBindDao.class) {
                if (instances == null) {
                    instances = new LabelBindDao();
                }
            }
        }
        return instances;
    }

    private LabelBindDao() {

    }

    public void init() {
        dao = App.getDbHelper().getDao(LabelBind.class);
    }

    public void insertSingle(LabelBind labelBind) throws SQLException {
        dao.create(labelBind);
    }

    public List<LabelBind> getBindsByNoteUUID(String noteUUID) throws SQLException {
        QueryBuilder<LabelBind, Integer> builder = dao.queryBuilder();
        builder.where().eq("noteUUID", noteUUID);
        return builder.query();
    }

    public void deleteByNoteUUID(String noteUUID)throws SQLException{
        DeleteBuilder<LabelBind,Integer> builder = dao.deleteBuilder();
        builder.where().eq("noteUUID",noteUUID);
        builder.delete();
    }
}
