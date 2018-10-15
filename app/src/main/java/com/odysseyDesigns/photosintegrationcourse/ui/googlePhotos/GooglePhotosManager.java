package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos;

import android.util.Log;

import com.odysseyDesigns.googlePhotos.model.AlbumEntry;
import com.odysseyDesigns.googlePhotos.model.PhotoEntry;
import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertlo on 7/11/17.
 */

public class GooglePhotosManager {

    private static final String TAG = GooglePhotosManager.class.getSimpleName();
    private List<GooglePhotosItem> googlePhotosItemList;

    public GooglePhotosManager() {
        googlePhotosItemList = new ArrayList<>();
    }

    public GooglePhotosItem getItem(int position) {
        return googlePhotosItemList.get(position);
    }

    public int getItemCount() {
        return googlePhotosItemList.size();
    }

    public void setAlbumList(List<AlbumEntry> albumEntries) {
        googlePhotosItemList.clear();
        for(AlbumEntry entry : albumEntries) {
            GooglePhotosItem item = new GooglePhotosItem(entry);
            googlePhotosItemList.add(item);
        }
    }

    public void setPhotosList(List<PhotoEntry> photoEntries) {
        googlePhotosItemList.clear();
        String header = "";
        for(PhotoEntry entry : photoEntries) {
            Log.w(TAG, "Date: " + entry.getGphotoTimestamp());
            String timestamp = entry.getUpdated().getBody();
            Log.w(TAG, "timestamp: " + timestamp);
            if(!header.equals(timestamp)) {
                googlePhotosItemList.add(new GooglePhotosItem(timestamp));
                header = timestamp;
            }
            GooglePhotosItem item = new GooglePhotosItem(entry);
            googlePhotosItemList.add(item);
        }
    }
}
