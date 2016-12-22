package com.slut.simrec.note.label.option.m;

import android.text.TextUtils;

import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.database.note.dao.NoteLabelDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public class LabelOptionModelImpl implements LabelOptionModel {

    @Override
    public void loadData(ArrayList<NoteLabel> noteLabels, OnDataLoadListener onDataLoadListener) {
        List<NoteLabel> noteLabelList = null;
        try {
            noteLabelList = NoteLabelDao.getInstances().queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (noteLabelList != null) {
            List<Boolean> isCheckList = new ArrayList<>();
            for (NoteLabel noteLabel : noteLabelList) {
                boolean flag = false;
                for (NoteLabel label : noteLabels) {
                    if (label.getUuid().equals(noteLabel.getUuid())) {
                        flag = true;
                        break;
                    }
                }
                isCheckList.add(flag);
            }
            onDataLoadListener.onLoadSuccess(noteLabelList, isCheckList);
        }
    }

    @Override
    public void create(String name, OnCreateListener onCreateListener) {
        if (TextUtils.isEmpty(name)) {
            onCreateListener.onCreateError("");
            return;
        }
        NoteLabel noteLabel = null;
        try {
            noteLabel = NoteLabelDao.getInstances().queryByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (noteLabel == null) {
            //不存在
            String uuid = UUID.randomUUID().toString();
            long stamp = System.currentTimeMillis();
            NoteLabel label = new NoteLabel(uuid, name, stamp, stamp);
            try {
                NoteLabelDao.getInstances().insertSingle(label);
                onCreateListener.onCreateSuccess(label);
            }catch (Exception e){
                e.printStackTrace();
                onCreateListener.onCreateError("");
            }
        } else {
            //存在
            onCreateListener.onCreateError("");
            return;
        }
    }

}
