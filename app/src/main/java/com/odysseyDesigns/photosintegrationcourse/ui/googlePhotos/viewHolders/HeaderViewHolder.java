package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.viewHolders;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.odysseyDesigns.photosintegrationcourse.R;
import com.odysseyDesigns.photosintegrationcourse.models.GooglePhotosItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.BaseGridAdapter;

/**
 * Created by albertlo on 7/12/17.
 */

public class HeaderViewHolder extends BaseViewHolder {
    private TextView textView;

    public HeaderViewHolder(AppCompatActivity activity, ViewGroup parent) {
        super(LayoutInflater.from(activity).inflate(R.layout.google_photos_header, parent, false));
        textView = (TextView) getRootView().findViewById(R.id.label);
    }

    @Override
    public void setData(BaseGridAdapter adapter, GooglePhotosItem item, int position) {
        textView.setText(item.getTimestamp());
    }
}
