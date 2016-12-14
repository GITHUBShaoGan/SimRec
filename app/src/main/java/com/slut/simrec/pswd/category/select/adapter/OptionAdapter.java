package com.slut.simrec.pswd.category.select.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;

    private int footerSize;
    private List<PassCat> passCatList;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OptionAdapter() {
    }

    public void addFooter() {
        footerSize = 1;
    }

    public void removeFooter() {
        footerSize = 0;
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
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_default_cat, parent, false);
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
        if (passCatList != null && position < passCatList.size()) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            PassCat passCat = passCatList.get(position);
            if (passCat != null) {
                ImageLoader.getInstance().displayImage(passCat.getCatIconUrl(), itemViewHolder.avatar, ImgLoaderOptions.init404Options());
                itemViewHolder.title.setText(passCat.getCatTitle() + "");
                String url = passCat.getCatUrl();
                if (TextUtils.isEmpty(url)) {
                    itemViewHolder.url.setText("Empty URL");
                } else {
                    itemViewHolder.url.setText(passCat.getCatUrl());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        int type = TYPE_ITEM;
        if (passCatList != null && !passCatList.isEmpty()) {
            if (position == passCatList.size()) {
                type = TYPE_FOOTER;
            }
        }
        return type;
    }

    @Override
    public int getItemCount() {
        if (passCatList != null && !passCatList.isEmpty()) {
            return passCatList.size() + footerSize;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.website)
        TextView url;

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
