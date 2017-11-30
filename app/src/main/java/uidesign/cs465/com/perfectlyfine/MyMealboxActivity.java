package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.MealboxItem;

public class MyMealboxActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DEBUG = "Debug";
    private RecyclerView myMealboxItemRecycler;
    private MyMealboxItemsAdapter mealboxAdapter;
    private ArrayList<MealboxItem> myMealboxItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mealbox);

        Intent intent = getIntent();
        myMealboxItems = (ArrayList<MealboxItem>) intent.getSerializableExtra("mealbox_items");
        Log.d(DEBUG, "list size :" + myMealboxItems.size());

        populateMyMealboxItemsList();

        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);

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

//        mealboxAdapter.setOnClick(this);// Bind the listener
        mealboxAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.confirm_button) {
            Intent intent = new Intent(this, ConfirmationActivity.class);
            intent.putExtra("confirmed_mealbox_items", myMealboxItems);
            startActivity(intent);
        }
    }
}
