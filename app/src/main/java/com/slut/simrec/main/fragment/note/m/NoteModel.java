package com.slut.simrec.main.fragment.note.m;

import com.slut.simrec.database.note.bean.LabelBind;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public interface NoteModel {

    interface OnDataLoadListener{

        void onLoadSuccess(List<Note> noteList, List<List<NoteLabel>> noteLabelLists);

        void onLoadError(String msg);

    }

    void load(long pageNo,long pageSize,OnDataLoadListener onDataLoadListener);

}
