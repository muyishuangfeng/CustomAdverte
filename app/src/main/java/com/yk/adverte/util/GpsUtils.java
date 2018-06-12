package com.yk.adverte.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.yk.adverte.R;

public class GpsUtils {



    /**
     * 检测是否打开定位权限
     * @param context
     * @return
     */
    public static boolean checkGPSIsOpen(Context context) {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }




}
