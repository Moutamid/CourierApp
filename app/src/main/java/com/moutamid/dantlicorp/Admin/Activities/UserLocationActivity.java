package com.moutamid.dantlicorp.Admin.Activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;

public class UserLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // below are the latitude and longitude
    // of 4 different locations.
    double lat, lng;
    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;
    String name, box;
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
        locationArrayList = new ArrayList<>();

        // on below line we are adding our

        // locations in our array list.
        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lng = Double.parseDouble(getIntent().getStringExtra("lng"));
        name = getIntent().getStringExtra("name");

        if (lat != 0.0) {
            LatLng sydney = new LatLng(lat, lng);
            locationArrayList.add(sydney);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int height = 50;
        int width = 50;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.record);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        for (int i = 0; i < locationArrayList.size(); i++) {
            if (lat != 0.0) {
                mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(name ).icon(BitmapDescriptorFactory.fromBitmap(smallMarker))).showInfoWindow();
            }
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationArrayList.get(i), 12.0f));
//            float zoomLevel = 16.0f; //This goes up to 21
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }
}
