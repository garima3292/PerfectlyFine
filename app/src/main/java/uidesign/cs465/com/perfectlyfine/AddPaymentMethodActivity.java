package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import uidesign.cs465.com.perfectlyfine.model.PaymentMethod;

public class AddPaymentMethodActivity extends AppCompatActivity {

    private RestaurantsLookupDb restuarantsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method);

        restuarantsData = RestaurantsLookupDb.getInstance();

        Button addPaymentMethodButton = (Button) findViewById(R.id.addCard);
        addPaymentMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup cardProvider = (RadioGroup) findViewById(R.id.card_provider);
                EditText name = (EditText) findViewById(R.id.name);
                EditText cardNo = (EditText) findViewById(R.id.card_no);
                EditText cvc = (EditText) findViewById(R.id.cvc);
                EditText expMonth = (EditText) findViewById(R.id.exp_month);
                EditText expYear = (EditText) findViewById(R.id.exp_year);

                if (isEmpty(name) || isEmpty(cardNo) || isEmpty(cvc) || isEmpty(expMonth) || isEmpty(expYear)) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.addPaymentMethod), "Please fill out all fields!", Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    return;
                }

                PaymentMethod.Provider provider;

                if (cardProvider.equals("VISA")) provider = PaymentMethod.Provider.VISA;
                else if (cardProvider.equals("Mastercard")) provider = PaymentMethod.Provider.MASTERCARD;
                else provider = PaymentMethod.Provider.AMERICANEXPRESS;

                PaymentMethod paymentMethod = new PaymentMethod(name.getText().toString(), cardNo.getText().toString(), cvc.getText().toString(),
                        expMonth.getText().toString(), expYear.getText().toString(), provider);

                restuarantsData.addPaymentMethod(paymentMethod);

                Intent intent = new Intent(AddPaymentMethodActivity.this, ManagePaymentMethodActivity.class);
                intent.putExtra("cardAdded", "true");
                startActivity(intent);
            }
        });
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }
}
