package com.example.jesse.bedroomlight.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jesse.bedroomlight.Device;
import com.example.jesse.bedroomlight.DevicesManager;
import com.example.jesse.bedroomlight.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevicesFragment extends Fragment {


    public DevicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        ListView lView = (ListView) view.findViewById(R.id.devicesList);

        List<Device> devices = new ArrayList();

        devices.add(new Device("Bedside Light","ledStatus"));
        devices.add(new Device("Main Light","bedroom/light/main"));
        devices.add(new Device("Main Light","bedroom/light/main",R.drawable.ic_home_black_24dp));
        devices.add(new Device("Bedside Light","ledStatus",R.drawable.background));
        devices.add(new Device("Main Light","bedroom/light/main",R.drawable.ic_home_black_24dp));
        devices.add(new Device("Main Light","bedroom/light/main",R.drawable.ic_home_black_24dp));



        DevicesManager adapter = new DevicesManager(getActivity(),R.layout.row_device,devices);
        lView.setAdapter(adapter);

        return view;
    }

}
