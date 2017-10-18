package com.craft.libraries.firebaseuiaddon;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * This class is a generic way of backing a Spinner with a Firebase location. It handles all of the
 * child events at the given Firebase location. It marshals received data into the given class type.
 * <p> Subclasses are responsible for populating an instance of the corresponding view with the data
 * from an instance of modelClass by providing an implementation of {@code populateView} and
 * {@code populateDropdownView}, which will be given an instance of your spinner item, your
 * dropdown list item, and an instance your class that holds your data. Populate the views
 * and this class will handle updating the list as the data changes at the given Firebase Database
 * location.
 *
 * <blockquote><pre>
 * {@code
 *      public class MyActivity extends AppCompatActivity {
 *          @Override
 *          public void onCreate(Bundle savedInstanceState) {
 *              // Create a Spinner in your XML file and reference it in your Activity code
 *              Spinner spinner = (Spinner) findViewById(R.id.spinner);
 *
 *              // Create a DatabaseReference to the data you wish to populate your Spinner with
 *              DatabaseReference colorReference = FirebaseDatabase.getInstance().getReference();
 *              spinner.setAdapter(new FirebaseSpinnerAdapter<FavoriteColor>(this,
 *                      FavoriteColor.class,android.R.layout.simple_spinner_item,
 *                      android.R.layout.simple_spinner_dropdown_item, colorReference) {
 *
 *                  // Override populateView() to modify the default (unselected) Spinner view
 *                  // displayed with the data from your model
 *                  @Override
 *                  protected void populateView(View view, FavoriteColor color, int position) {
 *                      TextView color = view.findViewById(android.R.id.text1);
 *                      color.setText(color.getName());
 *                  }
 *
 *                  // Override populateDropdownView() to modify the Spinner's dropdown view
 *                  // displayed with the data from your model
 *                  @Override
 *                  protected void populateDropdownView(View dropdownView, FavoriteColor color, int
 *                          position) {
 *                      TextView colorDropdown = dropdownView.findViewById(android.R.id.text1);
 *                      colorDropdown.setText(color.getName());
 *                  }
 *              });
 *          }
 *      }
 * }
 * </pre></blockquote>
 *
 * @param <T> The class type to use as a model for the data contained in the children of the given
 * Firebase location
 */
public abstract class FirebaseSpinnerAdapter<T> extends FirebaseListAdapter<T> implements
        SpinnerAdapter {

    private static final String TAG = "FirebaseSpinnerAdapter";

    private int mDropDownLayout;

    /**
     * @param activity A context used to inflate the Spinner views
     * @param modelClass A class which Firebase will marshall the data into
     * @param layout A layout used to represent a single list item.
     * @param dropdownLayout A layout used to represent a single list item when the spinner is in
     * the selection state (dropdown/dialog)
     * @param query The Firebase location to watch for data changes. Can also be a slice of a
     * location, using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()},
     */
    public FirebaseSpinnerAdapter(Activity activity, Class<T> modelClass, int layout,
            int dropdownLayout, Query query) {
        super(activity, modelClass, layout, query);
        mDropDownLayout = dropdownLayout;
    }

    /**
     * @param activity A context used to inflate the Spinner views
     * @param modelClass A class which Firebase will marshall the data into
     * @param layout A layout used to represent a single list item.
     * @param dropdownLayout A layout used to represent a single list item when the spinner is in
     * the selection state (dropdown/dialog)
     * @param reference The Firebase location to watch for data changes. Can also be a slice of a
     * location, using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()},
     */
    public FirebaseSpinnerAdapter(Activity activity, Class<T> modelClass, int layout,
            int dropdownLayout, DatabaseReference reference) {
        super(activity, modelClass, layout, reference);
        mDropDownLayout = dropdownLayout;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, databaseError.toException());
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mDropDownLayout, parent, false);
        }
        T model = getItem(position);
        populateDropdownView(convertView, model, position);
        return convertView;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for
     * each item that needs to be displayed. The first two arguments correspond to the {@code
     * dropdownLayout} and {@code modelClass} given to the constructor of this class. The third
     * argument is the item's position in the list which can be used to obtain the item using {@link
     * #getItem(int)} <p> Subclass implementation should populate the dropdown view using the data
     * contained in the model.
     *
     * @param dropdownView The spinner's dropdown view to populate
     * @param model The object containing the data used to populate the view
     * @param position The position in the list of the view being populated
     */
    abstract protected void populateDropdownView(View dropdownView, T model, int position);
}
