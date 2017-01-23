package com.example.jesse.bedroomlight.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.jesse.bedroomlight.Device;
import com.example.jesse.bedroomlight.DevicesManager;
import com.example.jesse.bedroomlight.MainActivity;
import com.example.jesse.bedroomlight.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevicesFragment extends Fragment {

    Menu menu;
    DevicesManager adapter;

    public DevicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        setHasOptionsMenu(true);

        ListView lView = (ListView) view.findViewById(R.id.devicesList);

        MainActivity activity = (MainActivity) getActivity();
        List<Device> devices = activity.getDevicesList();


        adapter = new DevicesManager(getActivity(),R.layout.row_device,devices);
        lView.setAdapter(adapter);

        return view;
    }


}
