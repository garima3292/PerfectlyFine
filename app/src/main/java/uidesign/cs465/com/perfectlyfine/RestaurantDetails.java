package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import uidesign.cs465.com.perfectlyfine.model.Meal;

public class RestaurantDetails extends AppCompatActivity {

    private Meal[] deals = {
            new Meal("Meal A", 3.5, 5, "vegetarian"),
            new Meal("Meal B", 2.5, 3, "meat"),
            new Meal("Meal C", 4.5, 1, "low-carb"),
    };

    private RecyclerView dealsRecycler;
    private DealsAdapter dealsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.RESTAURANT_ID);



    }
}
