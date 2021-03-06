/*
 * Copyright 2016. SHENQINCI(沈钦赐)<946736079@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.slut.simrec.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.slut.simrec.R;


/**
 * TAB
 * Created by on 16/6/21.
 */
public class TabIconView extends HorizontalScrollView {

    private LinearLayout mLayout;
    private LayoutInflater mInflater;

    public TabIconView(Context context) {
        super(context);
        init();
    }

    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        this.setHorizontalScrollBarEnabled(false);

        mInflater = LayoutInflater.from(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayout = new LinearLayout(getContext());
        mLayout.setPadding(1, 0, 1, 0);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mLayout, params);
    }


    public void addTab(@DrawableRes int iconId, @IdRes int id, OnClickListener onClickListener) {
        View v = mInflater.inflate(R.layout.item_tab_icon, mLayout, false);
        ImageButton view = (ImageButton) v.findViewById(R.id.icon);
        view.setImageResource(iconId);
        view.setId(id);
        view.setOnClickListener(onClickListener);
        mLayout.addView(v, mLayout.getChildCount());
    }


    public void removeTab() {
        int count = mLayout.getChildCount();
        //移除最后一个
        mLayout.removeViewAt(count - 1);
    }

}
