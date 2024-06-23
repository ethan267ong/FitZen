package com.example.caloriecounter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> mealList;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public MealAdapter(List<Meal> mealList, FirebaseFirestore db, FirebaseAuth mAuth) {
        this.mealList = mealList;
        this.db = db;
        this.mAuth = mAuth;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.mealNameTextView.setText(meal.getName());
        holder.caloriesTextView.setText(String.valueOf(meal.getCalories()));
        holder.buttonDelete.setOnClickListener(v -> {
            String userId = mAuth.getCurrentUser().getUid();
            String mealId = meal.getId(); // Assuming Meal class has a method getId() to get the document ID

            db.collection("users").document(userId).collection("meals").document(mealId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        mealList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mealList.size());
                        Toast.makeText(v.getContext(), "Meal deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(v.getContext(), "Failed to delete meal", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void deleteItem(int position) {
        Meal meal = mealList.get(position);
        String userId = mAuth.getCurrentUser().getUid();
        String mealId = meal.getId(); // Now this method exists and works

        db.collection("users").document(userId).collection("meals").document(mealId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    mealList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mealList.size());
                    Toast.makeText(recyclerView.getContext(), "Meal deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(recyclerView.getContext(), "Failed to delete meal", Toast.LENGTH_SHORT).show();
                });
    }


    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealNameTextView, caloriesTextView;
        Button buttonDelete;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealNameTextView = itemView.findViewById(R.id.meal_name);
            caloriesTextView = itemView.findViewById(R.id.calories);
            buttonDelete = itemView.findViewById(R.id.button_delete_meal);
        }
    }
}

