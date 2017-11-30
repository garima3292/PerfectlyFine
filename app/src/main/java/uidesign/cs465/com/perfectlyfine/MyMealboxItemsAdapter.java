package uidesign.cs465.com.perfectlyfine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Deal;
import uidesign.cs465.com.perfectlyfine.model.MealboxItem;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

/**
 * Created by Garima on 29/11/17.
 */

public class MyMealboxItemsAdapter extends RecyclerView.Adapter<MyMealboxItemsAdapter.MealboxItemViewHolder> {

    private static String DEBUG = "DEBUG";

    private ArrayList<MealboxItem> myMealboxItems;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyMealboxItemsAdapter(ArrayList<MealboxItem> myMealboxItemsList) {
        myMealboxItems = myMealboxItemsList;
    }

    //declare interface to handle click-events on items of the RecyclerView
    private OnItemClicked onClick;

    //implement interface
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // Provides a reference to the views for each data item
    public static class MealboxItemViewHolder extends RecyclerView.ViewHolder {

        public TextView amount;
        public TextView itemName;
        public ImageView deleteIcon;
        public TextView restaurantName;
        public TextView distance;
        public TextView price;
        public TextView currency;

        public View layout;

        public MealboxItemViewHolder(View v) {
            super(v);

            amount = (TextView) v.findViewById(R.id.amount);
            itemName = (TextView) v.findViewById(R.id.itemName);
            deleteIcon = (ImageView) v.findViewById(R.id.deleteIcon);
            restaurantName = (TextView) v.findViewById(R.id.restaurantName);
            distance = (TextView) v.findViewById(R.id.distance);
            price = (TextView) v.findViewById(R.id.price);
            currency = (TextView) v.findViewById(R.id.currency);
        }
    }

    @Override
    public MyMealboxItemsAdapter.MealboxItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealbox_item, parent, false);

        return new MealboxItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealboxItemViewHolder holder, final int position) {
        MealboxItem mealboxItem = myMealboxItems.get(position);
        holder.amount.setText(String.valueOf(mealboxItem.getPortions()) + " x");
        holder.itemName.setText(String.valueOf(mealboxItem.getName()));
        holder.price.setText(String.valueOf(mealboxItem.getPrice() * mealboxItem.getPortions()));
        holder.restaurantName.setText(String.valueOf(mealboxItem.getRestaurantName()));
        holder.distance.setText(String.format( "%.1f", mealboxItem.getRestaurantDistance() ) + " miles away");

        // set OnClickListener to listen for clicks on a row and pass the row-number
        // further handling of the click happens in the MainActivity
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myMealboxItems.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }

}
