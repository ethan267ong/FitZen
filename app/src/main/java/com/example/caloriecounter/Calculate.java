package com.example.caloriecounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calculate extends AppCompatActivity {
    // Declare your UI elements and other necessary variables
    EditText heightEditText, weightEditText, ageEditText;
    RadioGroup genderRadioGroup;
    Button calculateButton;
    TextView maintenanceCaloriesTextView;
    Spinner lifestyleSpinner, goalSpinner, durationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        heightEditText = findViewById(R.id.height_edit_text);
        weightEditText = findViewById(R.id.weight_edit_text);
        ageEditText = findViewById(R.id.age_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        calculateButton = findViewById(R.id.calculate_button);
        maintenanceCaloriesTextView = findViewById(R.id.maintenance_calories_text_view);
        goalSpinner = findViewById(R.id.goal_spinner);
        lifestyleSpinner = findViewById(R.id.lifestyle_spinner);
        durationSpinner = findViewById(R.id.duration_spinner);
        Button backButton = findViewById(R.id.back_button);

        ArrayAdapter<CharSequence> lifestyleAdapter = ArrayAdapter.createFromResource(this,
                R.array.lifestyle_options, android.R.layout.simple_spinner_item);
        lifestyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifestyleSpinner.setAdapter(lifestyleAdapter);

        ArrayAdapter<CharSequence> goaladapter = ArrayAdapter.createFromResource(this,
                R.array.goal_options, android.R.layout.simple_spinner_item);
        goaladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSpinner.setAdapter(goaladapter);

        ArrayAdapter<CharSequence> durationadapter = ArrayAdapter.createFromResource(this,
                R.array.duration_options, android.R.layout.simple_spinner_item);
        durationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(durationadapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(Calculate.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMaintenanceCalories();
            }
        });
    }

    private void calculateMaintenanceCalories() {
        // Retrieve user input
        double height = Double.parseDouble(heightEditText.getText().toString());
        double weight = Double.parseDouble(weightEditText.getText().toString());
        double age = Double.parseDouble(ageEditText.getText().toString());
        RadioButton selectedGenderRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());
        String gender = selectedGenderRadioButton.getText().toString();
        String lifestyle = lifestyleSpinner.getSelectedItem().toString();
        String goal = goalSpinner.getSelectedItem().toString();
        String duration = durationSpinner.getSelectedItem().toString();

        // Validate input
        if (height <= 0 || weight <= 0) {
            Toast.makeText(this, "Please enter valid height and weight.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate Basal Metabolic Rate (BMR)
        double bmr;
        if (gender.equals("Male")) {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5; // For males, the formula is slightly different
        } else {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161; // For females
        }

        // Adjust BMR based on lifestyle
        double maintenanceCalories;
        switch (lifestyle) {
            case "Sedentary":
                maintenanceCalories = bmr * 1.2;
                break;
            case "Lightly Active":
                maintenanceCalories = bmr * 1.375;
                break;
            case "Moderately Active":
                maintenanceCalories = bmr * 1.55;
                break;
            case "Very Active":
                maintenanceCalories = bmr * 1.725;
                break;
            case "Extremely Active":
                maintenanceCalories = bmr * 1.9;
                break;
            default:
                maintenanceCalories = bmr; // Default to sedentary
                break;
        }

        double calorieAdjustment;
        switch (goal) {
            case "Gain 0.5kg":
                calorieAdjustment = 500; // Assuming 0.5kg weight gain per week needs an additional 500 calories per day
                break;
            case "Lose 0.5kg":
                calorieAdjustment = -500; // Assuming 0.5kg weight loss per week needs a deficit of 500 calories per day
                break;
            case "Gain 1kg":
                calorieAdjustment = 1000; // Assuming 1kg weight gain per week needs an additional 1000 calories per day
                break;
            case "Lose 1kg":
                calorieAdjustment = -1000; // Assuming 1kg weight loss per week needs a deficit of 1000 calories per day
                break;
            case "Maintain Weight":
                calorieAdjustment = 0; // No adjustment needed for maintaining weight
                break;
            default:
                calorieAdjustment = 0;
                break;
        }

        int weeks = Integer.parseInt(duration.split(" ")[0]); // Extract number of weeks from the spinner text
        double dailyAdjustment = (calorieAdjustment / 7) / (weeks); // Calculate daily adjustment for the selected duration

        double finalCalories = maintenanceCalories + dailyAdjustment;

        maintenanceCaloriesTextView.setText(String.format("%.2f", finalCalories));
    }
}

