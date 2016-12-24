package com.slut.simrec.note.edit.p;

import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.edit.m.NoteCreateModel;
import com.slut.simrec.note.edit.m.NoteCreateModelImpl;
import com.slut.simrec.note.edit.v.NoteCreateView;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NoteCreatePresenterImpl implements NoteCreatePresenter, NoteCreateModel.OnCreateListener, NoteCreateModel.OnCheckUIListener, NoteCreateModel.OnUpdateListener {

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

    @Override
    public void checkUI(String title, String content, Note primaryNote, ArrayList<NoteLabel> primaryLabelList, ArrayList<NoteLabel> extraLabelList) {
        noteCreateModel.checkUI(title, content, primaryNote, primaryLabelList, extraLabelList, this);
    }

    @Override
    public void update(Note note, String title, String content, ArrayList<NoteLabel> noteLabels) {
        noteCreateModel.update(note, title, content, noteLabels, this);
    }

    @Override
    public void onUIChanged() {
        noteCreateView.onUIChanged();
    }

    @Override
    public void onUINotChanged() {
        noteCreateView.onUINotChanged();
    }

    @Override
    public void onUpdateSuccess(Note note) {
        noteCreateView.onUpdateSuccess(note);
    }

    @Override
    public void onUpdateError(String msg) {
        noteCreateView.onUpdateError(msg);
    }
}
