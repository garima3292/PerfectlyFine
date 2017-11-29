package uidesign.cs465.com.perfectlyfine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import uidesign.cs465.com.perfectlyfine.model.Meal;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class RestaurantDetails extends AppCompatActivity implements DealsAdapter.OnItemClicked {


    private Meal[] meals = {
            new Meal("Meal A", 3.5, 5, "vegetarian", new String[]{"Wheat", "Nut", "Milk", "Nut", "Milk", "Potato"}),
            new Meal("Meal B", 2.5, 3, "meat", new String[]{"Wheat", "Nut", "Milk", "Potato"}),
            new Meal("Meal C", 4.5, 1, "low-carb", new String[]{"Wheat", "Nut", "Milk", "Potato"}),
    };

    private RecyclerView mealsRecycler;
    private DealsAdapter dealsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restaurantsData;
    private Restaurant currentRestaurant;


    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String restaurantName = intent.getStringExtra(MainActivity.RESTAURANT_ID);

        restaurantsData = new RestaurantsLookupDb();
        restaurantsData.populateRestaurantsData();
        restaurantsData.populateDealsData();

        //get object of current restaurant that involves a list of all the deals offered
        currentRestaurant = restaurantsData.getRestaurantDealsByName(restaurantName);

        // fill the Views with the corresponding restaurant details
        populateRestaurantDetails();

        // populate the RecyclerView containing the Deals
        populateMealsList();

    }

    public void populateRestaurantDetails() {
        TextView restaurantName = (TextView) this.findViewById(R.id.restaurantName);
        TextView price = (TextView) this.findViewById(R.id.price);
        TextView distance = (TextView) this.findViewById(R.id.distance);
        ImageView availabilityIcon = (ImageView) this.findViewById(R.id.availabilityIcon);
        TextView availabilityDescription = (TextView) this.findViewById(R.id.availabilityDescription);
        TextView availabilityTimeInHrs = (TextView) this.findViewById(R.id.availabilityTimeInHrs);
        TextView availabilityUnitsInHrs = (TextView) this.findViewById(R.id.availabilityUnitsInHrs);
        TextView availabilityTimeInMins = (TextView) this.findViewById(R.id.availabilityTimeInMins);
        TextView availabilityUnitsInMins = (TextView) this.findViewById(R.id.availabilityUnitsInMins);

        restaurantName.setText(currentRestaurant.getResturantName());
        price.setText(String.valueOf(currentRestaurant.getStartingPrice()));
        //availabilityTime.setText(String.valueOf(currentRestaurant.isAvailableNow()));
        distance.setText(String.valueOf(currentRestaurant.getDistanceFromUser()));
    }

    public void populateMealsList() {
        // get RecyclerView from activitiy_main to be populated with meal items
        mealsRecycler=(RecyclerView) findViewById(R.id.meals_recycler);

        // improves performance
        mealsRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mealsRecycler.setLayoutManager(mLayoutManager);

        dealsAdapter = new DealsAdapter(currentRestaurant, this);
        mealsRecycler.setAdapter(dealsAdapter);

        dealsAdapter.setOnClick(this);// Bind the listener
    }

    @Override
    public void onItemClick(int position) {
        showMealPopUp(position);

    }

    public void showMealPopUp(int position) {

        // create a dialog-pop up to show the details of a meal and
        // provide a method to add meals to your mealbox
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_deal_pop_up);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // fill the dialog views with the corresponding data
        TextView name = (TextView) dialog.findViewById(R.id.mealName);
        name.setText(currentRestaurant.getDeals().get(position).getName());

        dialog.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // remove dialog
                dialog.dismiss();

            }
        });

        NumberPicker numberPicker = dialog.findViewById(R.id.np);
        // should be bound by availability in future
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        dialog.show();
    }

}
