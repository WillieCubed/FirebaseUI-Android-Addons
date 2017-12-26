# FirebaseUI-Android Addons - Some more UI bindings for Firebase
[ ![Download](https://api.bintray.com/packages/craftco/libaries/firebaseui-android-addons/images/download.svg) ](https://bintray.com/craftco/libaries/firebaseui-android-addons/_latestVersion)

FirebaseUI-Android Addons is a set of non-essential addons to Firebase's open source library for Android, [FirebaseUI-Android](https://github.com/firebase/FirebaseUI-Android).
FirebaseUI Addons allows you to back UI views such as Spinners to the [Firebase Realtime Database](https://firebase.google.com/docs/database/).

As for right now, there is no iOS equivalent. If you have any desire to create one, 
please notify me so I can link to it.

## Installation
Add this dependency to your app's `build.gradle`:
```groovy
dependencies {
    // ...
    implementation 'com.craft.libraries:firebase-ui-addons:2.0.0'
}
```

### Dependencies
FirebaseUI-Android Addons uses these versions of the Android Support Libraries
and Firebase SDKs:

| Library Version   | FirebaseUI Version    | Firebase SDK version  | Support Library Version 	|
|-----------------	|--------------------	|----------------------	|-------------------------	|
| 2.0.0           	| 3.1.0              	| 11.8.0               	| 27.0.2                  	|
| 1.0.0           	| 2.1.0              	| 11.0.1               	| 26.0.0-beta1            	|


## Usage
### FirebaseSpinnerAdapter
```java
public class MyActivity extends AppCompatActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Create a Spinner in your XML file and reference it in your Activity code
        Spinner spinner = findViewById(R.id.spinner);
        
        // Create a DatabaseReference to the data you wish to populate your Spinner
        DatabaseReference colorReference = FirebaseDatabase.getInstance().getReference()
                .child("colors");
        // Create a FirebaseListOptions to pass the query 
        FirebaseListOptions options = new FirebaseListOptions.Builder()
                .setQuery(colorReference)
                .setLayout(android.R.layout.simple_spinner_item)
                .build();
        spinner.setAdapter(new FirebaseSpinnerAdapter<FavoriteColor>(options, 
                android.R.layout.simple_spinner_dropdown_item) {
                
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

## Sample App

There is a sample app in the `app/` directory that demonstrates the features of this library. 
Before loading the project in Android Studio, make sure to connect it to a Firebase project 
(preferably a test one) using Android Studio's tools or manually by following the directions on 
the Firebase website. After that, build it and run it on your Android device. 

## Contributing
### Deploying to Bintray
Prerequisite: make sure you're registered in the CraftCompany Bintray 
[organization](https://bintray.com/craftco).
1) Create a `bintray.properties` file at the root of the project.
2) Add two lines for your credentials in `bintray.properties`, replacing
   `yourusername` and `yourapikey` with your Bintray username and API key,
   respectively:
    ```properties
    user=yourusername
    key=yourapikey
    ```
3) Run `./gradlew install bintrayUpload` to deploy the library.