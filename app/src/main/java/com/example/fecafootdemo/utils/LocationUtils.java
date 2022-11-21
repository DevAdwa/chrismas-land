package com.example.fecafootdemo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/31/22)
 */
public class LocationUtils implements LifecycleObserver, LocationListener {
    private static final String TAG = LocationUtils.class.getSimpleName();
    private static final int INTERVAL = 60000 * 10;
    private static final int DISTANCE = 100;

    private final Context context;
    private final LocationManager locationManager;

    public LocationUtils(Context context, Lifecycle lifecycle) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lifecycle.addObserver(this);
    }

    private long lastUpdatedTime;
    private boolean isShown;
    private Location lastLocation;


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void startRequest() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVAL, DISTANCE, this);
    }

    public Location getLastLocation() {
        if (lastLocation != null) {
            if (System.currentTimeMillis() - lastUpdatedTime < TimeUnit.MINUTES.toMillis(60))
                return lastLocation;
            else {
                Location location = getProviderLastKnownLocation();
                return location != null ? location : lastLocation;
            }
        } else {
            return getProviderLastKnownLocation();
        }
    }

    private Location getProviderLastKnownLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return location != null ? location : locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pauseRequest() {
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;
        lastUpdatedTime = System.currentTimeMillis();
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    private void showDialogAlert() {
        if (!isShown) return;
        new AlertDialog.Builder(context)
                .setMessage("You must enable location before usage. Do you want to enable it?")
                .setPositiveButton("Enable", (dialogInterface, i) -> {
                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    isShown = false;
                }).setNegativeButton("Cancel", (dialogInterface, i) -> ((Activity) context).finish())
                .setCancelable(false).show();
        isShown = true;
    }
}
