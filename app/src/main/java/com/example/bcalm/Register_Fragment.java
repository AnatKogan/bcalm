package com.example.bcalm;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class Register_Fragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public Register_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_, container, false);

        EditText etEmail = view.findViewById(R.id.editTextTextEmailAddress2);
        EditText etPassword = view.findViewById(R.id.editTextTextPassword2);
        EditText etFullName = view.findViewById(R.id.editTextFullName);
        EditText etPhone = view.findViewById(R.id.editTextPhone);
        EditText etId = view.findViewById(R.id.editTextTextID);
        EditText etAge = view.findViewById(R.id.editTextAge);
        Button btnRegister = view.findViewById(R.id.button_registerr);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String name = etFullName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String id = etId.getText().toString().trim();
            String age = etAge.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(email, password, name, phone, id, age, v);
        });

        return view;
    }

    private void registerUser(String email, String password, String name, String phone, String idNumber, String ageStr, View v) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        writeUserToDb(userId, email, phone, name, idNumber, ageStr, v);
                    } else {
                        Toast.makeText(getContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void writeUserToDb(String userId, String email, String phone, String name, String idNumber, String ageStr, View v) {
        Map<String, Object> user = new HashMap<>();
        user.put("fullName", name);
        user.put("email", email);
        user.put("phone", phone);
        user.put("id", idNumber);
        user.put("age", ageStr);

        mDatabase.child("users").child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_register_Fragment_to_home_Fragment);
                    } else {
                        Toast.makeText(getContext(), "Error saving data: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}