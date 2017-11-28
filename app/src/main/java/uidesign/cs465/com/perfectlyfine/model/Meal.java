package uidesign.cs465.com.perfectlyfine.model;

/**
 * Created by oberpete on 27.11.2017.
 */

public class Meal {
    private String mealName;
    private double price;
    private int portions;
    private String category;
    private String[] ingredients;

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public Meal(String mealName, double price, int portions, String category, String[] ingredients) {
        this.mealName = mealName;
        this.price = price;
        this.portions = portions;
        this.category = category;
        this.ingredients = ingredients;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
