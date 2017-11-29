package uidesign.cs465.com.perfectlyfine;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {
    private Restaurant restaurant;
    private Context context;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealName;
        public TextView price;
        public TextView portions;
        public TextView category;
        public LinearLayout ingredients;



        public View layout;

        public ViewHolder(View v) {
            super(v);
            mealName = (TextView) v.findViewById(R.id.mealName);
            price = (TextView) v.findViewById(R.id.price);
            portions = (TextView) v.findViewById(R.id.portions);
            category = (TextView) v.findViewById(R.id.category);
            ingredients = (LinearLayout) v.findViewById(R.id.ingredients);
        }
    }

    //declare interface to handle click-events on items of the RecyclerView
    private OnItemClicked onClick;

    //implement interface
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DealsAdapter(Restaurant restaurant, Context context) {
        this.restaurant = restaurant;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mealName.setText(restaurant.getDeals().get(position).getName());
        holder.price.setText(String.valueOf(restaurant.getDeals().get(position).getPrice()));
        holder.portions.setText(String.valueOf(restaurant.getDeals().get(position).getPortions()));
        //holder.category.setText(restaurant.getDeals().get(position).isItVeg());

        // add TextViews to the LinearLayout ingredients
        // add one view for every ingredient to make tag-like appearance
        String[] items = restaurant.getDeals().get(position).getContains();

        for (String item : items) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0,10,10,5);
            TextView textView = new TextView(holder.ingredients.getContext());
            textView.setLayoutParams(params);
            textView.setBackgroundResource(R.drawable.rounded_corner_green);
            textView.setText(item.toUpperCase());
            textView.setTextColor(context.getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.fontSizeDetails));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setPadding(10,10,10,10);

            holder.ingredients.addView(textView);
        }


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
        return restaurant.getDeals().size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}