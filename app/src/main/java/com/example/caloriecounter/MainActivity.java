package com.example.caloriecounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    Button logout_button, addMealButton, viewLogsButton, calculateButton;

    TextView user_details, totalCaloriesTextView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        logout_button = findViewById(R.id.logout);
        user_details = findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        addMealButton = findViewById(R.id.add_meal_button);
        viewLogsButton = findViewById(R.id.view_logs_button);
        calculateButton = findViewById(R.id.find_ideal_calories_button);
        totalCaloriesTextView = findViewById(R.id.total_calories);

        //double check
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            user_details.setText(user.getEmail());
            loadTotalCalories();
        }

        addMealButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMealActivity.class);
            startActivityForResult(intent, 1);  // 1 is the request code
        });
        viewLogsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LogsActivity.class)));
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculate.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //error for empty calorie input
    private void loadTotalCalories() {
        String userId = user.getUid();
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        db.collection("users").document(userId).collection("meals")
                .whereEqualTo("date", today)
                .get()
                .addOnCompleteListener(task -> {
                    int totalCalories = 0;
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Meal meal = document.toObject(Meal.class);
                            totalCalories += meal.getCalories();
                        }
                        totalCaloriesTextView.setText(String.valueOf(totalCalories));
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to load calories.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadTotalCalories();  // Refresh total calories
        }
    }
}