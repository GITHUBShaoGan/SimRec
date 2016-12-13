package com.slut.simrec.pswd.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/13.
 */

public class PassSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private int footerSize = 0;
    private List<Password> passwordList;
    private List<PassCat> passCatList;

    public PassSearchAdapter() {
    }

    public void addFooter() {
        footerSize = 1;
    }

    public void removeFooter() {
        footerSize = 0;
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }

    public List<PassCat> getPassCatList() {
        return passCatList;
    }

    public void setPassCatList(List<PassCat> passCatList) {
        this.passCatList = passCatList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_FOOTER:
                View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer, parent, false);
                viewHolder = new FooterViewHolder(footerView);
                break;
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_pass_search, parent, false);
                viewHolder = new FooterViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (passwordList != null && passCatList != null && position < passwordList.size() && passwordList.size() == passCatList.size()) {

        }
    }

    @Override
    public int getItemCount() {
        if (passwordList != null && !passwordList.isEmpty()) {
            return passwordList.size() + footerSize;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (passwordList != null && !passwordList.isEmpty()) {
            if (position == passwordList.size()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
