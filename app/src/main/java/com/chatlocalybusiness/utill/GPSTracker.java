package com.chatlocalybusiness.utill;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class GPSTracker extends Service implements LocationListener {

    private static Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        GPSTracker.mContext = context;
        getLocation();
    }

    public boolean getGpsStatus() {
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.i("TAG", "No Network Provider available");
                //showSettingsAlert();
            } else if (isNetworkEnabled) {
                this.canGetLocation = true;
                // if GPS Enabled get lat/long using GPS Services
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                Log.d("Network", "Network");
                if (locationManager != null) {
                    Log.i("In First If N/W", "In First if N/W");
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.i("In Network Provider", "Get Lat Long from Network Provider");
                    }
                }
            } else if (isGPSEnabled) {
                this.canGetLocation = true;
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        Log.i("In First If GPS", "In First if GPS");
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i("In GPS", "Get Lat Long from GPS");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }


    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        getLatitude();
        getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Method to turn on GPS *
     */


    public void gpsDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    public void turnGPSOn() {
        try {

            String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                mContext.sendBroadcast(poke);
            }
        } catch (Exception e) {

        }
    }

    // Method to turn off the GPS
    @SuppressWarnings("deprecation")
    public static void turnGPSOff() {
        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);
        }
    }

   /* public String getCityName() {
        String CityName = "";
        try {
            // Getting address from found locations.
            Geocoder geocoder;

            List<Address> addresses;
            geocoder = new Geocoder(mContext, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            CityName = addresses.get(0).getLocality();
            // you can get more details other than this . like country code, state code, etc.

            System.out.println(" CityName " + CityName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CityName;
    }
*/

    // below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location.

    public static Location getLastKnownLocation(Context context) {
        Location location = null;
        LocationManager locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<?> list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator<?> iterator = list.iterator();
        do {
            //System.out.println("---------------------------------------------------------------------");
            if (!iterator.hasNext())
                break;
            String s = (String) iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if (i != false && !locationmanager.isProviderEnabled(s))
                continue;
            // System.out.println("provider ===> "+s);
            Location location1 = locationmanager.getLastKnownLocation(s);
            if (location1 == null)
                continue;
            if (location != null) {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if (f >= f1) {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if (l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
            // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
            // System.out.println("---------------------------------------------------------------------");
        } while (true);
        return location;
    }

    /*public String getAddress() {
        List<Address> addresses = null;
        String sAdd = "";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null) {
            if (addresses.size() != 0) {
                Address add = addresses.get(0);
                String address = add.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = add.getLocality();
                String state = add.getAdminArea();
                String country = add.getCountryName();
                String postalCode = add.getPostalCode();

                sAdd = address + "," + city + "," + state + "," + postalCode + "," + country;
            }
        }
        return sAdd;
    }*/
}
