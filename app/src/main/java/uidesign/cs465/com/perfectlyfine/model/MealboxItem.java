package uidesign.cs465.com.perfectlyfine.model;

import java.io.Serializable;

/**
 * Created by oberpete on 27.11.2017.
 */

public class MealboxItem implements Serializable {

    private int mealboxItemId;
    private String name;
    private double price;
    private int portions;
    private String restaurantName;
    private double restaurantDistance;
    //TODO:This attribute can be passed as a separate total when items are added to mealbox
    //From Mealbox activity, it can be passed as an attribute for the Order object
    private double savings;

    public MealboxItem(int mealboxItemId, String mealName, double price, double savings, int portions, String restuarantName, double restaurantDistance) {
        this.mealboxItemId = mealboxItemId;
        this.name = mealName;
        this.price = price;
        this.savings = savings;
        this.portions = portions;
        this.restaurantName = restuarantName;
        this.restaurantDistance = restaurantDistance;
    }

    public int getMealboxItemId() {
        return mealboxItemId;
    }

    public void setMealboxItemId(int mealboxItemId) {
        this.mealboxItemId = mealboxItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String mealName) {
        this.name = mealName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getRestaurantDistance() {
        return restaurantDistance;
    }

    public void setRestaurantDistance(double restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

}
