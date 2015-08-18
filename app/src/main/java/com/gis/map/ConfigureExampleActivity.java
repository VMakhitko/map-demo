package com.gis.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gis.map.utils.GoogleMapHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by x0158990 on 17.08.15.
 */
public class ConfigureExampleActivity extends FragmentActivity {
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration_example);

        GoogleMap map = GoogleMapHelper.getMap(this, R.id.configured_map);
        final UiSettings settings = map.getUiSettings();

        map.setMyLocationEnabled(true);
        settings.setMyLocationButtonEnabled(false);

        infoTextView = (TextView) findViewById(R.id.info_textview);
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                updatePisition(cameraPosition);
            }
        });

        updatePisition(map.getCameraPosition());

        CheckBox controlCheckBox = (CheckBox) findViewById(R.id.controls_checkbox);
        controlCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setCompassEnabled(isChecked);
                settings.setMyLocationButtonEnabled(isChecked);
                settings.setZoomControlsEnabled(isChecked);
            }
        });

        CheckBox gesturesCheckBox = (CheckBox) findViewById(R.id.gestures_checkbox);
        gesturesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setAllGesturesEnabled(isChecked);
            }
        });
    }

    private void updatePisition(CameraPosition position) {
        String format = "latitude: %.2f longitude: %.2f zoom: %.2f bearing: %.2f tilt: %.2f";
        LatLng t = position.target;
        String text = String.format(format, t.latitude, t.longitude, position.zoom, position.bearing, position.tilt);
        infoTextView.setText(text);
    }

}
