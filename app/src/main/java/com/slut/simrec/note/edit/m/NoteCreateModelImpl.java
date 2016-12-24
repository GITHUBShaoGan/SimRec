package com.slut.simrec.note.edit.m;

import android.text.TextUtils;

import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.LabelBind;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.database.note.dao.LabelBindDao;
import com.slut.simrec.database.note.dao.NoteDao;
import com.slut.simrec.utils.ResUtils;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NoteCreateModelImpl implements NoteCreateModel {

    @Override
    public void checkUI(String title, String content, Note primaryNote, ArrayList<NoteLabel> primaryLabelList, ArrayList<NoteLabel> extraLabelList, OnCheckUIListener onCheckUIListener) {
        if (primaryNote == null) {
            //插入数据
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content) && extraLabelList != null && extraLabelList.isEmpty()) {
                onCheckUIListener.onUINotChanged();
            } else {
                onCheckUIListener.onUIChanged();
            }
        } else {
            //更新数据
            boolean isBaseSame = false;
            if (title.equals(primaryNote.getTitle()) && content.equals(primaryNote.getContent())) {
                isBaseSame = true;
            }
            if (!isBaseSame) {
                onCheckUIListener.onUIChanged();
                return;
            }
            if (primaryLabelList != null && extraLabelList != null) {
                if (primaryLabelList.size() == extraLabelList.size()) {
                    int sameCount = 0;
                    for (NoteLabel noteLabel : primaryLabelList) {
                        boolean flag = false;
                        for (NoteLabel label : extraLabelList) {
                            if (label.getUuid().equals(noteLabel.getUuid())) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            sameCount++;
                        }
                    }
                    if (sameCount == primaryLabelList.size()) {
                        onCheckUIListener.onUINotChanged();
                    } else {
                        onCheckUIListener.onUIChanged();
                    }
                } else {
                    onCheckUIListener.onUIChanged();
                }
            } else if (primaryLabelList == null && extraLabelList == null) {
                onCheckUIListener.onUINotChanged();
            } else {
                onCheckUIListener.onUIChanged();
            }
        }
    }

    @Override
    public void create(String title, String content, ArrayList<NoteLabel> noteLabels, OnCreateListener onCreateListener) {
        if (TextUtils.isEmpty(title)) {
            onCreateListener.onCreateError(ResUtils.getString(R.string.error_label_create_name_empty));
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        Note note = new Note(uuid, title, content, stamp, stamp);
        try {
            NoteDao.getInstances().insertSingle(note);
            if (noteLabels != null) {
                for (NoteLabel noteLabel : noteLabels) {
                    String bindUUID = UUID.randomUUID().toString();
                    String noteUUID = uuid;
                    String labelUUID = noteLabel.getUuid();
                    LabelBind labelBind = new LabelBind(bindUUID, noteUUID, labelUUID);
                    LabelBindDao.getInstances().insertSingle(labelBind);
                }
            }
            onCreateListener.onCreateSuccess(note);
        } catch (Exception e) {
            onCreateListener.onCreateError(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Note note, String title, String content, ArrayList<NoteLabel> noteLabels, OnUpdateListener onUpdateListener) {
        if (TextUtils.isEmpty(title)) {
            onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_label_create_name_empty));
            return;
        }
        if (note == null) {
            onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_note_update_null_note));
            return;
        }
        try {
            NoteDao.getInstances().updateSingleNote(note.getUuid(), title, content);
            LabelBindDao.getInstances().deleteByNoteUUID(note.getUuid());
            if (noteLabels != null) {
                for (NoteLabel noteLabel : noteLabels) {
                    String uuid = UUID.randomUUID().toString();
                    String noteUUID = note.getUuid();
                    String labelUUID = noteLabel.getUuid();
                    LabelBind labelBind = new LabelBind(uuid, noteUUID, labelUUID);
                    LabelBindDao.getInstances().insertSingle(labelBind);
                }
            }
            note.setTitle(title);
            note.setContent(content);
            note.setUpdateStamp(System.currentTimeMillis());
            onUpdateListener.onUpdateSuccess(note);
        } catch (Exception e) {
            onUpdateListener.onUpdateError(e.getLocalizedMessage());
        }
    }

}
