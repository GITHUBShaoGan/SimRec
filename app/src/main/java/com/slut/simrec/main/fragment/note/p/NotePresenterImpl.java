package com.slut.simrec.main.fragment.note.p;

import com.slut.simrec.database.note.bean.LabelBind;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.main.fragment.note.m.NoteModel;
import com.slut.simrec.main.fragment.note.m.NoteModelImpl;
import com.slut.simrec.main.fragment.note.v.NoteView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NotePresenterImpl implements NotePresenter,NoteModel.OnDataLoadListener {

    private NoteModel noteModel;
    private NoteView noteView;

    public NotePresenterImpl(NoteView noteView) {
        this.noteView = noteView;
        this.noteModel = new NoteModelImpl();
    }

    @Override
    public void load(long pageNo, long pageSize) {
        noteModel.load(pageNo,pageSize,this);
    }

    @Override
    public void onLoadSuccess(List<Note> noteList, List<List<NoteLabel>> noteLabelLists) {
        noteView.onLoadSuccess(noteList,noteLabelLists);
    }

    @Override
    public void onLoadError(String msg) {
        noteView.onLoadError(msg);
    }
}
