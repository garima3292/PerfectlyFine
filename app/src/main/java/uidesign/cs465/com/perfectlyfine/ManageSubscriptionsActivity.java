package uidesign.cs465.com.perfectlyfine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class ManageSubscriptionsActivity extends AppCompatActivity implements SubscriptionAdapter.OnItemClicked{

    private RecyclerView subscriptionRecycler;
    private SubscriptionAdapter subscriptionAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restuarantsData;
    private ArrayList<Restaurant> restaurants;
    private static final String DEBUG = "Debug";

    private ArrayList<Restaurant> subscriptions = new ArrayList<Restaurant>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_subscriptions);

        restuarantsData = RestaurantsLookupDb.getInstance();
        populateSubscriptionList();
    }

    public void populateSubscriptionList() {
        // get RecyclerView from activitiy_main to be populated with subscription items
        subscriptionRecycler=(RecyclerView) findViewById(R.id.subscriptions_recycler);

        // improves performance
        subscriptionRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        subscriptionRecycler.setLayoutManager(mLayoutManager);

        restaurants = restuarantsData.getRestaurantsList();

        // retrieve all restaurants that are set as favorite
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).isFavorite()) {
                subscriptions.add(restaurants.get(i));
            }
        }

        if (subscriptions != null) {
            subscriptionAdapter = new SubscriptionAdapter(subscriptions);
            Log.d(DEBUG, "Size of pastOrders : " + subscriptions.size());
            subscriptionRecycler.setAdapter(subscriptionAdapter);
            subscriptionAdapter.setOnClick(this);// Bind the listener
        }
        else {
            Toast toast=Toast.makeText(getApplicationContext(),"currently no subscriptions",Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onItemClick(int position) {
        String restaurantName = subscriptions.get(position).getResturantName();
        subscriptions.remove(position);

        // set the isFavorite field of the restaurant that has been clicked to false
        for(Restaurant res : restaurants) {
            if(res.getResturantName().equalsIgnoreCase(restaurantName)) {
                res.setFavorite(false);
            }
        }

        subscriptionAdapter.notifyDataSetChanged();

    }
}
