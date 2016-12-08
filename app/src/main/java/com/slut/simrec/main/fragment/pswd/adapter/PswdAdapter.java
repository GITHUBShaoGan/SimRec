package com.slut.simrec.main.fragment.pswd.adapter;

import android.media.Image;
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
import com.slut.simrec.utils.TimeUtils;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class PswdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private int footerViewSize = 0;

    private List<Password> passwordList;

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }

    public PswdAdapter() {
    }

    public void addFooterView() {
        footerViewSize = 1;
    }

    public void removeFooterView() {
        footerViewSize = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_FOOTER:
                View footer = LayoutInflater.from(App.getContext()).inflate(R.layout.footer, parent, false);
                viewHolder = new FooterViewHolder(footer);
                break;
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_password, parent, false);
                viewHolder = new ItemViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (passwordList != null && !passwordList.isEmpty() && position < passwordList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Password password = passwordList.get(position);
            if (password != null) {
                String title = RSAUtils.decrypt(password.getTitle());
                String account = RSAUtils.decrypt(password.getAccount());
                if (!TextUtils.isEmpty(title)) {
                    itemViewHolder.title.setText(title + "");
                } else {
                    itemViewHolder.title.setText(R.string.empty_title);
                }
                if (!TextUtils.isEmpty(account)) {
                    itemViewHolder.account.setText(account + "");
                } else {
                    itemViewHolder.account.setText(R.string.empty_account);
                }
                itemViewHolder.time.setText(TimeUtils.calInterval(password.getUpdateStamp(), System.currentTimeMillis()));
                if (TextUtils.isEmpty(title)) {
                    if (TextUtils.isEmpty(account)) {
                        itemViewHolder.avatar.setImageResource(R.drawable.empty);
                    } else {
                        itemViewHolder.avatar.setText(account.charAt(0) + "");
                    }
                } else {
                    itemViewHolder.avatar.setText(title.charAt(0) + "");
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (passwordList != null && !passwordList.isEmpty()) {
            if (position == passwordList.size()) {
                return TYPE_FOOTER;
            }
        }
        return type;
    }

    @Override
    public int getItemCount() {
        if (passwordList != null && !passwordList.isEmpty()) {
            return passwordList.size() + footerViewSize;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.account)
        TextView account;
        @BindView(R.id.time)
        TextView time;

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

}
