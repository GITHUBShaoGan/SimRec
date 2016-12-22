package com.slut.simrec.note.label.option.p;

import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.label.option.m.LabelOptionModel;
import com.slut.simrec.note.label.option.m.LabelOptionModelImpl;
import com.slut.simrec.note.label.option.v.LabelOptionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public class LabelOptionPresenterImpl implements LabelOptionPresenter, LabelOptionModel.OnDataLoadListener, LabelOptionModel.OnCreateListener {

    private LabelOptionView labelOptionView;
    private LabelOptionModel labelOptionModel;

    public LabelOptionPresenterImpl(LabelOptionView labelOptionView) {
        this.labelOptionView = labelOptionView;
        this.labelOptionModel = new LabelOptionModelImpl();
    }

    @Override
    public void onLoadSuccess(List<NoteLabel> noteLabelList, List<Boolean> isCheckList) {
        labelOptionView.onLoadSuccess(noteLabelList, isCheckList);
    }

    @Override
    public void onLoadError(String msg) {
        labelOptionView.onLoadError(msg);
    }

    @Override
    public void loadData(ArrayList<NoteLabel> noteLabels) {
        labelOptionModel.loadData(noteLabels, this);
    }

    @Override
    public void create(String name) {
        labelOptionModel.create(name, this);
    }

    @Override
    public void onCreateSuccess(NoteLabel noteLabel) {
        labelOptionView.onCreateSuccess(noteLabel);
    }

    @Override
    public void onCreateError(String msg) {
        labelOptionView.onCreateError(msg);
    }
}
