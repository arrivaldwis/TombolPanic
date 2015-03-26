package com.example.arrivaldwis.tombolpanic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.arrivaldwis.tombolpanic.Adapter.AdapterWhitelist;
import com.example.arrivaldwis.tombolpanic.Item.ItemWhitelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentWhitelist extends Fragment {
    ArrayList<HashMap<String,String>> itemWhitelists;
    AdapterWhitelist aw;
    ListView lvWhitelist;
    SQLiteHelper sqLiteHelper;
    private static final String LOGCAT = null;
    SimpleAdapter adapter;
    TextView txtId;
    final HashMap<String, String> queryValues =  new  HashMap<String, String>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab1.xml
        sqLiteHelper = new SQLiteHelper(getActivity());
        itemWhitelists = sqLiteHelper.getAllKontak();
        View view = inflater.inflate(R.layout.fragment_whitelist, container, false);
        lvWhitelist = (ListView) view.findViewById(R.id.lvWhitelist);
        Button btnAdd = (Button) view.findViewById(R.id.btnAddKontak);
        txtId = (TextView) view.findViewById(R.id.txtId);
        refresh();
        lvWhitelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromList(position,id);
                return true;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(1);
            }
        });
        return view;
    }

    protected void removeItemFromList(int position,long id) {
        final int deletePosition = position;
        final long ids = id;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this whitelist?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                itemWhitelists.remove(deletePosition);
                sqLiteHelper.hapusKontak(String.valueOf(ids));
                refresh();
                adapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

    public void refresh() {
        sqLiteHelper = new SQLiteHelper(getActivity());
        itemWhitelists = sqLiteHelper.getAllKontak();
        if (itemWhitelists.size() != 0) {
            adapter = new SimpleAdapter(getActivity(),
                    itemWhitelists, R.layout.row_whitelist, new String[] {"id","nama", "notelp" }, new int[] {
                    R.id.txtId,R.id.txtNama, R.id.txtNoTelp
            });
            lvWhitelist.setAdapter(adapter);
        } else {
            lvWhitelist.setAdapter(null);
            Log.d(LOGCAT, "Data Kosong");
        }
    }

    public void show(int dialogid) {
        final Dialog d = new Dialog(getActivity());
        d.setTitle("Tambah Kontak");
        d.setContentView(R.layout.dialog_addkontak);
        Button btncancel = (Button) d.findViewById(R.id.btncancelfb);
        Button btnsend = (Button) d.findViewById(R.id.btnsendfeedback);
        if (dialogid == 1) {
            btnsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText txtNama = (EditText) d.findViewById(R.id.txtNama);
                    EditText txtNoTelp = (EditText) d.findViewById(R.id.txtNoTelp);
                    try {
                        queryValues.put("nama", txtNama.getText().toString());
                        queryValues.put("notelp", txtNoTelp.getText().toString());
                        sqLiteHelper.addKontak(queryValues);
                        d.dismiss();
                        refresh();
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                }
            });
        }
        btncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // setUserVisibleHint(true);
    }

}


