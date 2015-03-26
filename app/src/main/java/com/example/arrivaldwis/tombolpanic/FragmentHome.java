package com.example.arrivaldwis.tombolpanic;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentHome extends Fragment implements LocationListener {
    ImageButton btnSOS;
    SQLiteHelper sqLiteHelper;
    ArrayList<HashMap<String,String>> itemWhitelists;
    double lat;
    double lng;
    long minTime;
    float minDistance;
    String locProvider;
    LocationManager locMgr;
    String message="";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get the view from fragmenttab1.xml
        sqLiteHelper = new SQLiteHelper(getActivity());
        itemWhitelists = sqLiteHelper.getAllKontak();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        btnSOS = (ImageButton) view.findViewById(R.id.btnSOS);
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemWhitelists.size() != 0) {
                    for(int i=0; i<itemWhitelists.size(); i++) {
                        HashMap<String, String> hashmapData = itemWhitelists.get(i);
                        String nama = hashmapData.get("nama");
                        String notelp = hashmapData.get("notelp");
                        getLokasi(nama,notelp);
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Anda belum memiliki Whitelist",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),"Lokasi anda telah dikirimkan ke daftar Whitelist",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void getLokasi(String nama, String phoneNumber) {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            message = "Anak anda sedang dalam keadaan berbahaya, dan GPSnya dalam keadaan mati -- TombolPanic Apps";
        } else {
            locMgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locProvider = LocationManager.NETWORK_PROVIDER;
            Location lastKnownLocation = locMgr.getLastKnownLocation(locProvider);
            lat = lastKnownLocation.getLatitude();
            lng = lastKnownLocation.getLongitude();

            Criteria cr = new Criteria();
            cr.setAccuracy(Criteria.ACCURACY_FINE);

            locProvider = locMgr.getBestProvider(cr, false);
            minTime = 1 * 60 * 1000;
            minDistance = 1;

            String googleUrl = "https://www.google.co.id/maps/@" + lat + "," + lng;
            message = "Anak anda sedang dalam keadaan berbahaya buka link ini " + googleUrl + " untuk mengetahui posisi anak anda  -- TombolPanic Apps";
        }

        sendSMS(nama,phoneNumber,message);
    }

    private void sendSMS(String nama, String phoneNumber, String message)
    {
        String nm = nama;
        String phoneNo = phoneNumber;
        String msg = message;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getActivity().getApplicationContext(), "Message to "+nm+" Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(),
                    ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // setUserVisibleHint(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
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


