package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.BaseGridAdapter;

/**
 * Created by albertlo on 7/12/17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public abstract void setData(BaseGridAdapter adapter, GooglePhotosItem item, int position);
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected View getRootView() {
        return this.itemView;
    }
}
