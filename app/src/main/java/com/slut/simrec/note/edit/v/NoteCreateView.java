package com.slut.simrec.note.edit.v;

import com.slut.simrec.database.note.bean.Note;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public interface NoteCreateView {

    void onCreateSuccess(Note note);

    void onCreateError(String msg);

    void onUIChanged();

    void onUINotChanged();

    void onUpdateSuccess(Note note);

    void onUpdateError(String msg);

}
