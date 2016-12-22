package com.slut.simrec.note.label.option.p;

import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.label.option.m.LabelOptionModel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public interface LabelOptionPresenter {

    void loadData(ArrayList<NoteLabel> noteLabels);

    void create(String name);
}
