package com.example.caloriecounter;
public class Meal {
    private String id;
    private String name;
    private int calories;
    private String date;

    public Meal() {
        // Default constructor required for calls to DataSnapshot.getValue(Meal.class)
    }

    public Meal(String name, int calories, String date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

