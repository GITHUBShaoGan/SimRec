package com.slut.simrec.note.create.p;

import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public interface NoteCreatePresenter {

    void create(String title, String content, ArrayList<NoteLabel> noteLabels);

}
