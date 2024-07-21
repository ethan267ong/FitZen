package com.example.caloriecounter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Workouts extends AppCompatActivity {

    Spinner workoutSpinner;
    List<Workout> workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout);

        Button backButton = findViewById(R.id.back_button);
        workoutSpinner = findViewById(R.id.workout_spinner);

        workoutList = new ArrayList<>();
        workoutList.add(new Workout("Options", ""));
        workoutList.add(new Workout("Muscle Building", "https://docs.google.com/spreadsheets/d/1gALyd6N8JYz9eCKEKBTdxwh5AYiparoNGPxfv2_weUU/edit?usp=sharing"));
        workoutList.add(new Workout("Weight Loss", "https://docs.google.com/spreadsheets/d/1uIIk8XLPmvQoZ-kkshdRmo8U0nBOR9EWFn88WOOHYs4/edit?usp=sharing"));
        workoutList.add(new Workout("Physique Improvement", "https://docs.google.com/spreadsheets/d/1bCNLBD1RYQUHaGmkuEuuQlkQ4SjXIvfcOoTVmyj3IAg/edit?usp=sharing"));

        ArrayAdapter<Workout> workoutAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, workoutList);
        workoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutSpinner.setAdapter(workoutAdapter);

        workoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected workout
                Workout selectedWorkout = (Workout) parent.getItemAtPosition(position);
                // Open the URL in a browser
                if (position != 0) { // Skip the first placeholder item
                    // Open the URL in a browser
                    String url = selectedWorkout.getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(Workouts.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
