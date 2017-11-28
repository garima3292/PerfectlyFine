package uidesign.cs465.com.perfectlyfine.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by oberpete on 25.11.2017.
 */

//public class Deal {
//    private String restaurant;
//    private double price;
//    private double distance;
//    private int availability;
//    private LatLng location;
//
//    public LatLng getLocation() {
//        return location;
//    }
//
//    public void setLocation(LatLng location) {
//        this.location = location;
//    }
//
//    public Deal (String restaurant, double price, double distance, int availability, LatLng location) {
//        this.restaurant = restaurant;
//        this.price = price;
//        this.distance = distance;
//        this.availability = availability;
//        this.location = location;
//
//    }
//
//    public String getRestaurant() {
//        return restaurant;
//    }
//
//    public void setRestaurant(String restaurant) {
//        this.restaurant = restaurant;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public double getDistance() {
//        return distance;
//    }
//
//    public void setDistance(double distance) {
//        this.distance = distance;
//    }
//
//    public int getAvailability() {
//        return availability;
//    }
//
//    public void setAvailability(int availability) {
//        this.availability = availability;
//    }
//}

public class Deal {
    private String name;
    private float price;
    private int portions;
    private String []contains;
    private boolean isItVeg;

    public Deal(String name, float price, int portions, String []contains, boolean isItVeg) {
        this.name = name;
        this.price = price;
        this.portions = portions;
        this.contains = contains;
        this.isItVeg = isItVeg;
    }

    public float getPrice() {
        return price;
    }

}
