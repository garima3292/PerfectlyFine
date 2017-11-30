package uidesign.cs465.com.perfectlyfine.model;

import java.io.Serializable;

/**
 * Created by oberpete on 27.11.2017.
 */

public class MealboxItem implements Serializable {

    private String name;
    private double price;
    private int portions;

    private String restaurantName;
    private double restaurantDistance;

    public MealboxItem(String mealName, double price, int portions, String restuarantName, double restaurantDistance) {
        this.name = mealName;
        this.price = price;
        this.portions = portions;
        this.restaurantName = restuarantName;
        this.restaurantDistance = restaurantDistance;
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
}
