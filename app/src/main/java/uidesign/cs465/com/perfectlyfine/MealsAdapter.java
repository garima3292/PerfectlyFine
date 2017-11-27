package uidesign.cs465.com.perfectlyfine;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uidesign.cs465.com.perfectlyfine.model.Deal;
import uidesign.cs465.com.perfectlyfine.model.Meal;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Meal[] mDataset;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealName;
        public TextView price;
        public TextView portions;
        public TextView category;



        public View layout;

        public ViewHolder(View v) {
            super(v);
            mealName = (TextView) v.findViewById(R.id.mealName);
            price = (TextView) v.findViewById(R.id.price);
            portions = (TextView) v.findViewById(R.id.portions);
            category = (TextView) v.findViewById(R.id.category);
        }
    }

    //declare interface to handle click-events on items of the RecyclerView
    private OnItemClicked onClick;

    //implement interface
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MealsAdapter(Meal[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mealName.setText(mDataset[position].getMealName());
        holder.price.setText(String.valueOf(mDataset[position].getPrice()));
        holder.portions.setText(String.valueOf(mDataset[position].getPortions()));
        holder.category.setText(mDataset[position].getCategory());

        // set OnClickListener to listen for clicks on a row and pass the row-number
        // further handling of the click happens in the MainActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}