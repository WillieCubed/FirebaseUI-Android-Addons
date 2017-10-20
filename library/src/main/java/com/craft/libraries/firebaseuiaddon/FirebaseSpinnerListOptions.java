package com.craft.libraries.firebaseuiaddon;

import android.arch.lifecycle.LifecycleOwner;

import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.ObservableSnapshotArray;

/**
 * @version 1.0.0
 * @since v1.0.0 (10/19/17)
 */

public class FirebaseSpinnerListOptions extends FirebaseListOptions {
    private FirebaseSpinnerListOptions(ObservableSnapshotArray snapshots, int layout, LifecycleOwner owner) {
        super(snapshots, layout, owner);
    }
}
