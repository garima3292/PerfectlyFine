package uidesign.cs465.com.perfectlyfine.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Garima on 27/11/17.
 */

public class Restaurant {

    private String restId;
    private String resturantName;
    private float distanceFromUser;
    private LatLng location;
    private ArrayList<Deal> deals;
    private boolean isAvailableNow;
    private long availabilityTime;

    public Restaurant(String restId, String resturantName, LatLng location) {
        this.restId = restId;
        this.resturantName = resturantName;
        this.location = location;
        this.distanceFromUser = 0.3f;//TODO: should be calculated from user's location and restaurant's location that was just passed
    }

    public Restaurant(String restId, float distanceFromUser, LatLng location, ArrayList<Deal> dealsPosted, boolean isAvailableNow) {
        this.restId = restId;
        this.distanceFromUser = distanceFromUser;
        this.location = location;
        this.deals = dealsPosted;
        this.isAvailableNow = isAvailableNow;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public ArrayList<Deal> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<Deal> deals) {
        this.deals = deals;
    }

    public float getStartingPrice() {
        ArrayList<Deal> deals = getDeals();
        if(deals != null && !deals.isEmpty())
            return deals.get(0).getPrice();
        else
            return 2.5f;
    }


    public float getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(float distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }


    public boolean isAvailableNow() {
        return isAvailableNow;
    }

    public void setAvailableNow(boolean availableNow) {
        isAvailableNow = availableNow;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    public long getAvailabilityTime() {
        return availabilityTime;
    }

    public void setAvailabilityTime(long availabilityTime) {
        this.availabilityTime = availabilityTime;
    }

    public long getAvailabilityHours() {
        long hrs = 0;
        if(!this.isAvailableNow) {
            hrs = getAvailabilityTime()/(60 * 60);
        }
        return hrs;
    }

    public long getAvailabilityMins() {
        long hrs=0,mins = 0;
        if(!this.isAvailableNow) {
            hrs = getAvailabilityTime()/(60 * 60);
            mins = (getAvailabilityTime() - hrs * 3600)/60;
        }
        return mins;
    }

}
