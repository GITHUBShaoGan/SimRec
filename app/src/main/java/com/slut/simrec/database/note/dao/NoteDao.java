package com.slut.simrec.database.note.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.simrec.App;
import com.slut.simrec.database.note.bean.Note;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public class NoteDao {

    private static volatile NoteDao instances;
    private Dao<Note, Integer> dao;

    public static NoteDao getInstances() {
        if (instances == null) {
            synchronized (NoteDao.class) {
                if (instances == null) {
                    instances = new NoteDao();
                }
            }
        }
        return instances;
    }

    private NoteDao() {

    }

    public void init() {
        dao = App.getDbHelper().getDao(Note.class);
    }

    public void insertSingle(Note note) throws SQLException {
        dao.create(note);
    }

    public List<Note> queryByPage(long pageNo, long pageSize) throws SQLException {
        QueryBuilder<Note, Integer> builder = dao.queryBuilder();
        long offset = (pageNo - 1) * pageSize;
        builder.offset(offset);
        builder.orderBy("createStamp", false);
        builder.limit(pageSize);
        return builder.query();
    }

    public void updateSingleNote(String uuid, String title, String content) throws SQLException {
        UpdateBuilder<Note, Integer> builder = dao.updateBuilder();
        builder.where().eq("uuid", uuid);
        builder.updateColumnValue("title", title);
        builder.updateColumnValue("content", content);
        builder.updateColumnValue("updateStamp", System.currentTimeMillis());
        builder.update();
    }
}
