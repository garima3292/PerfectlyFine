package uidesign.cs465.com.perfectlyfine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.PaymentMethod;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

/**
 * Created by oberpete on 04.12.2017.
 */

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder> {

    private static String DEBUG = "DEBUG";
    private ArrayList<PaymentMethod> paymentMethods;

    // Provides a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardNo;
        public LinearLayout delete;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            cardNo = (TextView) v.findViewById(R.id.card_no);
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
    public PaymentMethodsAdapter(ArrayList<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_method_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        // get 4 last digits of card
        String cardNo = paymentMethods.get(position).getCardNo();
        String lastFourDigits = cardNo;

        if (cardNo.length() > 4) {
            lastFourDigits = "**** **** **** ";
            lastFourDigits += cardNo.substring(cardNo.length() - 4);
        }

        holder.cardNo.setText(lastFourDigits);



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
        return paymentMethods.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}
