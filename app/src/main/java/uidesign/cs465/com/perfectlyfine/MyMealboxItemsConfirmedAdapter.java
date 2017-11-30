package uidesign.cs465.com.perfectlyfine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.MealboxItem;

public class MyMealboxItemsConfirmedAdapter extends RecyclerView.Adapter<MyMealboxItemsConfirmedAdapter.MealboxItemConfirmedViewHolder>{

    private static String DEBUG = "DEBUG";
    private ArrayList<MealboxItem> myMealboxItems;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyMealboxItemsConfirmedAdapter(ArrayList<MealboxItem> myMealboxItemsList) {
        myMealboxItems = myMealboxItemsList;
    }

    // Provides a reference to the views for each data item
    public static class MealboxItemConfirmedViewHolder extends RecyclerView.ViewHolder {

        public TextView amount;
        public TextView itemName;


        public View layout;

        public MealboxItemConfirmedViewHolder(View v) {
            super(v);

            amount = (TextView) v.findViewById(R.id.amount);
            itemName = (TextView) v.findViewById(R.id.itemName);
        }
    }

    @Override
    public MyMealboxItemsConfirmedAdapter.MealboxItemConfirmedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confirmed_mealbox_item, parent, false);

        return new MyMealboxItemsConfirmedAdapter.MealboxItemConfirmedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealboxItemConfirmedViewHolder holder, int position) {
        MealboxItem mealboxItem = myMealboxItems.get(position);
        holder.amount.setText(String.valueOf(mealboxItem.getPortions()) + " x");
        holder.itemName.setText(String.valueOf(mealboxItem.getName()));
    }

    @Override
    public int getItemCount() {
        return myMealboxItems.size();
    }


}
