package uidesign.cs465.com.perfectlyfine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uidesign.cs465.com.perfectlyfine.R;
import uidesign.cs465.com.perfectlyfine.model.MealboxItem;
import uidesign.cs465.com.perfectlyfine.model.Order;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ConfirmationActivity extends AppCompatActivity {

    private static final String DEBUG = "Debug";
    private ArrayList<MealboxItem> confirmedMealboxItems;
    private RecyclerView myMealboxItemsRecycler;
    private TextView restuarantName;
    private TextView dateView;
    private MyMealboxItemsConfirmedAdapter mealboxItemsConfirmedAdapter;
    private RestaurantsLookupDb restaurantsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        restaurantsData = RestaurantsLookupDb.getInstance();

        Intent intent = getIntent();
        confirmedMealboxItems = (ArrayList<MealboxItem>) intent.getSerializableExtra("confirmed_mealbox_items");
        dateView = (TextView) findViewById(R.id.date);

        // get current date
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String date = dateobj.toString();

        dateView.setText(date);


        Order newOrder = new Order(date, confirmedMealboxItems);
        restaurantsData.addOrder(newOrder);

        populateConfirmationReceipt();
    }

    public void populateConfirmationReceipt() {
        if(! confirmedMealboxItems.isEmpty()) {
            restuarantName = (TextView) findViewById(R.id.restaurantName);
            restuarantName.setText(confirmedMealboxItems.get(0).getRestaurantName());

            myMealboxItemsRecycler = (RecyclerView) findViewById(R.id.mealbox_item_recycler);

            //Improves performance
            myMealboxItemsRecycler.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            myMealboxItemsRecycler.setLayoutManager(mLayoutManager);

            mealboxItemsConfirmedAdapter = new MyMealboxItemsConfirmedAdapter(confirmedMealboxItems);
            myMealboxItemsRecycler.setAdapter(mealboxItemsConfirmedAdapter);

            //        mealboxAdapter.setOnClick(this);// Bind the listener
            mealboxItemsConfirmedAdapter.notifyDataSetChanged();
        }
    }
}
