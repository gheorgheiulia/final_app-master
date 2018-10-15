package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.odysseyDesigns.googlePhotos.model.PhotoEntry;
import com.odysseyDesigns.photosintegrationcourse.R;
import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.AlbumGridAdapter;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.BaseGridAdapter;
import com.odysseyDesigns.photosintegrationcourse.ui.views.CheckBox;
import com.squareup.picasso.Picasso;

/**
 * Created by albertlo on 7/12/17.
 */

public class GooglePhotosViewHolder extends BaseViewHolder implements View.OnClickListener {
    private ImageView image;
    private AppCompatActivity activity;
    private CheckBox checkBox;
    private AlbumGridAdapter adapter;

    public GooglePhotosViewHolder(AppCompatActivity activity, AlbumGridAdapter albumGridAdapter, ViewGroup parent) {
        super(LayoutInflater.from(activity).inflate(R.layout.google_photos_item, parent, false));
        image = (ImageView) getRootView().findViewById(R.id.thumb);
        checkBox = (CheckBox) getRootView().findViewById(R.id.check_box);
        getRootView().setOnClickListener(this);
        this.adapter = albumGridAdapter;
    }

    @Override
    public void setData(BaseGridAdapter adapter, GooglePhotosItem item, int position) {
        PhotoEntry photoEntry = item.getPhotoItem();
        String thumb = photoEntry.getMediaGroup().getContents().get(0).getUrl();

        Picasso.with(activity).load(thumb).into(image);
        if (item.isSelected()) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.onClick();
        }
    }

    @Override
    public void onClick(View v) {
        GooglePhotosItem item = adapter.getItem(getAdapterPosition());

        checkBox.onClick();
        item.setSelected(checkBox.isSelected());
        if(checkBox.isChecked()) {
            checkBox.setVisibility(View.VISIBLE);
        } else  {
            checkBox.setVisibility(View.GONE);
        }
    }
}
