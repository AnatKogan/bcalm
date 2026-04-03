package com.example.bcalm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class BabyProfileFragment extends Fragment {

    private EditText etBabyName, etWeight;
    private Spinner spinnerCountry;
    private Button btnSaveProfile;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baby_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etBabyName = view.findViewById(R.id.etBabyName);
        etWeight = view.findViewById(R.id.etWeight);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);

        // הגדרת רשימת מדינות פשוטה
        String[] countries = {"Israel", "USA", "UK", "Germany", "France", "Canada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        btnSaveProfile.setOnClickListener(v -> saveBabyProfile());

        return view;
    }

    private void saveBabyProfile() {
        String name = etBabyName.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();
        String country = spinnerCountry.getSelectedItem().toString();
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "anonymous";

        if (name.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);

        Map<String, Object> babyData = new HashMap<>();
        babyData.put("fullName", name); // שימוש ב-fullName לפי הדרישה שלך
        babyData.put("weight", weight);
        babyData.put("country", country);
        babyData.put("lastUpdated", System.currentTimeMillis());

        mDatabase.child("babies").child(userId).setValue(babyData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    // חזרה למסך הקודם
                    if (getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}