package com.example.arrivaldwis.tombolpanic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class FragmentInstitusi extends Fragment {
    Button btnPolisi, btnRS, btnFire;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.fragment_institusi, container, false);
        Button btnPolisi = (Button) view.findViewById(R.id.btnPolisi);
        Button btnRS = (Button) view.findViewById(R.id.btnRS);
        Button btnFire = (Button) view.findViewById(R.id.btnFire);
        btnPolisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0222013179"));
                startActivity(callIntent);
            }
        });
        btnRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0222034953"));
                startActivity(callIntent);
            }
        });
        btnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0227207113"));
                startActivity(callIntent);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // setUserVisibleHint(true);
    }

}


