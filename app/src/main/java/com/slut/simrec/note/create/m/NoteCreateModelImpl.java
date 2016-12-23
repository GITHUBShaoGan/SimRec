package com.slut.simrec.note.create.m;

import android.text.TextUtils;

import com.slut.simrec.database.note.bean.LabelBind;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.database.note.dao.LabelBindDao;
import com.slut.simrec.database.note.dao.NoteDao;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NoteCreateModelImpl implements NoteCreateModel {

    @Override
    public void create(String title, String content, ArrayList<NoteLabel> noteLabels, OnCreateListener onCreateListener) {
        if (TextUtils.isEmpty(title)) {
            onCreateListener.onCreateError("");
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        Note note = new Note(uuid, title, content, stamp, stamp);
        try {
            NoteDao.getInstances().insertSingle(note);
            for (NoteLabel noteLabel : noteLabels) {
                String bindUUID = UUID.randomUUID().toString();
                String noteUUID = uuid;
                String labelUUID = noteLabel.getUuid();
                LabelBind labelBind = new LabelBind(bindUUID, noteUUID, labelUUID);
                LabelBindDao.getInstances().insertSingle(labelBind);
            }
            onCreateListener.onCreateSuccess(note);
        } catch (Exception e) {
            onCreateListener.onCreateError("");
        }
    }

}
