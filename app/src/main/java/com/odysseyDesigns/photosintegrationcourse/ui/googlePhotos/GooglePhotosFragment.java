package com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.odysseyDesigns.googlePhotos.PicasaClient;
import com.odysseyDesigns.googlePhotos.model.AlbumFeed;
import com.odysseyDesigns.googlePhotos.model.UserFeed;
import com.odysseyDesigns.photosintegrationcourse.R;
import com.odysseyDesigns.photosintegrationcourse.ui.views.GridRecyclerView;

import rx.Completable;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by albertlo on 7/11/17.
 */

public class GooglePhotosFragment extends Fragment {

    private static final String TAG = GooglePhotosFragment.class.getSimpleName();
    private static final String PREF_ACCOUNT = TAG + ".PREF_ACCOUNT";
    private SwipeRefreshLayout refreshLayout;
    private AlbumGridAdapter adapter;
    private GridRecyclerView grid;
    private TextView accountName;

    public static GooglePhotosFragment newInstance() {
        GooglePhotosFragment fragment = new GooglePhotosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GooglePhotosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_photos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        accountName = (TextView) view.findViewById(R.id.account_name);
        grid = (GridRecyclerView) view.findViewById(R.id.photo_grid);
        adapter = new AlbumGridAdapter((AppCompatActivity) getActivity());
        grid.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PicasaClient.get().isInitialized()) {
                    loadAlbum();
                }
            }
        });
        checkAccountOrLoadAlbums();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PicasaClient.get().onActivityResult(requestCode, resultCode, data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Completable.CompletableSubscriber() {
                    @Override
                    public void onCompleted() {
                        updateAccount();
                        loadAlbum();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "failed to get account" + e.getMessage());
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubscribe(Subscription d) {

                    }
                });
    }

    private void updateAccount() {
        Account account = PicasaClient.get().getAccount();
        if (account != null) {
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(PREF_ACCOUNT, account.name)
                    .apply();
        }
        accountName.setText(String.format(getString(R.string.account_name), account == null ? "None" : account.name));
    }

    private void checkAccountOrLoadAlbums() {
        if (PicasaClient.get().isInitialized()) {
            loadAlbum();
        } else {
            String savedAccount = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(PREF_ACCOUNT, null);
            if (!TextUtils.isEmpty(savedAccount)) {
                Account mAccount = new Account(savedAccount, PicasaClient.ACCOUNT_TYPE_GOOGLE);
                PicasaClient.get().setAccount(mAccount)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Completable.CompletableSubscriber() {
                            @Override
                            public void onCompleted() {
                                loadAlbum();
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e);
                            }

                            @Override
                            public void onSubscribe(Subscription d) {

                            }
                        });
            }

        }
    }

    private void loadAlbum() {
        refreshLayout.setRefreshing(true);
        PicasaClient.get().getUserFeed()
                .toObservable()
                .retry(3)
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<UserFeed>() {
                    @Override
                    public void onSuccess(UserFeed value) {
                        onLoadAlbumFinished(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        handleError(error);
                    }
                });
    }

    public void loadPhotos(long albumId) {
        refreshLayout.setRefreshing(true);
        PicasaClient.get().getAlbumFeed(albumId)
                .toObservable()
                .retry(3)
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<AlbumFeed>() {
                    @Override
                    public void onSuccess(AlbumFeed value) {
                        onLoadPhotosFinished(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        handleError(error);
                    }
                });
    }

    private void onLoadPhotosFinished(AlbumFeed feed) {
        refreshLayout.setRefreshing(false);
        adapter.setPhotosList(feed.getPhotoEntries());

    }
    private void onLoadAlbumFinished(UserFeed feed) {
        refreshLayout.setRefreshing(false);
        adapter.setAlbumList(feed.getAlbumEntries());
    }

    private void handleError(Throwable error) {
        error.printStackTrace();
        refreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
