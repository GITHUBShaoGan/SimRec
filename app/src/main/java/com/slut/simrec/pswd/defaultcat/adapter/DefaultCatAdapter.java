package com.slut.simrec.pswd.defaultcat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.pswd.defaultcat.bean.DefaultCatBean;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class DefaultCatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private int footerSize = 0;
    private List<DefaultCatBean> defaultCatBeanList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DefaultCatAdapter() {
    }

    public List<DefaultCatBean> getDefaultCatBeanList() {
        return defaultCatBeanList;
    }

    public void setDefaultCatBeanList(List<DefaultCatBean> defaultCatBeanList) {
        this.defaultCatBeanList = defaultCatBeanList;
    }

    public void addFooterView() {
        footerSize = 1;
    }

    public void removeFooter() {
        footerSize = 0;
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
        if (defaultCatBeanList != null && !defaultCatBeanList.isEmpty() && position < defaultCatBeanList.size()) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            DefaultCatBean defaultCatBean = defaultCatBeanList.get(position);
            String website = defaultCatBean.getWebsite();
            final String title = defaultCatBean.getTitle();
            itemViewHolder.title.setText(title + "");
            itemViewHolder.website.setText(website + "");
            ImageLoader.getInstance().displayImage(defaultCatBean.getIconUrl(), itemViewHolder.avatar, ImgLoaderOptions.init404Options());
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (defaultCatBeanList != null && !defaultCatBeanList.isEmpty()) {
            if (position == defaultCatBeanList.size()) {
                type = TYPE_FOOTER;
            }
        }
        return type;
    }

    @Override
    public int getItemCount() {
        if (defaultCatBeanList != null && !defaultCatBeanList.isEmpty()) {
            return defaultCatBeanList.size() + footerSize;
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
