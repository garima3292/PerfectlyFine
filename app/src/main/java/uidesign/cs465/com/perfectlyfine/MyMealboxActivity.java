package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import uidesign.cs465.com.perfectlyfine.model.Deal;
import uidesign.cs465.com.perfectlyfine.model.MealboxItem;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class MyMealboxActivity extends AppCompatActivity implements MyMealboxItemsAdapter.OnItemClicked, View.OnClickListener {

    private static final String DEBUG = "Debug";
    private RecyclerView myMealboxItemRecycler;
    private MyMealboxItemsAdapter mealboxAdapter;
    private ArrayList<MealboxItem> myMealboxItems;
    private RestaurantsLookupDb restaurantsData;
    private TextView totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mealbox);

        restaurantsData = RestaurantsLookupDb.getInstance();

        Intent intent = getIntent();
        myMealboxItems = (ArrayList<MealboxItem>) intent.getSerializableExtra("mealbox_items");
//        Log.d(DEBUG, "list size :" + myMealboxItems.size());

        populateMyMealboxItemsList();

        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);

        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        double totalPriceValue = 0;
        for(MealboxItem mealboxItem : myMealboxItems) {
            totalPriceValue += (mealboxItem.getPrice() * mealboxItem.getPortions());
        }

        totalPrice.setText(String.format( "%.2f", totalPriceValue) + " $");
    }


    public void populateMyMealboxItemsList() {
        myMealboxItemRecycler = (RecyclerView) findViewById(R.id.mealbox_item_recycler);

        //Improves performance
        myMealboxItemRecycler.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myMealboxItemRecycler.setLayoutManager(mLayoutManager);

        mealboxAdapter = new MyMealboxItemsAdapter(myMealboxItems);
        myMealboxItemRecycler.setAdapter(mealboxAdapter);

        mealboxAdapter.setOnClick(this);// Bind the listener
        mealboxAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.confirm_button) {

            ArrayList<Restaurant> restaurantsList = restaurantsData.getRestaurantsList();
            HashMap<String, ArrayList<Deal>> dealsPostedByRestauarants = restaurantsData.getDealsPostedByRestaurants();


            //Update the deals posted on the restaurant details page
            for(MealboxItem mealboxItem: myMealboxItems) {
                String restaurantName = mealboxItem.getRestaurantName();
                ArrayList<Deal> dealsByRest = dealsPostedByRestauarants.get(restaurantName);
                Deal oldDeal = dealsByRest.get(mealboxItem.getMealboxItemId());
                oldDeal.setPortions(oldDeal.getPortions() - mealboxItem.getPortions());
            }

            Intent intent = new Intent(this, ConfirmationActivity.class);
            intent.putExtra("confirmed_mealbox_items", myMealboxItems);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(int position) {
        myMealboxItems.remove(position);
        mealboxAdapter.notifyDataSetChanged();
    }
}
