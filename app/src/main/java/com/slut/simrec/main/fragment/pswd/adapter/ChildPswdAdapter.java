package com.slut.simrec.main.fragment.pswd.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.pswd.unlock.grid.v.GridUnlockActivity;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ResUtils;
import com.slut.simrec.utils.SystemUtils;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slut.simrec.main.v.MainActivity.REQUEST_UNLOCK_COPY;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class ChildPswdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private List<Password> passwordList;
    private int footerViewSize = 0;
    private Context context;

    public ChildPswdAdapter(Context context) {
        this.context = context;
    }

    public void addFooter() {
        footerViewSize = 1;
    }

    public void removeFooter() {
        footerViewSize = 0;
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
                View footView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer, parent, false);
                viewHolder = new FooterViewHolder(footView);
                break;
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_pswd_cat_child, parent, false);
                viewHolder = new ItemViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (passwordList != null && position < passwordList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Password password = passwordList.get(position);
            if (password != null) {
                String title = RSAUtils.decrypt(password.getTitle());
                final String account = RSAUtils.decrypt(password.getAccount());
                if (TextUtils.isEmpty(account)) {
                    if (TextUtils.isEmpty(title)) {
                        itemViewHolder.avatar.setText(ResUtils.getString(R.string.empty_title).charAt(0) + "");
                    } else {
                        itemViewHolder.avatar.setText(title);
                    }
                } else {
                    itemViewHolder.avatar.setText(account.charAt(0) + "");
                }
                itemViewHolder.account.setText(account + "");
                itemViewHolder.title.setText(title + "");

                itemViewHolder.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!App.isPswdFunctionLocked()) {
                            PopupMenu popupMenu = new PopupMenu(context, view);
                            popupMenu.inflate(R.menu.item_password);
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.action_copy_account:
                                            SystemUtils.copy("account", account);
                                            break;
                                        case R.id.action_copy_password:
                                            SystemUtils.copy("password", RSAUtils.decrypt(password.getPassword()));
                                            break;
                                        case R.id.action_copy_both:
                                            SystemUtils.copy("both", RSAUtils.decrypt(password.getAccount() + " " + password.getPassword()));
                                            break;
                                    }

                                    return false;
                                }
                            });
                            popupMenu.show();
                        } else {
                            Intent intent = new Intent(context, GridUnlockActivity.class);
                            ((Activity) context).startActivityForResult(intent, REQUEST_UNLOCK_COPY);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (passwordList == null) {
            if (position == passwordList.size()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (passwordList != null) {
            return passwordList.size() + footerViewSize;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.account)
        TextView account;
        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.copy)
        ImageView copy;

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
