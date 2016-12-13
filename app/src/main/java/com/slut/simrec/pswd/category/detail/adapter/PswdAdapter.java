package com.slut.simrec.pswd.category.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/9.
 */

public class PswdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private List<Password> passwordList;
    private int footerSize = 0;
    private PswdAdapter.OnItemClickListener onItemClickListener;

    public PswdAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addFooter() {
        footerSize = 1;
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
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
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_cat_detail_pass, parent, false);
                viewHolder = new ItemViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (passwordList != null && position < passwordList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Password password = passwordList.get(position);
            if (password != null) {
                String title = RSAUtils.decrypt(password.getTitle());
                String account = RSAUtils.decrypt(password.getAccount());
                itemViewHolder.title.setText(title);
                itemViewHolder.account.setText(account);
                itemViewHolder.avatar.setText(passwordList.size() - position + "");
                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (passwordList != null) {
            if (position == passwordList.size()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (passwordList != null) {
            return passwordList.size() + footerSize;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        TextView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.account)
        TextView account;

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

    }

}
