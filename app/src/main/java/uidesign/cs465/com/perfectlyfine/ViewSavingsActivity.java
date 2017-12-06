package uidesign.cs465.com.perfectlyfine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uidesign.cs465.com.perfectlyfine.model.MealboxItem;
import uidesign.cs465.com.perfectlyfine.model.Order;

import static android.app.PendingIntent.getActivity;

public class ViewSavingsActivity extends AppCompatActivity {

    private RestaurantsLookupDb restaurantsData;
    ArrayList<Order> pastOrders;
    private FrameLayout dataNotFoundView;
    private GraphView graphView;
    private TextView savingsView;
    private TextView mealsSavedView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_savings);

        dataNotFoundView = (FrameLayout) findViewById(R.id.data_not_found);
        graphView = (GraphView) findViewById(R.id.graph);
        savingsView = (TextView) findViewById(R.id.money_savings);
        mealsSavedView = (TextView) findViewById(R.id.meal_savings);

        calendar = Calendar.getInstance();

        Intent intent = getIntent();
        restaurantsData = RestaurantsLookupDb.getInstance();
        populateGraphs();



    }


    public void populateGraphs() {
        pastOrders = restaurantsData.getPastOrders();
        if(pastOrders == null) {
            dataNotFoundView.setVisibility(View.VISIBLE);
            graphView.setVisibility(View.GONE);
            savingsView.setVisibility(View.GONE);
        }
        else {
            ArrayList<DataPoint> dataPoints1 = new ArrayList<DataPoint>();
            ArrayList<DataPoint> dataPoints2 = new ArrayList<DataPoint>();
            int mealPortionsSaved = 0;
            double savings = 0;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd / HH:mm");

            for (int i = 0; i < pastOrders.size(); i++) {
                Order currentOrder = pastOrders.get(i);
                savings += currentOrder.getSavings();
                Date date = null;
                try {
                    date = df.parse(currentOrder.getOrderPlacedOn());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<MealboxItem> mealsForOrder = currentOrder.getConfirmedMealboxItems();
                for(MealboxItem meal: mealsForOrder) {
                    mealPortionsSaved += meal.getPortions();
                }
                dataPoints1.add(new DataPoint(date , currentOrder.getSavings()));
                calendar.add(Calendar.DATE, 1);
                dataPoints2.add(new DataPoint(date, mealPortionsSaved));
            }

            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> lineGraphSeries1 = new LineGraphSeries<DataPoint>(dataPoints1.toArray(new DataPoint[dataPoints1.size()]));
            LineGraphSeries<DataPoint> lineGraphSeries2 = new LineGraphSeries<DataPoint>(dataPoints1.toArray(new DataPoint[dataPoints1.size()]));


            //show axis titles
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
            graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.DKGRAY);
            graph.getGridLabelRenderer().setVerticalAxisTitle("Savings($)/Meal Portions");
            graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.DKGRAY);

            // set date label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(ViewSavingsActivity.this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints1.size()); // only 4 because of the space

            // set manual x bounds to have nice steps
            graph.getViewport().setMinX(dataPoints1.get(0).getX());
            graph.getViewport().setMaxX(dataPoints1.get(dataPoints1.size()-1).getX()+1);
            graph.getViewport().setXAxisBoundsManual(true);

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);

            graph.getViewport().setScrollable(true);

            graph.addSeries(lineGraphSeries1);
            graph.addSeries(lineGraphSeries2);


            //TextView Update
            savingsView.setText("Congrats! You saved " + String.valueOf(savings) + " $ !  ");
            mealsSavedView.setText("And " + String.valueOf(mealPortionsSaved) + " meal portions from getting wasted ");

        }



    }
}
