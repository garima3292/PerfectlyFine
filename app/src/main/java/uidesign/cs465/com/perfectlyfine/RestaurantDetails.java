package uidesign.cs465.com.perfectlyfine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Deal;
import uidesign.cs465.com.perfectlyfine.model.MealboxItem;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class RestaurantDetails extends AppCompatActivity implements DealsAdapter.OnItemClicked, View.OnClickListener {

    private static final String DEBUG = "Debug";
    private RecyclerView mealsRecycler;
    private DealsAdapter dealsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restaurantsData;
    private Restaurant currentRestaurant;
    private ArrayList<MealboxItem> myMealbox;


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

        LinearLayout myMealboxView = (LinearLayout) findViewById(R.id.my_mealbox);
        myMealboxView.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.my_mealbox) {
            Intent intent = new Intent(this, MyMealboxActivity.class);
            intent.putExtra("mealbox_items", myMealbox);
            startActivity(intent);
        }
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

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        restaurantName.setText(currentRestaurant.getResturantName());
        price.setText(String.valueOf(currentRestaurant.getStartingPrice()));
        distance.setText(String.valueOf(currentRestaurant.getDistanceFromUser()));

        // format the availability output
        boolean isAvailable = currentRestaurant.isAvailableNow();

        // if availability equals zero, offer is available_now now
        if (isAvailable) {
            availabilityDescription.setText(R.string.availableNow);

        } else {
            availabilityDescription.setText(R.string.availableIfLater);
            long hrs = currentRestaurant.getAvailabilityHours();
            long mins = currentRestaurant.getAvailabilityMins();
            if(hrs > 0) {
                availabilityTimeInHrs.setText(String.valueOf(hrs));
                availabilityTimeInHrs.setPadding(5, 0,0,0);
                availabilityUnitsInHrs.setText("hrs");
                availabilityUnitsInHrs.setPadding(5,0,0,0);

            }
            if(mins > 0) {
                availabilityTimeInMins.setText(String.valueOf(mins));
                availabilityTimeInMins.setPadding(5,0, 0,0);
                availabilityUnitsInMins.setText(R.string.availabililtyUnits);
                availabilityUnitsInMins.setPadding(5,0,0,0);
            }

            int iconColor = Color.LTGRAY;
            availabilityIcon.setImageResource(R.drawable.available_later);
        }
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
        final TextView name = (TextView) dialog.findViewById(R.id.mealName);
        name.setText(deals.get(position).getName());

        final TextView price = (TextView) dialog.findViewById(R.id.price);
        price.setText(String.valueOf(deals.get(position).getPrice()));

        final TextView portions = (TextView) dialog.findViewById(R.id.portions);
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

        final NumberPicker numberPicker = dialog.findViewById(R.id.np);
        // should be bound by availability in future
        numberPicker.setMaxValue(deals.get(position).getPortions());
        numberPicker.setMinValue(0);

        // set OnClickListener to Add to mealbox button
        dialog.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // remove dialog
                dialog.dismiss();
                String feedbackString = "";
                int portionsSelected = numberPicker.getValue();
                Log.d(DEBUG, "Value selected by number picker : " + numberPicker.getValue());

                if(portionsSelected == 0) {
                    feedbackString = "No item to add";
                }
                else {
                    feedbackString = "Item added to your Mealbox";
                }

                // show Snackbar to indicate successfull adding of item
                Snackbar snackbar = Snackbar.make(findViewById(R.id.restaurantDetails), feedbackString, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();

                if(portionsSelected > 0) {
                    //Create a mealboxItem object and add it mealbox
                    if(myMealbox == null) {
                        myMealbox = new ArrayList<MealboxItem>();
                    }

                    MealboxItem item = new MealboxItem(name.getText().toString(),  Double.parseDouble(price.getText().toString()), portionsSelected, currentRestaurant.getResturantName(), currentRestaurant.getDistanceFromUser());
                    myMealbox.add(item);
                }
            }
        });

        dialog.show();


    }

}

