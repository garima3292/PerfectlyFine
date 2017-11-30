package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Order;

public class OrderHistoryActivity extends AppCompatActivity implements OrderAdapter.OnItemClicked{

    // fields for showing a list of restaurants
    private RecyclerView orderRecycler;
    private OrderAdapter orderAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restuarantsData;
    private static final String DEBUG = "Debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        Intent intent = getIntent();

        restuarantsData = RestaurantsLookupDb.getInstance();
        populateOrderList();

    }

    public void populateOrderList() {
        // get RecyclerView from activitiy_main to be populated with deal items
        orderRecycler=(RecyclerView) findViewById(R.id.order_recycler);

        // improves performance
        orderRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(mLayoutManager);

        ArrayList<Order> pastOrders = restuarantsData.getPastOrders();
        Log.d(DEBUG, "Size of pastOrders : " + pastOrders.size());

        if (pastOrders != null) {
            orderAdapter = new OrderAdapter(pastOrders, this);
            Log.d(DEBUG, "Size of pastOrders : " + pastOrders.size());
            orderRecycler.setAdapter(orderAdapter);
           // orderAdapter.setOnClick(this);// Bind the listener
        }
        else {
            Toast toast=Toast.makeText(getApplicationContext(),"currently no past orders",Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onItemClick(int position) {

    }



}
