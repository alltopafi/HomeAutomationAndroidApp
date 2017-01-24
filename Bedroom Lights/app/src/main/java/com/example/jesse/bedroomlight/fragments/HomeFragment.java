package com.example.jesse.bedroomlight.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jesse.bedroomlight.MainActivity;
import com.example.jesse.bedroomlight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    static String HOST_IP;
    static String HOST_Port;
    static String ClientID;
    Context context;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

//        ((MainActivity) getActivity()).setupConnection();




//        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.bedsideLightToggle);
//        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // The toggle is enabled
//                    ((MainActivity)getActivity()).pubOn("ledStatus");
//                } else {
//                    // The toggle is disabled
//                    ((MainActivity)getActivity()).pubOff("ledStatus");
//                }
//            }
//        });

//        ToggleButton toggle2 = (ToggleButton) view.findViewById(R.id.mainLightToggle);
//        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // The toggle is enabled
//                    ((MainActivity)getActivity()).pubOn("bedroom/light/main");
//                } else {
//                    // The toggle is disabled
//                    ((MainActivity)getActivity()).pubOff("bedroom/light/main");
//                }
//            }
//        });



        return view;

    }

}
