package com.gis.map.utils;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by x0158990 on 17.08.15.
 */
public class GoogleMapHelper {

    private GoogleMapHelper() {
    }

    public static GoogleMap getMap(FragmentActivity activity, int id) {
        FragmentManager fm = activity.getSupportFragmentManager();
        SupportMapFragment f = (SupportMapFragment) fm.findFragmentById(id);
        return f.getMap();
    }
}
