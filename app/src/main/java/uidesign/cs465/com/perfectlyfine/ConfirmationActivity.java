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

import static android.provider.LiveFolders.INTENT;

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

    // indicates whether action was called directly after purchase or from OrderHistoryActivity
    private boolean calledOnPurchase;


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

        // distinguish between which activity called the ConfirmationActivity
        // either MyMealboxActivity (then orderPosition = null) or OrderHistoryActivity (then confirmedMealboxItems = null)
        if (confirmedMealboxItems == null) {
            calledOnPurchase = false;

            int orderPosition = Integer.valueOf(intent.getStringExtra(OrderHistoryActivity.ORDER_POS));
            Order order = restaurantsData.getPastOrders().get(orderPosition);
            populateConfirmationReceiptAfterPurchase(order);

        } else {
            calledOnPurchase = true;

            populateConfirmationReceiptOnPurchase();
        }

        intent.removeExtra("confirmed_mealbox_items");
        intent.removeExtra(OrderHistoryActivity.ORDER_POS);

    }

    public void populateConfirmationReceiptOnPurchase() {
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

            // get current date and set the respective TextField
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd / HH:mm");
            Date dateobj = new Date();
            String date = df.format(dateobj);
            dateView = (TextView) findViewById(R.id.date);
            dateView.setText(date);

            // save the Order
            Order newOrder = new Order(date, confirmedMealboxItems);
            restaurantsData.addOrder(newOrder);

        }
    }


    public void populateConfirmationReceiptAfterPurchase(Order order) {

        restuarantName = (TextView) findViewById(R.id.restaurantName);
        restuarantName.setText(order.getConfirmedMealboxItems().get(0).getRestaurantName());

        myMealboxItemsRecycler = (RecyclerView) findViewById(R.id.mealbox_item_recycler);

        //Improves performance
        myMealboxItemsRecycler.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myMealboxItemsRecycler.setLayoutManager(mLayoutManager);

        mealboxItemsConfirmedAdapter = new MyMealboxItemsConfirmedAdapter(order.getConfirmedMealboxItems());
        myMealboxItemsRecycler.setAdapter(mealboxItemsConfirmedAdapter);

        // mealboxAdapter.setOnClick(this);// Bind the listener
        mealboxItemsConfirmedAdapter.notifyDataSetChanged();

        dateView = (TextView) findViewById(R.id.date);
        dateView.setText(order.getOrderPlacedOn());



    }

    // set up-button (back-button) based on which activity called the ConfirmationActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (calledOnPurchase) {
                    Intent intent = new Intent (this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(this, OrderHistoryActivity.class);
                    startActivity(intent);
                }
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
