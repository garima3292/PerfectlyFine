package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import uidesign.cs465.com.perfectlyfine.model.Meal;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class RestaurantDetails extends AppCompatActivity {


    private Meal[] meals = {
            new Meal("Meal A", 3.5, 5, "vegetarian", new String[]{"Wheat", "Nut", "Milk", "Nut", "Milk", "Potato"}),
            new Meal("Meal B", 2.5, 3, "meat", new String[]{"Wheat", "Nut", "Milk", "Potato"}),
            new Meal("Meal C", 4.5, 1, "low-carb", new String[]{"Wheat", "Nut", "Milk", "Potato"}),
    };

    private RecyclerView mealsRecycler;
    private MealsAdapter mealsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restuarantsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String restaurantName = intent.getStringExtra(MainActivity.RESTAURANT_ID);

        populateMealsList();

    }

    public void populateMealsList() {
        // get RecyclerView from activitiy_main to be populated with meal items
        mealsRecycler=(RecyclerView) findViewById(R.id.meals_recycler);

        // improves performance
        mealsRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mealsRecycler.setLayoutManager(mLayoutManager);

        mealsAdapter = new MealsAdapter(meals, this);
        mealsRecycler.setAdapter(mealsAdapter);

        //mealsAdapter.setOnClick(this);// Bind the listener
        
    }
}
