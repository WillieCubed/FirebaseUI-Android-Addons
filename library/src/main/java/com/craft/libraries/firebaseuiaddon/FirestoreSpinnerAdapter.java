package com.craft.libraries.firebaseuiaddon;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.ClassSnapshotParser;
import com.firebase.ui.firestore.FirestoreArray;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

/**
 * This class is a generic way of backing a Spinner with a Firebase Firestore location. It handles
 * all of the child events at the given Firebase location. It marshals received data into the given
 * class type. <p> Subclasses are responsible for populating an instance of the corresponding view
 * with the data from an instance of modelClass by providing an implementation of {@code
 * populateView} and {@code populateDropdownView}, which will be given an instance of your spinner
 * item, your dropdown list item, and an instance your class that holds your data. Populate the
 * views and this class will handle updating the list as the data changes at the given Firebase
 * Database location.
 *
 * <blockquote><pre>
 * {@code
 *      public class MyActivity extends AppCompatActivity {
 *          {@literal @}Override
 *          public void onCreate(Bundle savedInstanceState) {
 *              Spinner spinner = (Spinner) findViewById(R.id.spinner);
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
 * @version 1.0.0
 * @since 2.0.0
 */
public abstract class FirestoreSpinnerAdapter<T> extends BaseAdapter implements
        ChangeEventListener {

    @LayoutRes
    private int mDropdownLayout;

    @LayoutRes
    private int mLayout;

    private ObservableSnapshotArray<T> mSnapshots;

    /**
     * Creates a new FirebaseSpinnerAdapter with the given options and dropdown layout.
     *
     * @param query The location to monitor for data changes
     * @param layout The layout to inflate for the normally displayed view
     * @param dropdownLayout The layout to inflate for the spinner dropdown menu
     */
    @SuppressWarnings("WeakerAccess")
    public FirestoreSpinnerAdapter(@NonNull Query query, Class<T> modelClass, @LayoutRes int layout,
                                   @LayoutRes int dropdownLayout) {
        super();
        mSnapshots = new FirestoreArray<>(query, new ClassSnapshotParser<>(modelClass));
        mLayout = layout;
        mDropdownLayout = dropdownLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(mLayout, parent, false);
        }
        T model = getItem(position);
        populateView(convertView, model, position);
        return convertView;
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

    @Override
    public int getCount() {
        return mSnapshots.size();
    }

    @Override
    public T getItem(int index) {
        return mSnapshots.get(index);
    }

    @Override
    public void onChildChanged(ChangeEventType type, DocumentSnapshot snapshot, int newIndex,
                               int oldIndex) {
        // Leave open for subclasses
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startListening() {
        if (!mSnapshots.isListening(this)) {
            mSnapshots.addChangeEventListener(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopListening() {
        mSnapshots.removeChangeEventListener(this);
        notifyDataSetChanged();
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
    @SuppressWarnings("WeakerAccess")
    protected abstract void populateView(View view, T model, int position);

    /**
     * Each time the data at the given Firebase location changes, this method will be called for
     * each item that needs to be displayed. The first two arguments correspond to the {@code
     * mDropdownLayout} and {@code modelClass} given to the constructor of this class. The third
     * argument is the item's position in the list which can be used to obtain the item using {@link
     * #getItem(int)} <p> Subclass implementation should populate the dropdown view using the data
     * contained in the model.
     *
     * @param dropdownView The spinner's dropdown view to populate
     * @param model The object containing the data used to populate the view
     * @param position The position in the list of the view being populated
     */
    @SuppressWarnings("WeakerAccess")
    protected abstract void populateDropdownView(View dropdownView, T model, int position);
}
