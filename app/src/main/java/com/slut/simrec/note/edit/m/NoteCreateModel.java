package com.slut.simrec.note.edit.m;

import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public interface NoteCreateModel {

    interface OnCheckUIListener {

        void onUIChanged();

        void onUINotChanged();

    }

    void checkUI(String title, String content, Note primaryNote, ArrayList<NoteLabel> primaryLabelList, ArrayList<NoteLabel> extraLabelList, OnCheckUIListener onCheckUIListener);

    interface OnCreateListener {

        void onCreateSuccess(Note note);

        void onCreateError(String msg);

    }

    void create(String title, String content, ArrayList<NoteLabel> noteLabels, OnCreateListener onCreateListener);

    interface OnUpdateListener {

        void onUpdateSuccess(Note note);

        void onUpdateError(String msg);

    }

    void update(Note note, String title, String content, ArrayList<NoteLabel> noteLabels, OnUpdateListener onUpdateListener);
}
