package com.slut.simrec.main.fragment.pswd.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.simrec.App;
import com.slut.simrec.R;
import com.slut.simrec.database.pswd.bean.PassCat;
import com.slut.simrec.database.pswd.bean.Password;
import com.slut.simrec.database.pswd.dao.PassCatDao;
import com.slut.simrec.pswd.category.CategoryConst;
import com.slut.simrec.pswd.unlock.grid.v.GridUnlockActivity;
import com.slut.simrec.rsa.RSAUtils;
import com.slut.simrec.utils.ImgLoaderOptions;
import com.slut.simrec.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slut.simrec.main.v.MainActivity.REQUEST_UNLOCK_COPY;

/**
 * Created by 七月在线科技 on 2016/12/8.
 */

public class PswdCatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0, TYPE_FOOTER = 1;
    private int footerViewSize;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (passCatList != null && position < passCatList.size()) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final PassCat passCat = passCatList.get(position);
            ImageLoader.getInstance().displayImage(RSAUtils.decrypt(passCat.getCatIconUrl()), itemViewHolder.avatar, ImgLoaderOptions.init404Options());
            itemViewHolder.title.setText(RSAUtils.decrypt(passCat.getCatTitle()));
            itemViewHolder.website.setText(RSAUtils.decrypt(passCat.getCatUrl()));
            itemViewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onAddClick(view, position);
                }
            });
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onCatItemClick(view, position);
                }
            });
            if (passwordList != null && position < passwordList.size()) {
                final ChildPswdAdapter adapter = new ChildPswdAdapter(context);
                adapter.setPasswordList(passwordList.get(position));
                adapter.setPassCat(passCatList.get(position));
                adapter.setOnItemClickListener(new ChildPswdAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int pos) {
                        onItemClickListener.onPasswordItemClick(view, position,pos);
                    }
                });
                itemViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
                itemViewHolder.recyclerView.setAdapter(adapter);
                itemViewHolder.lessOrMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (passCat.isExpand()) {
                            itemViewHolder.lessOrMore.setImageResource(R.drawable.ic_expand_more_grey600_24dp);
                        } else {
                            itemViewHolder.lessOrMore.setImageResource(R.drawable.ic_expand_less_grey600_24dp);
                        }
                        PassCatDao.getInstances().updateExpand(!passCat.isExpand(), passCat.getUuid());
                        passCat.setExpand(!passCat.isExpand());
                        adapter.setPassCat(passCat);
                        adapter.notifyDataSetChanged();
                    }
                });
                if (passCat.isExpand()) {
                    itemViewHolder.lessOrMore.setImageResource(R.drawable.ic_expand_less_grey600_24dp);
                } else {
                    itemViewHolder.lessOrMore.setImageResource(R.drawable.ic_expand_more_grey600_24dp);
                }
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
        ImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.website)
        TextView website;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;
        @BindView(R.id.add)
        ImageView add;
        @BindView(R.id.lessormore)
        ImageView lessOrMore;

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

        void onAddClick(View view, int position);

        void onCatItemClick(View view, int position);

        void onPasswordItemClick(View view,int catPosition, int passPosition);

    }
}
