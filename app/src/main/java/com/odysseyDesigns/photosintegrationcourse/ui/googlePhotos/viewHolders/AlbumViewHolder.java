package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.odysseyDesigns.googlePhotos.model.AlbumEntry;
import com.odysseyDesigns.photosintegrationcourse.R;
import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.BaseGridAdapter;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.GooglePhotosFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by albertlo on 7/12/17.
 */

public class AlbumViewHolder extends BaseViewHolder {
    private ImageView image;
    private AppCompatActivity activity;

    public AlbumViewHolder(AppCompatActivity activity, ViewGroup parent) {
        super(LayoutInflater.from(activity).inflate(R.layout.google_photos_item, parent, false));
        image = (ImageView) getRootView().findViewById(R.id.thumb);
        this.activity = activity;
    }

    @Override
    public void setData(BaseGridAdapter adapter, GooglePhotosItem item, int position) {
        final AlbumEntry albumEntry = item.getAlbumItem();
        String thumb = albumEntry.getMediaGroup().getContents().get(0).getUrl();

        Picasso.with(activity).load(thumb).into(image);
        getRootView().setOnClickListener(new ItemClickHandler(adapter));
    }

    private class ItemClickHandler implements View.OnClickListener {
        private final BaseGridAdapter adapter;

        public ItemClickHandler(BaseGridAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                long albumId = adapter.getItem(getAdapterPosition()).getAlbumItem().getGphotoId();
                FragmentManager manager = activity.getSupportFragmentManager();
                GooglePhotosFragment fragment = (GooglePhotosFragment) manager.findFragmentByTag(GooglePhotosFragment.class.getSimpleName());
                fragment.loadPhotos(albumId);

            }
        }
    }
}
