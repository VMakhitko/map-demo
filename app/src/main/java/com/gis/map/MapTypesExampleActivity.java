package com.gis.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.gis.map.utils.GoogleMapHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasper on 8/20/15.
 */
public class MapTypesExampleActivity extends FragmentActivity {
    private GoogleMap satteliteMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_type_example);

        final List<GoogleMap> maps = new ArrayList<GoogleMap>();
        final GoogleMap.OnCameraChangeListener cameraChangeListener = new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                CameraUpdate cameraUpdate = new CameraUpdateFactory.newCameraPosition(cameraPosition);
                for (GoogleMap map : maps) {
                    map.animateCamera(cameraUpdate, 200, null);
                }
            }
        };

        OnTouchListener overlayTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for (GoogleMap map : maps) {
                    map.setOnCameraChangeListener(null);
                }
                GoogleMap touchMap = (GoogleMap) v.getTag();
                touchMap.setOnCameraChangeListener(cameraChangeListener);
                return false;
            }
        };

        int[] mapIds = {R.id.normal_map, R.id.terrain_map, R.id.sattelite_map};
        int[] overlayIds = {R.id.normal_overlay, R.id.terrain_overlay, R.id.sattelite_overlay};

        for (int i = 0; i < mapIds.length; i++) {
            GoogleMap map = GoogleMapHelper.getMap(this, mapIds[i]);
            maps.add(map);

            if (mapIds[i] == R.id.sattelite_map) {
                satteliteMap = map;
            }

            View overlay = findViewById(overlayIds[i]);
            overlay.setTag(map);
            overlay.setOnTouchListener(overlayTouchListener);
        }
    }

    public void onSatelliteHybridSwitchClick(View view) {
        if (satteliteMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
            satteliteMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else {
            satteliteMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }

}
