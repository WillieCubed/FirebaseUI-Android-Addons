# FirebaseUI Addons - Some more UI bindings for Firebase
FirebaseUI-Android-Addons is a set of non-essential addons to Firebase's open source library, [FirebaseUI](https://github.com/firebase/firebaseui-android). FirebaseUI Addons allows you to 
back UI views such as Spinners to the [Firebase Realtime Database](https://firebase.google.com/docs/database/).

As for right now, there is no iOS equivalent. If you have any desire to create one, 
please notify me so I can link to it.

## Usage
### FirebaseSpinnerAdapter
```java
// Create a Spinner in your XML file and reference it in your Activity code
Spinner spinner = (Spinner) findViewById(R.id.spinner);

// Create a DatabaseReference to the data you wish to populate your Spinner with
DatabaseReference colorReference = FirebaseDatabase.getInstance().getReference();
spinner.setAdapter(new FirebaseSpinnerAdapter<FavoriteColor>(this, FavoriteColor.class,
        android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item,
        colorReference) {
        
    // Override populateView() to modify the default (unselected) Spinner view displayed with 
    // the data from your model
    @Override
    protected void populateView(View view, FavoriteColor color, int position) {
       ((TextView) view.findViewById(android.R.id.text1)).setText(color.getName());
    }
    
    // Override populateDropdownView() to modify the Spinner's dropdown view displayed with 
    // the data from your model
    @Override
    protected void populateDropdownView(View dropdownView, FavoriteColor color, int position) {
        ((TextView) dropdownView.findViewById(android.R.id.text1)).setText(color.getName());
    }
});
```

## Installation
```groovy
dependencies {
    compile 'com.craft.libraries:firebaseui-android-addons:0.1.0'
}
```

## Dependencies
FirebaseUI Addons has the following transitive dependency on the Firebase SDK:
```
firebaseui-android-addons
  |--- com.google.firebase:firebase-ui-database
    |--- com.google.firebase:firebase-database

```
This essentially means you shouldn't have to include the dependencies for
FirebaesUI and Firebase Database separately in your `build.gradle` file

## Sample App

There is a sample app in the `app/` directory that demonstrates the features of 
FirebaseUI. Load the project in Android Studio and run it on your Android device 
to see a demonstration.

