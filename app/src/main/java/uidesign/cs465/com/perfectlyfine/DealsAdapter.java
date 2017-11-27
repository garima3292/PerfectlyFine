package uidesign.cs465.com.perfectlyfine;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uidesign.cs465.com.perfectlyfine.model.Deal;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {
    private Deal[] mDataset;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;
        public TextView price;
        public TextView availabilityTime;
        public TextView distance;
        public ImageView availabilityIcon;
        public TextView availabilityUnits;
        public TextView availabilityDescription;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            restaurantName = (TextView) v.findViewById(R.id.restaurantName);
            price = (TextView) v.findViewById(R.id.price);
            availabilityTime = (TextView) v.findViewById(R.id.availabilityTime);
            distance = (TextView) v.findViewById(R.id.distance);
            availabilityIcon = (ImageView) v.findViewById(R.id.availabilityIcon);
            availabilityDescription = (TextView) v.findViewById(R.id.availabilityDescription);
            availabilityUnits = (TextView) v.findViewById(R.id.availabilityUnits);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DealsAdapter(Deal[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deals_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.restaurantName.setText(mDataset[position].getRestaurant());
        holder.price.setText(String.valueOf(mDataset[position].getPrice()));
        holder.distance.setText(String.valueOf(mDataset[position].getDistance()));

        // format the availability output
        int availability = mDataset[position].getAvailability();

        // if availability equals zero, offer is available_now now
        if (availability == 0) {
            holder.availabilityTime.setText(R.string.availableNow);

        } else {
            holder.availabilityDescription.setText(R.string.availableIfLater);
            holder.availabilityTime.setText(String.valueOf(mDataset[position].getAvailability()));
            holder.availabilityUnits.setText(R.string.availabililtyUnits);

            int iconColor = Color.LTGRAY;
            holder.availabilityIcon.setImageResource(R.drawable.available_later);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}