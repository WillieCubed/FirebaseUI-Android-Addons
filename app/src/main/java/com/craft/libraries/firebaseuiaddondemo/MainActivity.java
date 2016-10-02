package com.craft.libraries.firebaseuiaddondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.craft.libraries.firebaseuiaddon.FirebaseSpinnerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference colorReference = mDatabaseRoot.child("favoriteColors");

        setupTestData();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new FirebaseSpinnerAdapter<FavoriteColor>(this, FavoriteColor.class,
                android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item,
                colorReference) {
            @Override
            protected void populateView(View view, FavoriteColor color, int position) {
                ((TextView) view.findViewById(android.R.id.text1)).setText(color.getName());
            }

            @Override
            protected void populateDropdownView(View dropdownView, FavoriteColor color, int position) {
                ((TextView) dropdownView.findViewById(android.R.id.text1))
                        .setText(color.getLongName());
            }
        });
    }

    /**
     * Used to push some dummy items into the Firebase Realtime Database
     */
    private void setupTestData() {
        String[] shortNames = new String[]{
                "Red, Blue, Green, Orange"
        };
        String[] longNames = new String[]{"Royal Red", "Breezy Blue", "Easy Evergreen",
                "Outrageous Orange"
        };
        for (int i = 0; i < shortNames.length; i++) {
            mDatabaseRoot.child("favoriteColors").push()
                    .setValue(new FavoriteColor(shortNames[i], longNames[i]));
        }
    }
}
