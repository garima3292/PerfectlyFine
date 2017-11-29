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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, RestaurantsAdapter.OnItemClicked {

    final LatLng CURRENT_POSITION = new LatLng(40.118196, -88.243535);
    private static final String TAG = "MainActivity";
    public static final String RESTAURANT_ID = "com.example.myfirstapp.MESSAGE";

    private RecyclerView dealsRecycler;
    private RestaurantsAdapter restaurantsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restuarantsData;
    TextView bottomSheetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(" ");
        }

        /**
         * If we want to listen for states callback
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

        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Title Dummy");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet.findViewById(R.id.bottom_sheet_title);

        behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);

        // inflate map_style
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.support_map);
        mapFragment.getMapAsync(this);

        restuarantsData = new RestaurantsLookupDb();
        restuarantsData.populateRestaurantsData();
        restuarantsData.populateDealsData();
        populateDealsList();

    }

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
}
