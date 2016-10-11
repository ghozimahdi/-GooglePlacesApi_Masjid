package bcode.pe.hu.googleplacesapi;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends FragmentActivity implements LocationListener {
    GoogleMap map;

    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
    }

    private void getData(Location l) {
        String url = "https://maps.googleapis.com/maps/api/place/search/json?types=mosque&location=" + l.getLatitude() + "," + l.getLongitude() + "&radius=5000&sensor=false&key=AIzaSyAMqfAs0fsWkv_FekUS-dqjLB1djSGT4lM";

        final Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okhttp = new OkHttpClient();
        okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Rchelper.pre("Pesan Request : " + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    new IOException("Error Code");
                }

                final String responData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(responData);
                            JSONArray results = object.getJSONArray("results");
                            String status = object.getString("status");

                            if(status.equals("OK")) {
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject a = results.getJSONObject(i);

                                    JSONObject geometry = a.getJSONObject("geometry");
                                    JSONObject location = geometry.getJSONObject("location");
                                    Double lat = location.getDouble("lat");
                                    Double lng = location.getDouble("lng");

                                    addMarker(lat, lng);
                                    Rchelper.pesan(getBaseContext(), String.valueOf(lat));
                                }
                            }else{
                                Rchelper.pesan(getBaseContext(),"Error Key Places Google");
                            }

                        } catch (JSONException e) {
                            Rchelper.pesan(getBaseContext(),"Error Parsing Json");
                            e.printStackTrace();
                        } catch (Exception e) {
                            Rchelper.pesan(getBaseContext(),"Error Data : " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void addMarker(Double lat, Double lng) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
    }

    private void setupView() {

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            map = mapFragment.getMap();

            map.setMyLocationEnabled(true);


            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
                getData(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        LatLng latlng = new LatLng(lat, lng);

        Rchelper.pre(latlng.toString());

        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        map.addMarker(new MarkerOptions().position(latlng));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
