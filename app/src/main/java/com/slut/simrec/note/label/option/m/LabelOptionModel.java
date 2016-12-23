package com.slut.simrec.note.label.option.m;

import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.label.option.adapter.LabelOptionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public interface LabelOptionModel {

    interface OnDataLoadListener {

        void onLoadSuccess(List<NoteLabel> noteLabelList, List<Boolean> isCheckList);

        void onLoadError(String msg);

    }

    void loadData(ArrayList<NoteLabel> noteLabels, OnDataLoadListener onDataLoadListener);


    interface OnCreateListener {

        void onCreateSuccess(NoteLabel noteLabel);

        void onCreateError(String msg);

    }

    void create(String name, OnCreateListener onCreateListener);

}
