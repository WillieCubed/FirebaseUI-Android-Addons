package com.craft.libraries.firebaseuiaddon;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;

/**
 * This class is a generic way of backing a Spinner with a Firebase location. It handles all of the
 * child events at the given Firebase location. It marshals received data into the given class type.
 * <p> Subclasses are responsible for populating an instance of the corresponding view with the data
 * from an instance of modelClass by providing an implementation of {@code populateView} and {@code
 * populateDropdownView}, which will be given an instance of your spinner item, your dropdown list
 * item, and an instance your class that holds your data. Populate the views and this class will
 * handle updating the list as the data changes at the given Firebase Database location.
 *
 * <blockquote><pre>
 * {@code
 *      public class MyActivity extends AppCompatActivity {
 *          {@literal @}Override
 *          public void onCreate(Bundle savedInstanceState) {
 *              Spinner spinner = findViewById(R.id.spinner);
 *              DatabaseReference colorReference = FirebaseDatabase.getInstance().getReference()
 *                      .child("colors");
 *              FirebaseListOptions options = new FirebaseListOptions.Builder()
 *                      .setQuery(colorReference)
 *                      .setLayout(android.R.layout.simple_spinner_item)
 *                      .build();
 *              spinner.setAdapter(new FirebaseSpinnerAdapter<FavoriteColor>(options,
 *                      android.R.layout.simple_spinner_dropdown_item) {
 *                 {@literal @}Override
 * `                protected void populateView(View view, FavoriteColor color, int position) {
 *                      ((TextView) view.findViewById(android.R.id.text1)).setText(color.getName());
 *                  }
 *
 *                {@literal @}Override
 *                  protected void populateDropdownView(View dropdownView, FavoriteColor color, int
 *                                                      position) {
 *                      ((TextView) dropdownView.findViewById(android.R.id.text1)).setText(color.getName());
 *                 }
 *             });
 *         }
 *     }
 * }
 * </pre></blockquote>
 *
 * @param <T> The class type to use as a model for the data contained in the children of the given
 * Firebase location
 * @version 2.0.0
 * @since 1.0.0
 */
public abstract class FirebaseSpinnerAdapter<T> extends FirebaseListAdapter<T> implements
        SpinnerAdapter {

    @LayoutRes
    private int mDropdownLayout;

    /**
     * Creates a new FirebaseSpinnerAdapter with the given options and dropdown layout.
     *
     * @param dropdownLayout The layout to inflate for the spinner dropdown menu
     */
    @SuppressWarnings("WeakerAccess")
    public FirebaseSpinnerAdapter(FirebaseListOptions<T> options, @LayoutRes int dropdownLayout) {
        super(options);
        mDropdownLayout = dropdownLayout;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(mDropdownLayout, parent, false);
        }
        T model = getItem(position);
        populateDropdownView(convertView, model, position);
        return convertView;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for
     * each item that needs to be displayed. The first two arguments correspond to the layout and
     * model class given to the {@link FirebaseListOptions} passed into the constructor. The third
     * argument is the item's position in the list.
     * <p>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param view The view to populate
     * @param model The object containing the data used to populate the view
     * @param position The position in the list of the view being populated
     */
    @Override
    protected abstract void populateView(View view, T model, int position);

    /**
     * Each time the data at the given Firebase location changes, this method will be called for
     * each item that needs to be displayed. The first two arguments correspond to the layout and
     * model class given to the {@link FirebaseListOptions} passed into the constructor. The third
     * argument is the item's position in the list.
     * <p>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param dropdownView The spinner's dropdown view to populate
     * @param model The object containing the data used to populate the view
     * @param position The position in the list of the view being populated
     */
    @SuppressWarnings("WeakerAccess")
    protected abstract void populateDropdownView(View dropdownView, T model, int position);
}
