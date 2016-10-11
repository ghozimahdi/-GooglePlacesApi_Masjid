// TODO : ubah nama package sesuai package anda ..
package bcode.pe.hu.googleplacesapi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RChelper is ( Resource Code Helper ).
 * Created by Ghozi Mahdi ( Blank ).
 * http://bcode.id // bcode.pe.hu
 * http://gmcode.id
 * Exp 23-September-2016
 * for contact me profghozimahdi@gmail.com , businessghozimahdi@gmail.com
 */

public class Rchelper {

    public static final int DEBUG = 1;

    /**
     * untuk jika kosong
     **/
    public static Boolean isEmpty(EditText text) {
        if (text.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * untuk jika edittext kurang dari jumlah
     **/
    public static boolean minLength(EditText e, int jmlh) {
        if (e.getText().toString().trim().length() >= jmlh) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * untuk pesan
     **/
    public static void pesan(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * untuk LOG
     **/
    public static void pre(String log) {
        try {
            if (DEBUG == 1) {
                System.err.println(log);
            }
        } catch (Exception e) {
            //TODO : handle exception
        }
    }

    /**
     * untuk jika email tidak benar
     **/
    public static boolean isEmailValid(EditText email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString();

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * check koneksi internet
     **/
    public static boolean isOnline(ConnectivityManager cm) {
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * check gps aktif atau tidak
     **/
    public static boolean isGpsActive(LocationManager locationManager) {
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    /**
     * check gps aktif atau tidak
     **/
    public static void isGps(Context c,LocationManager locationManager, String message) {
        if (!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(c,message);
        }
    }

    /** alert gps no active **/
    private static void buildAlertMessageNoGps(final Context c, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        c.startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
