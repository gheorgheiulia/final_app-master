package com.odysseyDesigns.photosintegrationcourse.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import com.odysseyDesigns.photosintegrationcourse.R;

/**
 * Created by Albert on 5/27/2017.
 */

public class GooglePhotosItemView extends SquareRelativeLayout {
    private ImageView thumb;
    private CheckBox checkBox;

    public GooglePhotosItemView(Context context) {
        super(context);
        initViews(context);
    }

    public GooglePhotosItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public GooglePhotosItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.thumb_view, this);
        thumb = (ImageView) rootView.findViewById(R.id.thumb);
        checkBox = (CheckBox) rootView.findViewById(R.id.check_box);
    }

    public void setData(String url, boolean isSelected) {
        Picasso.with(getContext()).load(url).into(thumb);
        if (isSelected) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.onClick();
        }
    }

    public void handleClick() {
        checkBox.onClick();
        if (checkBox.isChecked()) {
            checkBox.setVisibility(View.VISIBLE);
        } else  {
            checkBox.setVisibility(View.GONE);
        }
    }
}
