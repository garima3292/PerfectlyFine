package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import uidesign.cs465.com.perfectlyfine.model.PaymentMethod;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

public class ManagePaymentMethodActivity extends AppCompatActivity implements PaymentMethodsAdapter.OnItemClicked {

    private RecyclerView paymentRecycler;
    private PaymentMethodsAdapter paymentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantsLookupDb restuarantsData;
    private static final String DEBUG = "Debug";

    private ArrayList<PaymentMethod> paymentMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_payment_method);

        restuarantsData = RestaurantsLookupDb.getInstance();
        populatePaymentMethodsList();

        Intent intent = getIntent();
        String msg = intent.getStringExtra("cardAdded");
        if (msg != null) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.managePaymentMethods), "Payment method successfully added.", Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
        }

    }

    public void populatePaymentMethodsList() {
        // get RecyclerView from activitiy_main to be populated with payment items
        paymentRecycler=(RecyclerView) findViewById(R.id.payment_recycler);

        // improves performance
        paymentRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        paymentRecycler.setLayoutManager(mLayoutManager);

        paymentMethods = restuarantsData.getPaymentMethods();


        if (paymentMethods.size() != 0) {
            paymentAdapter = new PaymentMethodsAdapter(paymentMethods);
            Log.d(DEBUG, "Size of pastOrders : " + paymentMethods.size());
            paymentRecycler.setAdapter(paymentAdapter);
            paymentAdapter.setOnClick(this);// Bind the listener
        }
        else {
            Toast toast=Toast.makeText(getApplicationContext(),"there are no saved payment methods",Toast.LENGTH_SHORT);
            toast.show();
        }

        final Button button = findViewById(R.id.addCard);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ManagePaymentMethodActivity.this, AddPaymentMethodActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onItemClick(int position) {

        restuarantsData.deletePaymentMethod(position);

        // show Snackbar to indicate successfull adding of item
        Snackbar snackbar = Snackbar.make(findViewById(R.id.managePaymentMethods), "Payment Method deleted", Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();

        paymentAdapter.notifyDataSetChanged();

    }


}

