package com.moutamid.dantlicorp.Activities.Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // below are the latitude and longitude
    // of 4 different locations.
    double lat, lng;
    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;
    String name, box;
    ArrayList<ChecksModel> locationModels;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);
        // in below line we are initializing our array list.
        locationModels = Stash.getArrayList("CheckIn", ChecksModel.class);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();

        // on below line we are adding our

        // locations in our array list.
        lat = getIntent().getDoubleExtra("lat", 0.0);
        lng = getIntent().getDoubleExtra("lng", 0.0);
        name = getIntent().getStringExtra("name");
        box = getIntent().getStringExtra("box");
        if (lat != 0.0) {

            LatLng sydney = new LatLng(lat, lng);
            locationArrayList.add(sydney);
        } else {
            for (int i = 0; i < locationModels.size(); i++) {
                LatLng sydney = new LatLng(locationModels.get(i).lat, locationModels.get(i).lng);
                if (locationModels.get(i).lat > -90 && locationModels.get(i).lat < 90 && locationModels.get(i).lng > -180 && locationModels.get(i).lng < 180) {
                    locationArrayList.add(sydney);

                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       this.mMap = googleMap;
        int height = 50;
        int width = 50;

        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.human);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        for (int i = 0; i < locationArrayList.size(); i++) {
            if (lat != 0.0) {
                this. mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(name+" ("+ box+" boxes)").icon(BitmapDescriptorFactory.fromBitmap(smallMarker))).showInfoWindow();
            } else {
                this.mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(locationModels.get(i).name+" ("+ locationModels.get(i).box+" boxes)").icon(BitmapDescriptorFactory.fromBitmap(smallMarker))).showInfoWindow();
            }
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
            this. mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationArrayList.get(i), 12.0f));
//            float zoomLevel = 16.0f; //This goes up to 21
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }
}
