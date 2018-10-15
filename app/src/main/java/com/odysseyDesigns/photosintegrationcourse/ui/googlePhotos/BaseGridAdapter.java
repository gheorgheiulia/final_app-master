package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos;

import android.support.v7.widget.RecyclerView;

import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders.BaseViewHolder;

/**
 * Created by albertlo on 7/12/17.
 */

public abstract class BaseGridAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    protected GooglePhotosManager googlePhotosManager;

    public BaseGridAdapter() {
        super();
        googlePhotosManager = new GooglePhotosManager();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(this, getItem(position), position);
    }

    @Override
    public int getItemViewType(int position ) {
        GooglePhotosItem item = getItem(position);

        return item.getViewType();
    }

    @Override
    public int getItemCount() {
        return googlePhotosManager.getItemCount();
    }

    public GooglePhotosItem getItem(int position) {
        return googlePhotosManager.getItem(position);
    }
}
