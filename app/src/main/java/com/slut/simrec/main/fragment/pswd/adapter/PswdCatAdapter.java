package com.slut.simrec.main.fragment.pswd.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class PswdCatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private int footerViewSize;

    public void addFooterView() {
        footerViewSize = 1;
    }

    public void removeFooterView() {
        footerViewSize = 0;
    }

    private Context context;
    private List<PassCat> passCatList;
    private List<List<Password>> passwordList;

    public PswdCatAdapter(Context context) {
        this.context = context;
    }

    public List<PassCat> getPassCatList() {
        return passCatList;
    }

    public void setPassCatList(List<PassCat> passCatList) {
        this.passCatList = passCatList;
    }

    public List<List<Password>> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<List<Password>> passwordList) {
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
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_home_cat, parent, false);
                viewHolder = new ItemViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (passCatList != null && position < passCatList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            PassCat passCat = passCatList.get(position);
            ImageLoader.getInstance().displayImage(RSAUtils.decrypt(passCat.getCatIconUrl()), itemViewHolder.avatar, ImgLoaderOptions.init404Options());
            itemViewHolder.title.setText(RSAUtils.decrypt(passCat.getCatTitle()));
            itemViewHolder.website.setText(RSAUtils.decrypt(passCat.getCatUrl()));

            if (passwordList != null && position < passwordList.size()) {
                ChildPswdAdapter adapter = new ChildPswdAdapter(context);
                adapter.setPasswordList(passwordList.get(position));
                itemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
                itemViewHolder.recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (passCatList != null && !passCatList.isEmpty()) {
            if (position == passCatList.size()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (passCatList != null && !passCatList.isEmpty()) {
            return passCatList.size() + footerViewSize;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.website)
        TextView website;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;

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
