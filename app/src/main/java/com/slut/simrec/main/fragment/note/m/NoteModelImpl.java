package com.slut.simrec.main.fragment.note.m;

import com.slut.simrec.database.note.bean.LabelBind;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.database.note.dao.LabelBindDao;
import com.slut.simrec.database.note.dao.NoteDao;
import com.slut.simrec.database.note.dao.NoteLabelDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NoteModelImpl implements NoteModel {

    @Override
    public void load(long pageNo, long pageSize, OnDataLoadListener onDataLoadListener) {
        if (pageNo < 1 || pageSize <= 0) {
            onDataLoadListener.onLoadError("");
            return;
        }
        List<Note> notes = null;
        try {
            notes = NoteDao.getInstances().queryByPage(pageNo, pageSize);
        } catch (Exception e) {

        }
        if (notes != null) {
            List<List<LabelBind>> labelBinds = new ArrayList<>();
            for (Note note : notes) {
                try {
                    List<LabelBind> labelBindList = LabelBindDao.getInstances().getBindsByNoteUUID(note.getUuid());
                    labelBinds.add(labelBindList);
                } catch (Exception e) {
                    labelBinds.add(new ArrayList<LabelBind>());
                }
            }
            List<List<NoteLabel>> noteLabels = new ArrayList<>();
            for (int i = 0; i < labelBinds.size(); i++) {
                List<LabelBind> labelBindList = labelBinds.get(i);
                List<NoteLabel> noteLabelList = new ArrayList<>();
                for (LabelBind labelBind : labelBindList) {
                    try {
                        noteLabelList.add(NoteLabelDao.getInstances().queryByUUID(labelBind.getLabelUUID()));
                    } catch (Exception e) {

                    }
                }
                noteLabels.add(noteLabelList);
            }
            onDataLoadListener.onLoadSuccess(notes, noteLabels);
        }
    }

}
