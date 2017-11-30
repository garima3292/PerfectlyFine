package uidesign.cs465.com.perfectlyfine.model;

import java.util.ArrayList;

/**
 * Created by Garima on 29/11/17.
 */

public class Order {

    private ArrayList<MealboxItem> confirmedMealboxItems;

    public Order(ArrayList<MealboxItem> confirmedMealboxItems) {
        this.confirmedMealboxItems = confirmedMealboxItems;
    }
    public ArrayList<MealboxItem> getConfirmedMealboxItems() {
        return confirmedMealboxItems;
    }

    public void setConfirmedMealboxItems(ArrayList<MealboxItem> confirmedMealboxItems) {
        this.confirmedMealboxItems = confirmedMealboxItems;
    }

}
