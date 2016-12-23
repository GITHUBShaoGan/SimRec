package com.slut.simrec.main.fragment.note.v;


import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.slut.simrec.R;
import com.slut.simrec.database.note.bean.Note;
import com.slut.simrec.database.note.bean.NoteLabel;
import com.slut.simrec.main.fragment.note.adapter.NoteAdapter;
import com.slut.simrec.main.fragment.note.p.NotePresenter;
import com.slut.simrec.main.fragment.note.p.NotePresenterImpl;
import com.slut.simrec.note.create.v.NoteCreateActivity;
import com.slut.simrec.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.y;
import static android.os.Build.VERSION_CODES.N;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NoteView, NoteAdapter.OnItemClickListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private int pageNo = 1;
    private static final int PAGE_SIZE = 10;

    private static volatile NoteFragment instances;
    private View rootView;

    private NoteAdapter noteAdapter;
    private LinearLayoutManager layoutManager;

    private List<Note> noteList;
    private List<List<NoteLabel>> noteLabelList;

    private NotePresenter notePresenter;

    public static NoteFragment getInstances() {
        if (instances == null) {
            synchronized (NoteFragment.class) {
                if (instances == null) {
                    instances = new NoteFragment();
                }
            }
        }
        return instances;
    }

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_note, container, false);
        }
        ButterKnife.bind(this, rootView);
        initView();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        notePresenter = new NotePresenterImpl(this);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        noteAdapter = new NoteAdapter();
        noteAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(noteAdapter);

        refresh.setOnRefreshListener(this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                NoteFragment.this.onRefresh();
            }
        });
    }

    /**
     * 刷新界面
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        noteList = new ArrayList<>();
        noteLabelList = new ArrayList<>();

        notePresenter.load(pageNo, PAGE_SIZE);
    }

    @Override
    public void onLoadSuccess(List<Note> noteList, List<List<NoteLabel>> noteLabelLists) {
        this.noteList.addAll(noteList);
        this.noteLabelList.addAll(noteLabelLists);
        noteAdapter.setNoteLabelLists(this.noteLabelList);
        noteAdapter.setNoteList(this.noteList);
        noteAdapter.notifyDataSetChanged();

        refresh.setRefreshing(false);
    }

    @Override
    public void onLoadError(String msg) {
        ToastUtils.showShort(msg);
        refresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), NoteCreateActivity.class);
        intent.putExtra(NoteCreateActivity.EXTRA_NOTE, this.noteList.get(position));
        intent.putParcelableArrayListExtra(NoteCreateActivity.EXTRA_NOTE_LABEL, (ArrayList) this.noteLabelList.get(position));
        startActivity(intent);
    }
}
