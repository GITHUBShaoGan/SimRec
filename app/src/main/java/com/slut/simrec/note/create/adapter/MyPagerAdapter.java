package com.slut.simrec.note.create.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/21.
 */

public class MyPagerAdapter extends PagerAdapter {

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if (viewList != null && position < viewList.size()) {
            container.removeView(viewList.get(position));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (viewList != null && position < viewList.size()) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    private List<View> viewList;

    public MyPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if (viewList != null)
            return viewList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
