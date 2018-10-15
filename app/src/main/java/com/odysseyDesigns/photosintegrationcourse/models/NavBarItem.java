package com.odysseyDesigns.photosintegrationcourse.models;

import com.odysseyDesigns.photosintegrationcourse.R;

/**
 * Created by albertlo on 7/10/17.
 */

public enum NavBarItem {
    GOOGLE_PHOTOS(R.id.google_photos),
    TWITTER(R.id.twitter),
    FACEBOOK(R.id.facebook),
    SETTINGS(R.id.user_settings);

    private int itemId;
    NavBarItem(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public static NavBarItem fromViewId(int viewId) {
        for(NavBarItem navBarItem : NavBarItem.values()) {
            if (navBarItem.getItemId() == viewId) {
                return navBarItem;
            }
        }
        throw new IllegalStateException("Cannot find viewType");
    }
}
