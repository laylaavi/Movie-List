package com.example.mobi6006_la26_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutUsActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    Double LAT, LNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        LAT = -6.194805;
        LNG = 106.819554;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_CafeMaps);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng defaultLocation = new LatLng(LAT, LNG);

        map.addMarker(new MarkerOptions().position(defaultLocation).title("Movie Time Office"));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 16f));
    }
}