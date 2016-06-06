package com.holler.hollerapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import Utilities.GPSTracker;


public class LocationActivity extends AppCompatActivity {

    private GoogleMap map;
    GPSTracker gps;
    private Button currentLocationButton;
    private Button continueButton;

    private Toolbar locationToolBar;

    double latitudeFinal;
    double longitudeFinal;

    private android.widget.SearchView placesSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationToolBar = (Toolbar) findViewById(R.id.tool_bar_location); // Attaching the layout to the toolbar object
        setSupportActionBar(locationToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        gps = new GPSTracker(LocationActivity.this);
        placesSearchView = (android.widget.SearchView) findViewById(R.id.placesSearchView);

        getCurrentLocation();

        currentLocationButton = (Button)findViewById(R.id.currentLocationButton);
        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });

        continueButton = (Button)findViewById(R.id.button_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("lat",latitudeFinal);
                intent.putExtra("lat",longitudeFinal);
                setResult(200,intent);

                finish();
            }
        });

        placesSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();
                placeMarkerAtAddress(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void  getCurrentLocation()
    {
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            this.latitudeFinal = gps.getLatitude();
            this.longitudeFinal = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
            map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                    .title("Pune"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void placeMarkerAtAddress(String address)
    {
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for(Address add : adresses){
                //if (statement) {//Controls to ensure it is right address such as country etc.
                double longitude = add.getLongitude();
                double latitude = add.getLatitude();

                this.latitudeFinal = add.getLatitude();
                this.longitudeFinal = add.getLongitude();

                map.clear();
                map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                        .title(address));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                //}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
