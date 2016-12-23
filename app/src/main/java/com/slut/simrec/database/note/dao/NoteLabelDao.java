package com.slut.simrec.database.note.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public class NoteLabelDao {

    private static volatile NoteLabelDao instances;
    private Dao<NoteLabel, Integer> dao;

    public static NoteLabelDao getInstances() {
        if (instances == null) {
            synchronized (NoteLabelDao.class) {
                if (instances == null) {
                    instances = new NoteLabelDao();
                }
            }
        }
        return instances;
    }

    private NoteLabelDao() {

    }

    public void init() {
        dao = App.getDbHelper().getDao(NoteLabel.class);
    }

    public List<NoteLabel> queryAll() throws SQLException {
        QueryBuilder<NoteLabel,Integer> builder = dao.queryBuilder();
        builder.orderBy("createStamp",false);
        return builder.query();
    }

    public void insertSingle(NoteLabel noteLabel) throws SQLException {
        dao.create(noteLabel);
    }

    public NoteLabel queryByName(String name)throws SQLException{
        QueryBuilder<NoteLabel,Integer> builder = dao.queryBuilder();
        builder.where().eq("name",name);
        return builder.queryForFirst();
    }

    public NoteLabel queryByUUID(String uuid)throws SQLException{
        QueryBuilder<NoteLabel,Integer> builder = dao.queryBuilder();
        builder.where().eq("uuid",uuid);
        return builder.queryForFirst();
    }

}
