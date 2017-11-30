package uidesign.cs465.com.perfectlyfine;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import uidesign.cs465.com.perfectlyfine.model.Deal;
import uidesign.cs465.com.perfectlyfine.model.Order;
import uidesign.cs465.com.perfectlyfine.model.Restaurant;

/**
 * Created by Garima on 27/11/17.
 */

//TODO: Use a db to store all this data; Currently storing in memory
//Created a singleton class to give data objects
public class RestaurantsLookupDb {

    private static RestaurantsLookupDb restaurantsLookupDbObject;
    private ArrayList<Restaurant> restaurantsList;
    private HashMap<String, ArrayList<Deal>> dealsPostedByRestaurants;
    private ArrayList<Order> ordersList;

    //Hardcoded Restaurants list
    //Ideally this data should be updated whenever a new restarant owner signs up
    public void populateRestaurantsData() {

        this.restaurantsList = new ArrayList<Restaurant>();
        //TODO: Change restId to unique Ids
//        restaurantsList.add(new Restaurant(UUID.randomUUID().toString(), "Restaurant 1", new LatLng(40.118171, -88.243212)));
        restaurantsList.add(new Restaurant("Restaurant 1", "Restaurant 1", new LatLng(40.118300, -88.243248)));
        restaurantsList.add(new Restaurant("Restaurant 2", "Restaurant 2", new LatLng(40.118112, -88.243791)));
        restaurantsList.add(new Restaurant("Restaurant 3", "Restaurant 3", new LatLng(40.117882, -88.243343)));
        restaurantsList.add(new Restaurant("Restaurant 4", "Restaurant 4", new LatLng(40.117855, -88.243166 )));
        restaurantsList.add(new Restaurant("Restaurant 5", "Restaurant 5", new LatLng(40.118476, -88.243756 )));
        restaurantsList.add(new Restaurant("Restaurant 6", "Restaurant 6", new LatLng(40.118769, -88.243761 )));
    }

    //Hardcoded Deals Data
    //Ideally this data should be stored in a database and updates whenever a restaurant posts new deals/ updates old deals or
    //whenever a user orders and portions get reduced
    public void populateDealsData() {
        this.dealsPostedByRestaurants = new HashMap<String, ArrayList<Deal>>();
        ArrayList<Deal> deals = new ArrayList<Deal>();


        deals.add(new Deal("Pizza", 2.5f, 6, new String[]{"Wheat", "Mushrooms", "Cheese"}, true));
        deals.add(new Deal("Pizza", 2f, 2, new String[]{"Whole Wheat", "Beef", "Cheese"}, false));
        deals.add(new Deal("Garlic Bread", 1.5f, 5, new String[]{"Wheat", "Garlic"}, true));
        deals.add(new Deal("Chicken Wings", 3.5f, 4, new String[]{"organic Chicken", "Nuts"}, false));

        dealsPostedByRestaurants.put("Restaurant 1", deals);
        dealsPostedByRestaurants.put("Restaurant 2", deals);
        dealsPostedByRestaurants.put("Restaurant 3", deals);
        dealsPostedByRestaurants.put("Restaurant 4", deals);
        dealsPostedByRestaurants.put("Restaurant 5", deals);
        dealsPostedByRestaurants.put("Restaurant 6", deals);

        //Statically setting deals for each restuarant
        ArrayList<Restaurant> restaurantsList = getRestaurantsList();
        for(Restaurant res : restaurantsList) {
            res.setDeals(dealsPostedByRestaurants.get(res.getResturantName()));
        }

        //Statically setting availabity time data for all the restaurants
        //Ideally these should be set when restaurant is posting deals
        this.restaurantsList.get(0).setAvailableNow(true);
        this.restaurantsList.get(1).setAvailableNow(false);
        this.restaurantsList.get(1).setAvailabilityTime(30 * 60);
        this.restaurantsList.get(2).setAvailableNow(true);
        this.restaurantsList.get(3).setAvailableNow(false);
        this.restaurantsList.get(3).setAvailabilityTime(90 * 60);
        this.restaurantsList.get(4).setAvailableNow(false);
        this.restaurantsList.get(4).setAvailabilityTime(60 * 60);
        this.restaurantsList.get(5).setAvailableNow(true);

    }

    private RestaurantsLookupDb() {
        populateRestaurantsData();
        populateDealsData();
    }

    /**
    * Create a static method to get instance.
    */
    public static RestaurantsLookupDb getInstance(){
        if(restaurantsLookupDbObject == null){
            restaurantsLookupDbObject = new RestaurantsLookupDb();
        }
        return restaurantsLookupDbObject;
    }

    public ArrayList<Restaurant> getRestaurantsList() {
        return this.restaurantsList;
    }

    public HashMap<String, ArrayList<Deal>> getDealsPostedByRestaurants() {
        return this.dealsPostedByRestaurants;
    }

    //Lookup using a restuarant name and get the latest deals by that restaurant
    public Restaurant getRestaurantDealsByName(String restaurantName) {

        ArrayList<Restaurant> restaurantsList = getRestaurantsList();
        HashMap<String, ArrayList<Deal>> dealsPostedByRestaurants = getDealsPostedByRestaurants();
        for(Restaurant res : restaurantsList) {
            if(res.getResturantName().equalsIgnoreCase(restaurantName) && dealsPostedByRestaurants.containsKey(res.getResturantName())) {
                return res;
            }
        }

        return null;
    }

    public ArrayList<Order> getPastOrders() {
        return ordersList;
    }

    public void setPastOrders(ArrayList<Order> pastOrders) {
        this.ordersList = pastOrders;
    }

    public void addOrder(Order newOrder) {
        if(ordersList == null) {
            ordersList = new ArrayList<Order>();
        }

        ordersList.add(newOrder);
    }
}
