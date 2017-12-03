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

import java.util.ArrayList;
import java.util.Collections;

import uidesign.cs465.com.perfectlyfine.model.Order;

/**
 * Created by oberpete on 29.11.2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Order> ordersList;
    private Context context;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderNo;
        public TextView date;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            orderNo = (TextView) v.findViewById(R.id.orderItem);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    //declare interface to handle click-events on items of the RecyclerView
    private OnItemClicked onClick;

    //implement interface
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderAdapter(ArrayList<Order> ordersList, Context context) {
        this.ordersList = ordersList;
        //causes problems when activity is called several times
        //Collections.reverse(this.ordersList);
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.orderNo.setText("Order " + (position+1));
        holder.date.setText(ordersList.get(position).getOrderPlacedOn());


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
        return ordersList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }

}
