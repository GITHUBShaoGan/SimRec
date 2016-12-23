package com.slut.simrec.note.create.p;

import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public interface NoteCreatePresenter {

    void create(String title, String content, ArrayList<NoteLabel> noteLabels);

    void checkUI(String title, String content, Note primaryNote, ArrayList<NoteLabel> primaryLabelList,ArrayList<NoteLabel> extraLabelList);

    void update(Note note, String title, String content, ArrayList<NoteLabel> noteLabels);

}
