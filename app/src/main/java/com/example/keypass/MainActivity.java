package com.example.keypass;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static String currFamilyId = null;
    private static String currCarId = null;
    public static final String USERID = "com.example.keypassapp.USERID";
    private Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("DEBUGLOG: starting application");

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                System.out.println("DEBUGLOG: location is " + location.toString());
                if (currFamilyId != null && currCarId != null) {
                    System.out.println("DEBUGLOG: requested unlock for car id " + currCarId);
                    try {
                        System.out.println("DEBUGLOG: location is " + location.toString());
                        unlockThisCar(currFamilyId, currCarId);
                    }
                    catch (JSONException je) {
                        je.printStackTrace();
                    }
                }
                userLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("DEBUGLOG: onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                System.out.println("DEBUGLOG: onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                System.out.println("DEBUGLOG: onProviderDisabled");
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            System.out.println("DEBUGLOG: passed ActivityCompat check");
            System.out.println("DEBUGLOG: last known location: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }


    }

    public static void setCurrFamilyId(String familyId) {
        currFamilyId = familyId;
    }

    public static void setCurrCarId(String carId) {
        currCarId = carId;
    }

    public void unlockThisCar(String familyId, String carId) throws JSONException {
        HttpRestClient.post("/family/" + familyId + "/unlock?car_id=" + carId + "&lat=123&lon=456", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.getString("status"));
                }
                catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                System.out.println("DEBUGLOG: passed grantResults check");
            }
        }
    }

//    public void goToUserSettings(View view) {
//        Intent intent = new Intent(this, UserSettingsActivity.class);
//        intent.putExtra(USERID, userId);
//        startActivity(intent);
//        System.out.println("DEBUGLOG: Going to user settings page");
//    }

    public void goToFamilyList(View view) {
        Intent intent = new Intent(this, FamilyListActivity.class);
        startActivity(intent);
        System.out.println("DEBUGLOG: Going to request list page");
    }

}
