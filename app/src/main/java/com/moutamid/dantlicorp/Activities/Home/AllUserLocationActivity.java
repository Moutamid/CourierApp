package com.moutamid.dantlicorp.Activities.Home;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moutamid.dantlicorp.Model.ChecksModel;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;

public class AllUserLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<LatLng> locationArrayList;
    ArrayList<ChecksModel> locationModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationModels = Stash.getArrayList("AllUserLocation", ChecksModel.class);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();

        // on below line we are adding our

        // locations in our array list.

        for (int i = 0; i < locationModels.size(); i++) {
            LatLng sydney = new LatLng(locationModels.get(i).lat, locationModels.get(i).lng);
            if (locationModels.get(i).lat > -90 && locationModels.get(i).lat < 90 && locationModels.get(i).lng > -180 && locationModels.get(i).lng < 180) {
                locationArrayList.add(sydney);

            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int height = 50;
        int width = 50;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.human);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        for (int i = 0; i < locationArrayList.size(); i++) {

            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(locationModels.get(i).name).icon(BitmapDescriptorFactory.fromBitmap(smallMarker))).showInfoWindow();

            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationArrayList.get(i), 12.0f));
//            float zoomLevel = 16.0f; //This goes up to 21
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }
}
