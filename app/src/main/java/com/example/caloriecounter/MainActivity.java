package com.example.caloriecounter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    ImageView addMealButton, viewLogsButton, calculateButton, workOutButton;

    Button logout_button;

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

        addMealButton = findViewById(R.id.addMeal);
        viewLogsButton = findViewById(R.id.viewLog);
        calculateButton = findViewById(R.id.calculateCalories);
        workOutButton = findViewById(R.id.exerciseProgs);
        totalCaloriesTextView = findViewById(R.id.total_calories);

        // for notification
        NotificationUtils.createNotificationChannel(this);
        requestNotificationPermission();


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

        workOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Workouts.class);
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

    private void scheduleMealReminders() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // 9 AM
        scheduleAlarm(alarmManager, 9, 0, 0);
        // 1 PM
        scheduleAlarm(alarmManager, 13, 0, 0);
        // 7 PM
        scheduleAlarm(alarmManager, 21, 2, 0);
    }

    private void scheduleAlarm(AlarmManager alarmManager, int hour, int minute, int second) {
        Intent intent = new Intent(this, MealReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, hour, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1001;

    private void requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
        } else {
            // Permission already granted, schedule the reminders
            scheduleMealReminders();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                scheduleMealReminders();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission required to send notifications", Toast.LENGTH_SHORT).show();
            }
        }
    }

}