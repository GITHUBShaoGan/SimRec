package com.slut.simrec.database.note.dao;

import com.j256.ormlite.dao.Dao;
import com.slut.simrec.App;
import com.slut.simrec.database.note.bean.Note;

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

}
