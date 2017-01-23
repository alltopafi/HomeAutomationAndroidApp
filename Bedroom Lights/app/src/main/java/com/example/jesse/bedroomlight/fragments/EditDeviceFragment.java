package com.example.jesse.bedroomlight.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jesse.bedroomlight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeviceFragment extends Fragment {


    public EditDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_device, container, false);
        //TODO this will be the view to edit/delete a device need to get the information for the device we are editing
        



        return view;
    }

}
