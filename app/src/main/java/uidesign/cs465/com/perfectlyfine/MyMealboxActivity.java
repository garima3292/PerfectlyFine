package uidesign.cs465.com.perfectlyfine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MyMealboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.mealbox_item_recycler);
    }
}
