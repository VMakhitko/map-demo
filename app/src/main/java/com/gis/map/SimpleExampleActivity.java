package com.gis.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.gis.map.utils.GoogleMapHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

/**
 * Created by x0158990 on 17.08.15.
 */
public class SimpleExampleActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_example);

        GoogleMap map = GoogleMapHelper.getMap(this, R.id.simple_map);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                handleOnClick("Click", latLng);
            }
        });

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                handleOnClick("Long Click", latLng);
            }
        });
    }

    private void handleOnClick(String id, LatLng latLng) {
        String text = String.format(Locale.US, "%s: %.2f, %.2f", id, latLng.latitude, latLng.longitude);
        Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show();
    }
}
