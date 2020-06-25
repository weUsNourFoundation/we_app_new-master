package com.example.we_us_n_our_app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.we_us_n_our_app.ui.makepayment.Transactions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String email=new String("");
    private String admin_mail=new String("weusnourfoundation@gmail.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database= FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef=database.getReference("Users").child(firebaseAuth.getCurrentUser().getUid());

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(admin_mail==user.getEmail()){
            setContentView(R.layout.activity_admin_menu);
            Toast.makeText(this, "In admin", Toast.LENGTH_LONG).show();

        }
        else{
            setContentView(R.layout.activity_menu);
            Toast.makeText(this, "In user", Toast.LENGTH_LONG).show();
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        //email=user.getEmail();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if(admin_mail==user.getEmail()){
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_transaction_history,
                    R.id.nav_about, R.id.nav_share)
                    .setDrawerLayout(drawer)
                    .build();

        }
        else {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_make_payment, R.id.nav_transaction_history,
                    R.id.nav_about, R.id.nav_share)
                    .setDrawerLayout(drawer)
                    .build();
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        database= FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef=database.getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
        myRef.child("location").setValue(getUserLocation());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name= dataSnapshot.child("name").getValue(String.class);
                TextView textView= (TextView) findViewById(R.id.nameTextView);
                textView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // get user location on start
        // copy this function where you want to get location
        String city = getUserLocation();
        if(city == null){
            Toast.makeText(this, "location not found", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, city, Toast.LENGTH_LONG).show();
        }
    }

    public String getUserLocation(){

        String city = null;
        double longitude, latitude;
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        assert locManager != null;
        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;


        if (network_enabled) {


            // user permission again for location
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);
                }
            }

            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location!=null){
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Toast.makeText(MenuActivity.this, "Location :" + longitude+" "+latitude, Toast.LENGTH_LONG).show();
                Log.i("sanchit","Location :" + longitude+" "+latitude);
                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {

                        city = addresses.get(0).getLocality();
                        Log.i("sanchit",city);
//                        Toast.makeText(MainActivity.this, city, Toast.LENGTH_LONG).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return city;
    }


}
