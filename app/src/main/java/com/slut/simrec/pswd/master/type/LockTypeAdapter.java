package com.slut.simrec.pswd.master.type;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.simrec.App;
import com.slut.simrec.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/11.
 */

public class LockTypeAdapter extends RecyclerView.Adapter<LockTypeAdapter.ViewHolder> {

    private List<LockType> lockTypeList;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LockTypeAdapter() {
    }

    public List<LockType> getLockTypeList() {
        return lockTypeList;
    }

    public void setLockTypeList(List<LockType> lockTypeList) {
        this.lockTypeList = lockTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_lock_type, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (lockTypeList != null && position < lockTypeList.size()) {
            LockType lockType = lockTypeList.get(position);
            holder.avatar.setImageResource(lockType.getImageID());
            holder.title.setText(lockType.getTitle());
            holder.description.setText(lockType.getDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (lockTypeList != null && !lockTypeList.isEmpty()) {
            return lockTypeList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener{

        void onItemClick(View view,int position);

    }

}
