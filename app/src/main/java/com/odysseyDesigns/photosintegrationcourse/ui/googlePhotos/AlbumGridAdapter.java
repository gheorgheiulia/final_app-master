package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.odysseyDesigns.googlePhotos.model.AlbumEntry;
import com.odysseyDesigns.googlePhotos.model.PhotoEntry;
import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders.AlbumViewHolder;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders.BaseViewHolder;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders.GooglePhotosViewHolder;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders.HeaderViewHolder;

import java.util.List;

/**
 * Created by albertlo on 7/12/17.
 */

public class AlbumGridAdapter extends BaseGridAdapter {
    private AppCompatActivity activity;

    public AlbumGridAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GooglePhotosItem.ItemType type = GooglePhotosItem.ItemType.fromViewType(viewType);

        if (type == null) {
            throw new NullPointerException("No ViewHolder for this type: " + viewType);
        }

        switch (type) {
            case ALBUM:
                return new AlbumViewHolder(activity, parent);

            case PHOTO:
                return new GooglePhotosViewHolder(activity, this, parent);

            case ALBUM_HEADER:
            case PHOTO_HEADER:
                return  new HeaderViewHolder(activity, parent);

            default:
                throw new IllegalStateException("Unknown view type:" + viewType);
        }
    }

    public void setAlbumList(List<AlbumEntry> albumEntries) {
        googlePhotosManager.setAlbumList(albumEntries);
        notifyDataSetChanged();
    }

    public void setPhotosList(List<PhotoEntry> photoEntries) {
        googlePhotosManager.setPhotosList(photoEntries);
        notifyDataSetChanged();
    }
}
