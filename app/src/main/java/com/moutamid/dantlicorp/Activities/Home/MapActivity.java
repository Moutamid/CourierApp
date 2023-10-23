package com.moutamid.dantlicorp.Activities.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.moutamid.dantlicorp.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Set the map type to satellite view.
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Create a new CameraPosition object and set the zoom level to 1.
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .zoom(1.0f)
//                .build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
