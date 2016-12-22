package com.slut.simrec.note.label.option.v;

import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public interface LabelOptionView {

    void onLoadSuccess(List<NoteLabel> noteLabelList, List<Boolean> isCheckList);

    void onLoadError(String msg);

    void onCreateSuccess(NoteLabel noteLabel);

    void onCreateError(String msg);

}
