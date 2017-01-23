package com.example.jesse.bedroomlight.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jesse.bedroomlight.Device;
import com.example.jesse.bedroomlight.DevicesManager;
import com.example.jesse.bedroomlight.MainActivity;
import com.example.jesse.bedroomlight.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDeviceFragment extends Fragment {

    private final static int PICK_IMAGE = 100;
    Uri imageURI;
    ImageView addImage;
    EditText deviceName;
    EditText deviceTopic;

    public AddDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);

        deviceName = (EditText) view.findViewById(R.id.deviceNameEditField);
        deviceTopic = (EditText) view.findViewById(R.id.deviceTopicEditField);
        addImage = (ImageView) view.findViewById(R.id.imageViewSelector);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is where a user needs to select an image for their device.
                selectImage(v);
            }
        });

        Button createDevice = (Button) view.findViewById(R.id.createDeviceButton);
        createDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDevice(deviceName.getText().toString(),deviceTopic.getText().toString());
            }
        });

        return view;
    }


    public void selectImage(View view){
        //selecting an image when creating a new device
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageURI = data.getData();
            if(addImage != null)addImage.setImageURI(imageURI);
        }
    }

    private void saveDevice(String name, String topic){

        MainActivity activity = (MainActivity) getActivity();
        activity.getDevicesList().add(new Device(name,topic));
    }

}
