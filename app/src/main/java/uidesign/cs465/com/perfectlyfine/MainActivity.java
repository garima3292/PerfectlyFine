package uidesign.cs465.com.perfectlyfine;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.lib.BottomSheetBehaviorGoogleMapsLike;
import uidesign.cs465.com.perfectlyfine.lib.MergedAppBarLayoutBehavior;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, RestaurantsAdapter.OnItemClicked, NavigationView.OnNavigationItemSelectedListener {

    // Constant to simulate current position for testing purposes
    final LatLng CURRENT_POSITION = new LatLng(40.118196, -88.243535);

    private static final String TAG = "MainActivity";
    public static final String RESTAURANT_ID = "com.example.myfirstapp.MESSAGE";

    // fields for showing a list of restaurants
    private RecyclerView dealsRecycler;
    private RestaurantsAdapter restaurantsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restuarantsData;
    TextView bottomSheetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Perfectly Fine");
        }

        /**
         * listen for callbacks of the state of the BottomSheet
         */
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        Log.d("bottomsheet-", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        Log.d("bottomsheet-", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:
                        Log.d("bottomsheet-", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:
                        Log.d("bottomsheet-", "STATE_ANCHOR_POINT");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:
                        Log.d("bottomsheet-", "STATE_HIDDEN");
                        break;
                    default:
                        Log.d("bottomsheet-", "STATE_SETTLING");
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });


        // add navigation drawer
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        // set AppBar with Button to open Menu
        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle(getResources().getString(R.string.app_name));
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet.findViewById(R.id.bottom_sheet_title);

        behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);

        // inflate map_style
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.support_map);
        mapFragment.getMapAsync(this);

        // populate the list of Restaurants
        restuarantsData = new RestaurantsLookupDb();
        restuarantsData.populateRestaurantsData();
        restuarantsData.populateDealsData();
        populateDealsList();

    }

    // callback when map was fully loaded
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map_style using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG,"Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        // add Marker indicating your current position
        googleMap.addMarker(new MarkerOptions()
                .position(CURRENT_POSITION)
                .title("That's me!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.happy)));


        // add marker for each deal
        ArrayList<Restaurant> restaurantsList = restuarantsData.getRestaurantsList();
        for (Restaurant restaurant : restaurantsList) {
            // Bitmap shown on map to indicate deal
            BitmapDescriptor markerIcon;

            // if the deal is available_now now, pick green icon, else pick the gray one
            if (restaurant.isAvailableNow()) {
                markerIcon = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.deal_now);
            } else {
                markerIcon = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.deal_later);
            }
            googleMap.addMarker(new MarkerOptions()
                    .position(restaurant.getLocation())
                    .title(restaurant.getResturantName())
                    .icon(markerIcon)
            );
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_POSITION, 18));

    }

    // converts our custom map-marker from vector to bitmap, as google maps doesn't allow vector markers
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void populateDealsList() {
        // get RecyclerView from activitiy_main to be populated with deal items
        dealsRecycler=(RecyclerView) findViewById(R.id.deals_recycler);

        // improves performance
        dealsRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        dealsRecycler.setLayoutManager(mLayoutManager);

        restaurantsAdapter = new RestaurantsAdapter(restuarantsData.getRestaurantsList());
        dealsRecycler.setAdapter(restaurantsAdapter);

        restaurantsAdapter.setOnClick(this);// Bind the listener

        // create vertical line to divide rows of RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dealsRecycler.getContext(), DividerItemDecoration.VERTICAL);
        dealsRecycler.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RestaurantDetails.class);
        String restaurant = "Restaurant " + String.valueOf(position+1);
        intent.putExtra(RESTAURANT_ID, restaurant);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_manage_payments) {

        } else if (id == R.id.nav_order_history) {
            Toast toast = Toast.makeText(this, "Order History clicked", Toast.LENGTH_LONG);
            toast.show();
        } else if (id == R.id.nav_my_subscriptions) {

        } else if (id == R.id.nav_promo_code) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
