package uidesign.cs465.com.perfectlyfine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyMealboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRecyclerView = (RecyclerView) findViewById(R.id.myrecyclerview);
    }
}
