package uidesign.cs465.com.perfectlyfine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Deal;
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
        mealsRecycler = (RecyclerView) findViewById(R.id.meals_recycler);

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

        // deals to be displayed
        ArrayList<Deal> deals = currentRestaurant.getDeals();

        // create a dialog-pop up to show the details of a meal and
        // provide a method to add meals to your mealbox
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_deal_pop_up);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // fill the dialog views with the corresponding data
        TextView name = (TextView) dialog.findViewById(R.id.mealName);
        name.setText(deals.get(position).getName());

        TextView price = (TextView) dialog.findViewById(R.id.price);
        price.setText(String.valueOf(deals.get(position).getPrice()));

        TextView portions = (TextView) dialog.findViewById(R.id.portions);
        portions.setText(String.valueOf(deals.get(position).getPortions()));

        //TextView category = (TextView) dialog.findViewById(R.id.mealName);
        //name.setText(String.valueOf(currentRestaurant.getDeals().get(position).getPortions()));

        // add TextViews to the LinearLayout ingredients
        // add one view for every ingredient to make tag-like appearance
        String[] items = deals.get(position).getContains();
        LinearLayout ingredients = (LinearLayout) dialog.findViewById(R.id.ingredientsPopUp);

        for (String item : items) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 10, 10, 5);
            TextView textView = new TextView(ingredients.getContext());
            textView.setLayoutParams(params);
            textView.setBackgroundResource(R.drawable.rounded_corner_green);
            textView.setText(item.toUpperCase());
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fontSizeDetails));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setPadding(10, 10, 10, 10);

            ingredients.addView(textView);
        }

        // set OnClickListener to Add to mealbox button
        dialog.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // remove dialog
                dialog.dismiss();

            }
        });

        NumberPicker numberPicker = dialog.findViewById(R.id.np);
        // should be bound by availability in future
        numberPicker.setMaxValue(deals.get(position).getPortions());
        numberPicker.setMinValue(0);

        dialog.show();
    }
    
}

