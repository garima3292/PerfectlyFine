package uidesign.cs465.com.perfectlyfine;

import android.graphics.Color;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
//    private Deal[] mDataset;
    private static String DEBUG = "DEBUG";
    private ArrayList<Restaurant> restuarants;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;
        public TextView price;
        public TextView availabilityTimeInHrs;
        public TextView availabilityUnitsInHrs;
        public TextView availabilityTimeInMins;
        public TextView availabilityUnitsInMins;
        public TextView distance;
        public ImageView availabilityIcon;
        public TextView availabilityDescription;
        public ImageView favorite;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            restaurantName = (TextView) v.findViewById(R.id.restaurantName);
            price = (TextView) v.findViewById(R.id.price);
            distance = (TextView) v.findViewById(R.id.distance);
            availabilityIcon = (ImageView) v.findViewById(R.id.availabilityIcon);
            availabilityDescription = (TextView) v.findViewById(R.id.availabilityDescription);
            availabilityTimeInHrs = (TextView) v.findViewById(R.id.availabilityTimeInHrs);
            availabilityUnitsInHrs = (TextView) v.findViewById(R.id.availabilityUnitsInHrs);
            availabilityTimeInMins = (TextView) v.findViewById(R.id.availabilityTimeInMins);
            availabilityUnitsInMins = (TextView) v.findViewById(R.id.availabilityUnitsInMins);
            favorite = (ImageView) v.findViewById(R.id.star);
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
    public RestaurantsAdapter(ArrayList<Restaurant> restuarantsList) {
        restuarants = restuarantsList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RestaurantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
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
        holder.price.setText(String.valueOf(restuarants.get(position).getStartingPrice()));
        holder.distance.setText(String.valueOf(restuarants.get(position).getDistanceFromUser()));

        // set the star-icon based on whether the restaurant has been marked
        // as a favorite or not
        if(restuarants.get(position).isFavorite()) {
            holder.favorite.setImageResource(R.drawable.star_filled);
        } else {
            holder.favorite.setImageResource(R.drawable.star_empty);
        }


        // format the availability output
        boolean isAvailable = restuarants.get(position).isAvailableNow();

        // if availability equals zero, offer is available_now now
        if (isAvailable) {
            holder.availabilityDescription.setText(R.string.availableNow);
//            holder.availabilityTimeinMins.setText(R.string.availableNow);

        } else {
            holder.availabilityDescription.setText(R.string.availableIfLater);
            long hrs = restuarants.get(position).getAvailabilityHours();
            long mins = restuarants.get(position).getAvailabilityMins();
            if(hrs > 0) {
                holder.availabilityTimeInHrs.setText(String.valueOf(hrs));
                holder.availabilityTimeInHrs.setPadding(5, 0,0,0);
                holder.availabilityUnitsInHrs.setText("hrs");
                holder.availabilityUnitsInHrs.setPadding(5,0,0,0);

            }
            if(mins > 0) {
                holder.availabilityTimeInMins.setText(String.valueOf(mins));
                holder.availabilityTimeInMins.setPadding(5,0, 0,0);
                holder.availabilityUnitsInMins.setText(R.string.availabililtyUnits);
                holder.availabilityUnitsInMins.setPadding(5,0,0,0);
            }

            int iconColor = Color.LTGRAY;
            holder.availabilityIcon.setImageResource(R.drawable.available_later);
        }

        // set OnClickListener to listen for clicks on a row and pass the row-number
        // further handling of the click happens in the MainActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(restuarants.get(position).isFavorite()) {
                    restuarants.get(position).setFavorite(false);
                    holder.favorite.setImageResource(R.drawable.star_empty);
                } else {
                    restuarants.get(position).setFavorite(true);
                    holder.favorite.setImageResource(R.drawable.star_filled);
                }
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