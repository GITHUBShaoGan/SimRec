package com.slut.simrec.note.label.option.adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.NoteLabel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/22.
 */

public class LabelOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private List<NoteLabel> noteLabelList;
    private List<Boolean> isCheckedList;
    private OnCheckChangedListener onCheckChangedListener;

    public void setOnCheckChangedListener(OnCheckChangedListener onCheckChangedListener) {
        this.onCheckChangedListener = onCheckChangedListener;
    }

    public LabelOptionAdapter() {
    }

    public List<NoteLabel> getNoteLabelList() {
        return noteLabelList;
    }

    public void setNoteLabelList(List<NoteLabel> noteLabelList) {
        this.noteLabelList = noteLabelList;
    }

    public List<Boolean> getIsCheckedList() {
        return isCheckedList;
    }

    public void setIsCheckedList(List<Boolean> isCheckedList) {
        this.isCheckedList = isCheckedList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_note_label_option, parent, false);
                viewHolder = new ItemViewHolder(itemView);
                break;
            case TYPE_FOOTER:
                View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer, parent, false);
                viewHolder = new FooterViewHolder(footerView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (noteLabelList != null && isCheckedList != null && position < isCheckedList.size() && position < noteLabelList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            NoteLabel noteLabel = noteLabelList.get(position);
            if (noteLabel != null) {
                itemViewHolder.name.setText(noteLabel.getName() + "");
            }
            itemViewHolder.checkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onCheckChangedListener.onCheckChanged(compoundButton, position, b);
                }
            });
            itemViewHolder.checkStatus.setChecked(isCheckedList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (noteLabelList != null) {
            return noteLabelList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (noteLabelList != null) {
            if (position == noteLabelList.size()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.checkbox)
        CheckBox checkStatus;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnCheckChangedListener {

        void onCheckChanged(View view, int position, boolean flag);

    }
}
