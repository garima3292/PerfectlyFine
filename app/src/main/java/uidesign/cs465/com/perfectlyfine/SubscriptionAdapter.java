package uidesign.cs465.com.perfectlyfine;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {
//    private Deal[] mDataset;
    private static String DEBUG = "DEBUG";
    private ArrayList<Restaurant> restuarants;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;
        public LinearLayout delete;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            restaurantName = (TextView) v.findViewById(R.id.restaurantName);
            delete = (LinearLayout) v.findViewById(R.id.delete);
        }
    }

    //declare interface to handle click-events on items of the RecyclerView
    private OnItemClicked onClick;

    //implement interface
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public RestaurantsAdapter(Deal[] myDataset) {
//        mDataset = myDataset;
//    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubscriptionAdapter(ArrayList<Restaurant> restuarantsList) {
        restuarants = restuarantsList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscriptions_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.restaurantName.setText(restuarants.get(position).getResturantName());



        // set OnClickListener to listen for clicks on a row and pass the row-number
        // further handling of the click happens in the MainActivity
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return restuarants.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}