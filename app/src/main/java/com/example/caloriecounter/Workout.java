package com.example.caloriecounter;

public class Workout {
    private String name;
    private String url;

    public Workout(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name; // This is important for the ArrayAdapter to display the name
    }
}

