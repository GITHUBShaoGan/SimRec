package com.slut.simrec.note.create.m;

import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public interface NoteCreateModel {

    interface OnCreateListener{

        void onCreateSuccess(Note note);

        void onCreateError(String msg);

    }

    void create(String title, String content, ArrayList<NoteLabel> noteLabels,OnCreateListener onCreateListener);

}
