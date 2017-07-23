# FirebaseUI Addons - Some more UI bindings for Firebase
[![Download on Bintray](https://api.bintray.com/packages/craftco/libaries/firebaseui-android-addons/images/download.svg?version=1.0.0)](https://bintray.com/craftco/libaries/firebaseui-android-addons/1.0.0/link)

FirebaseUI-Android-Addons is a set of non-essential addons to Firebase's open source library for Android, [FirebaseUI-Android](https://github.com/firebase/FirebaseUI-Android).
FirebaseUI Addons allows you to back UI views such as Spinners to the [Firebase Realtime Database](https://firebase.google.com/docs/database/).

As for right now, there is no iOS equivalent. If you have any desire to create one, 
please notify me so I can link to it.

## Usage
### FirebaseSpinnerAdapter
```java
public class MyActivity extends AppCompatActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    }
}
```

## Installation
```groovy
dependencies {
    // ...
    implementation 'com.craft.libraries:firebaseui-android-addons:1.0.0'
}
```
Note: using "compile" is deprecated in the latest version (3.0.0) of Gradle build plugin.

## Dependencies
FirebaseUI Addons has the following transitive dependency on the Firebase SDK:
```
firebaseui-android-addons
  |--- com.firebaseui:firebase-ui-database:2.1.0
    |--- com.google.firebase:firebase-database:11.0.1

```

## Sample App

There is a sample app in the `app/` directory that demonstrates the features of this library. 
Before loading the project in Android Studio, make sure to connect it to a Firebase project 
(preferably a test one) using Android Studio's tools or manually by following the directions on 
the Firebase website. After that, build it and run it on your Android device. 

