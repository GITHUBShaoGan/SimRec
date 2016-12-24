package com.slut.simrec.main.fragment.note.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.note.label.option.adapter.LabelOptionAdapter;
import com.slut.simrec.utils.TimeUtils;
import com.slut.simrec.widget.RoundedLetterView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/23.
 */

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Random random = new Random();
    private String[] colorArr = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#3F51B5", "#2196F3", "#009688", "#4CAF50", "#FF9800", "#795548"};
    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private List<Note> noteList;
    private List<List<NoteLabel>> noteLabelLists;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NoteAdapter() {
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public List<List<NoteLabel>> getNoteLabelLists() {
        return noteLabelLists;
    }

    public void setNoteLabelLists(List<List<NoteLabel>> noteLabelLists) {
        this.noteLabelLists = noteLabelLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_home_note, parent, false);
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
        if (noteList != null && position < noteList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String color = colorArr[random.nextInt(colorArr.length)];
            Note note = noteList.get(position);
            itemViewHolder.name.setText(note.getTitle());
            itemViewHolder.roundedLetterView.setTitleText(note.getTitle().charAt(0) + "");
            itemViewHolder.roundedLetterView.setBackgroundColor(Color.parseColor(color));
            itemViewHolder.letters.setText(note.getContent().length() + " letters");
            itemViewHolder.time.setText(TimeUtils.calInterval(note.getCreateStamp(), System.currentTimeMillis()));
            itemViewHolder.flowLayout.removeAllViews();
            for (NoteLabel noteLabel : noteLabelLists.get(position)) {
                View view = LayoutInflater.from(App.getContext()).inflate(R.layout.label, new LinearLayout(App.getContext()), false);
                TextView textView = (TextView) view.findViewById(R.id.label);
                textView.setText(noteLabel.getName() + "");
                itemViewHolder.flowLayout.addView(view);
            }
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
            itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(view, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (noteList != null) {
            return noteList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rlv_name_view)
        RoundedLetterView roundedLetterView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.letters)
        TextView letters;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.flowLayout)
        FlowLayout flowLayout;

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

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }
}
