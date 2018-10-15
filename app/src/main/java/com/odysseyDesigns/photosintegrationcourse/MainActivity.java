package com.odysseyDesigns.photosintegrationcourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.odysseyDesigns.googlePhotos.PicasaClient;
import com.odysseyDesigns.photosintegrationcourse.R;
import com.odysseyDesigns.photosintegrationcourse.models.NavBarItem;
import com.odysseyDesigns.photosintegrationcourse.ui.googlePhotos.GooglePhotosFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,BillingProcessor.IBillingHandler  {
    private  DrawerLayout drawer;
    private  TextView placeHolderText;
    private  MenuItem accountMenu;
    BillingProcessor bp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflateViews();
        PicasaClient.get().attachActivity(this);

        bp = new BillingProcessor(this, "5cb0f930406266b6bea741e76af5e636ade6c362 ", this);
        bp.initialize();

    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        accountMenu = menu.findItem(R.id.select_account);
        accountMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.select_account) {
            PicasaClient.get().pickAccount();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {



        if (requestCode == PicasaClient.REQUEST_ACCOUNT_PICKER) {
            handleActivityResult(requestCode, resultCode, intent, GooglePhotosFragment.class);
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }




    }

    private void handleActivityResult(int requestCode, int resultCode, Intent intent, Class classObj) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment frag: fragments) {
            if (classObj.isInstance(frag)) {
                frag.onActivityResult(requestCode, resultCode, intent);
            }
        }
    }

    private void inflateViews() {
        placeHolderText = (TextView) findViewById(R.id.placeholder_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(MainActivity.this,"android.test.purchased");
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setNavBarButtons();
    }

    private void setNavBarButtons() {
        for (NavBarItem item : NavBarItem.values()) {
            TextView itemView = (TextView) findViewById(item.getItemId());
            itemView.setOnClickListener(this);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (NavBarItem.fromViewId(v.getId())) {

            case SETTINGS:
                break;

            case FACEBOOK:
                break;

            case TWITTER:
                break;

            case GOOGLE_PHOTOS:
                drawer.closeDrawer(GravityCompat.START);
                displayGooglePhotosFragment();
                accountMenu.setVisible(true);
                break;
        }
    }

    private void setFragment(Fragment fragment, String fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_layout, fragment, fragmentName)
                        .commit();
    }
    private void displayGooglePhotosFragment() {
        setTitle("Google Photos");
        setFragment(GooglePhotosFragment.newInstance(), GooglePhotosFragment.class.getSimpleName());
        placeHolderText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        Toast.makeText(this,"You've purchased something",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(this,"Something were wrong",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBillingInitialized() {

    }



    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}


