package com.example.caloriecounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddMealActivity extends AppCompatActivity {
    private EditText mealNameEditText, caloriesEditText;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mealNameEditText = findViewById(R.id.meal_name);
        caloriesEditText = findViewById(R.id.calories);
        Button saveButton = findViewById(R.id.save_button);
        Button backButton = findViewById(R.id.back_button);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        saveButton.setOnClickListener(view -> {
            String mealName = mealNameEditText.getText().toString();
            String caloriesString = caloriesEditText.getText().toString();

            // Check for empty input in mealNameEditText and caloriesEditText
            if (mealName.isEmpty() || caloriesString.isEmpty()) {
                Toast.makeText(AddMealActivity.this, "Invalid input.", Toast.LENGTH_SHORT).show();
            } else {
                int calories = Integer.parseInt(caloriesString);
                saveMeal(mealName, calories);
            }
        });


        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(AddMealActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void saveMeal(String name, int calories) {
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        Meal meal = new Meal(name, calories, getCurrentDate());

        db.collection("users").document(userId).collection("meals")
                .add(meal)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddMealActivity.this, "Meal added.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AddMealActivity.this, "Error adding meal.", Toast.LENGTH_SHORT).show());
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

}

