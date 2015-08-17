package com.gis.map;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.sql.Connection;

/**
 * Created by x0158990 on 17.08.15.
 */
public class GooglePlayServicesErrorDialogFragment extends DialogFragment {
    private static final String TAG = GooglePlayServicesErrorDialogFragment.class.getSimpleName();
    private static final String STATUS_KEY = "status";

    public static GooglePlayServicesErrorDialogFragment newInstance(int status) {
        GooglePlayServicesErrorDialogFragment f = new GooglePlayServicesErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(STATUS_KEY, status);
        f.setArguments(args);
        return f;
    }

    public static void showDialog(int status, FragmentActivity fragmentActivity) {
        android.support.v4.app.FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        GooglePlayServicesErrorDialogFragment f = newInstance(status);
        f.show(fm, TAG);
    }

    public static boolean showDialogIfNotAvailable(FragmentActivity activity) {
        removeDialog(activity);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        boolean available = status == ConnectionResult.SUCCESS;
        if (!available) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showDialog(status, activity);
            } else {
                Toast.makeText(activity.getApplication(),"Not Available", Toast.LENGTH_SHORT).show();
            }
        }
        return available;
    }

    public static void removeDialog(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag(TAG);
        if (f != null) {
            fm.beginTransaction().remove(f).commit();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int status = args.getInt(STATUS_KEY);
        return GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0);
    }

}
