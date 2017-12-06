package uidesign.cs465.com.perfectlyfine.model;

import java.util.ArrayList;

/**
 * Created by Garima on 29/11/17.
 */

public class Order {

    private String orderPlacedOn;
    private ArrayList<MealboxItem> confirmedMealboxItems;
    private double savings;

    public String getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(String orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    public Order(String dateOfOrder, ArrayList<MealboxItem> confirmedMealboxItems, double savings) {
        this.orderPlacedOn = dateOfOrder;
        this.confirmedMealboxItems = confirmedMealboxItems;
        this.savings = savings;
    }
    public ArrayList<MealboxItem> getConfirmedMealboxItems() {
        return confirmedMealboxItems;
    }

    public void setConfirmedMealboxItems(ArrayList<MealboxItem> confirmedMealboxItems) {
        this.confirmedMealboxItems = confirmedMealboxItems;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }


}
