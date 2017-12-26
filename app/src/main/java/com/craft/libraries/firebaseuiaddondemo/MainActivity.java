package com.craft.libraries.firebaseuiaddondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.craft.libraries.firebaseuiaddon.FirebaseSpinnerAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An {@link android.app.Activity} that demonstrates usage of this library.
 */
public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference colorReference = mDatabaseRoot.child("favoriteColors");

        setupTestData();

        FirebaseListOptions<FavoriteColor> options = new FirebaseListOptions.Builder<FavoriteColor>()
                .setLayout(android.R.layout.simple_spinner_item)
                .setQuery(colorReference, FavoriteColor.class)
                .build();
        FirebaseSpinnerAdapter adapter = new FirebaseSpinnerAdapter<FavoriteColor>(options,
                android.R.layout.simple_spinner_dropdown_item) {
            @Override
            protected void populateView(View view, FavoriteColor color, int position) {
                ((TextView) view.findViewById(android.R.id.text1)).setText(color.getColor());
            }

            @Override
            protected void populateDropdownView(View dropdownView, FavoriteColor color,
                                                int position) {
                ((TextView) dropdownView.findViewById(android.R.id.text1))
                        .setText(color.getLongName());
            }
        };
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }

    /**
     * Pushes some dummy items into the Firebase Realtime Database.
     */
    private void setupTestData() {
        String[] shortNames = new String[]{
                "Red, Blue, Green, Orange"
        };
        String[] longNames = new String[]{
                "Royal Red", "Breezy Blue", "Easy Evergreen", "Outrageous Orange"
        };
        for (int i = 0; i < shortNames.length; i++) {
            mDatabaseRoot.child("favoriteColors").push()
                    .setValue(new FavoriteColor(shortNames[i], longNames[i]));
        }
    }
}
