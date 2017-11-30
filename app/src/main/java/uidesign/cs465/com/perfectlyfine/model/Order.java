package uidesign.cs465.com.perfectlyfine.model;

import java.util.ArrayList;

/**
 * Created by Garima on 29/11/17.
 */

public class Order {

    public String getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(String orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    private String orderPlacedOn;
    private ArrayList<MealboxItem> confirmedMealboxItems;

    public Order(String dateOfOrder, ArrayList<MealboxItem> confirmedMealboxItems) {
        this.orderPlacedOn = dateOfOrder;
        this.confirmedMealboxItems = confirmedMealboxItems;
    }
    public ArrayList<MealboxItem> getConfirmedMealboxItems() {
        return confirmedMealboxItems;
    }

    public void setConfirmedMealboxItems(ArrayList<MealboxItem> confirmedMealboxItems) {
        this.confirmedMealboxItems = confirmedMealboxItems;
    }

}
