package com.slut.simrec.main.fragment.note.v;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.slut.simrec.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private static volatile NoteFragment instances;
    private View rootView;

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
        initView();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(){

    }
}
