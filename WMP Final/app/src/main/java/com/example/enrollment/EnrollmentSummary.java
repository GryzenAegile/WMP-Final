package com.example.enrollment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentSummary extends AppCompatActivity {

    private LinearLayout subjectListLayout;
    private TextView totalCreditsTextView;
    private Button dropButton;
    private FirebaseFirestore db; // Firestore instance
    private String enrollmentDocumentId; // Document ID for the enrollment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_summary); // Set the content view

        subjectListLayout = findViewById(R.id.subjectListLayout);
        totalCreditsTextView = findViewById(R.id.totalCreditsTextView);
        dropButton = findViewById(R.id.dropButton);
        db = FirebaseFirestore.getInstance(); // Initialize Firestore instance

        // Get the data passed from Enrollment activity
        Intent intent = getIntent();
        Subject[] selectedSubjectsArray = (Subject[]) intent.getSerializableExtra("selectedSubjects");
        int totalCredits = intent.getIntExtra("totalCredits", 0);
        enrollmentDocumentId = intent.getStringExtra("enrollmentDocumentId"); // Get the document ID

        // Check if selectedSubjectsArray is not null
        if (selectedSubjectsArray != null) {
            List<Subject> selectedSubjects = new ArrayList<>(List.of(selectedSubjectsArray));
            // Display the subjects and total credits
            for (Subject subject : selectedSubjects) {
                TextView subjectTextView = new TextView(this);
                subjectTextView.setText(subject.getName() + " (" + subject.getCredits() + " credits)");
                subjectListLayout.addView(subjectTextView);
            }
            totalCreditsTextView.setText("Total Credits: " + totalCredits);
        } else {
            totalCreditsTextView.setText("Total Credits: 0");
        }

        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropEnrollment();
            }
        });
    }

    private void dropEnrollment() {
        if (enrollmentDocumentId != null) {
            DocumentReference enrollmentRef = db.collection("Enrollments").document(enrollmentDocumentId);
            enrollmentRef.delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EnrollmentSummary.this, "Enrollment dropped successfully.", Toast.LENGTH_SHORT).show();
                        // Redirect to Enrollment activity
                        Intent intent = new Intent(EnrollmentSummary.this, EnrollmentMain.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EnrollmentSummary.this, "Error dropping enrollment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No enrollment to drop.", Toast.LENGTH_SHORT).show();
        }
    }

}
