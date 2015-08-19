package com.gis.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.gis.map.utils.GoogleMapHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by x0158990 on 18.08.15.
 */
public class MarkersExampleActivity extends FragmentActivity {
    private final String TAG = MarkersExampleActivity.class.getSimpleName();

    private Map<Marker, String> data = new HashMap<Marker, String>();
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceSaved) {
        super.onCreate(savedInstanceSaved);
        setContentView(R.layout.markers_example);

        infoTextView = (TextView) findViewById(R.id.info_textview);

        GoogleMap map = GoogleMapHelper.getMap(this, R.id.markers_map);

        setMarkerClickListeners(map);
        setMarkerDragListener(map);
        setInfoWindowClickListener(map);

        addDefaultMarker(map);
        addPredefinedMarkers(map);
        addColorfulMarkersCircle(map);
        addCustomMarkers(map);
    }

    private void setMarkerClickListeners(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                updateInfo("click", marker);
                boolean isRed = "red".equals(marker.getTitle());
                if (isRed) {
                    marker.showInfoWindow();
                    return true;
                }
                return false;
            }
        });
    }

    private void setMarkerDragListener(GoogleMap map) {
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                updateInfo("drag start", marker);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng p = marker.getPosition();
                updateInfo(String.format(Locale.US, "drag %.1f %.1f", p.latitude, p.longitude), marker);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                updateInfo("drag end", marker);
            }
        });
    }

    private void setInfoWindowClickListener(final GoogleMap map) {
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                updateInfo("info window click", marker);
                if (marker.getSnippet() == null) {
                    marker.hideInfoWindow();
                    for (Marker m : data.keySet()) {
                        m.setVisible(true);
                    }
                } else {
                    marker.setVisible(false);
                }
            }
        });
    }

    private void addDefaultMarker(GoogleMap map) {
        LatLng position = new LatLng(0.0, 0.0);
        String title = "red";
        MarkerOptions options = new MarkerOptions().position(position).title(title);

        Marker marker = map.addMarker(options);
        data.put(marker, "default (red)");
    }

    private void addPredefinedMarkers(GoogleMap map) {
        float[] hues = {BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_YELLOW,
            BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_CYAN,
            BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_MAGENTA};

        String[] names = {"red", "yellow", "green", "cyen", "blue", "magenta"};
        for (int i = 0; i < hues.length; i++) {
            double scaled = 2.0 * i / (hues.length - 1) - 1.0;

            LatLng position = new LatLng(7.0 * Math.abs(scaled) - 15.0, 10.0 * scaled);
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(hues[i]);
            String title = names[i];
            String snippet = String.format(Locale.US, "hue value: %.0f", hues[i]);
            MarkerOptions options = new MarkerOptions().position(position).icon(icon).title(title).snippet(snippet);

            Marker marker = map.addMarker(options);
            data.put(marker, "predefined (" + names[i] + ")");
        }
    }

    private void addColorfulMarkersCircle(GoogleMap map) {
        for (int degree = 9; degree < 360; degree += 18) {
            double rad = Math.toRadians(degree);

            LatLng position = new LatLng(30.0 * Math.cos(rad), 20.0 * Math.sin(rad));
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(degree);
            boolean draggable = true;
            MarkerOptions options = new MarkerOptions().position(position).icon(icon).draggable(draggable);

            Marker marker = map.addMarker(options);
            data.put(marker, "value (" + degree + ")");
        }
    }

    private void addCustomMarkers(GoogleMap map) {
        LatLng position = new LatLng(15.0, 0.0);

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_media_play);
        MarkerOptions options = new MarkerOptions().position(position).icon(icon).anchor(1.0f, 0.5f).title("right arrow").draggable(true);
        Marker marker = map.addMarker(options);
        data.put(marker, "right arrow");

        Bitmap bitmap = prepareBitmap();
        icon = BitmapDescriptorFactory.fromBitmap(bitmap);
        options = new MarkerOptions().position(position).icon(icon).anchor(0.0f, 0.5f).title("left arrow").draggable(true);
        marker = map.addMarker(options);
        data.put(marker, "left arrow");
    }

    private Bitmap prepareBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.settings_50);
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getResources().getDimension(R.dimen.text_size));
        String text = "mg6";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float x = bitmap.getWidth() / 2.0f;
        float y = (bitmap.getHeight() - bounds.height()) / 2.0f - bounds.top;
        canvas.drawText(text, x, y, paint);
        return bitmap;
    }

    private void updateInfo(String action, Marker marker) {
        String markerData = data.get(marker);
        infoTextView.setText("action: " + action + " on: " + markerData);
    }
}
