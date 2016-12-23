package com.slut.simrec.note.create.p;

import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.create.m.NoteCreateModel;
import com.slut.simrec.note.create.m.NoteCreateModelImpl;
import com.slut.simrec.note.create.v.NoteCreateView;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NoteCreatePresenterImpl implements NoteCreatePresenter, NoteCreateModel.OnCreateListener {

    private NoteCreateModel noteCreateModel;
    private NoteCreateView noteCreateView;

    public NoteCreatePresenterImpl(NoteCreateView noteCreateView) {
        this.noteCreateView = noteCreateView;
        this.noteCreateModel = new NoteCreateModelImpl();
    }

    @Override
    public void onCreateSuccess(Note note) {
        noteCreateView.onCreateSuccess(note);
    }

    @Override
    public void onCreateError(String msg) {
        noteCreateView.onCreateError(msg);
    }

    @Override
    public void create(String title, String content, ArrayList<NoteLabel> noteLabels) {
        noteCreateModel.create(title, content, noteLabels, this);
    }
}
