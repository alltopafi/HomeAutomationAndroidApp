package com.example.jesse.bedroomlight;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesse.bedroomlight.fragments.EditDeviceFragment;
import com.example.jesse.bedroomlight.fragments.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesse on 1/20/17.
 */

public class DevicesManager extends ArrayAdapter {

    public List<Device> getDevices() {
        return devices;
    }

    private List<Device> devices = new ArrayList<Device>();
    private int resource;
    private LayoutInflater inflater;
    private PowerController powerController;


    public DevicesManager(Context context, int resource, List objects) {
        super(context, resource, objects);
        devices = objects;
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = inflater.inflate(resource,null);
        }

        convertView.setClickable(true);
        convertView.setFocusable(true);
        ImageView deviceImageView = (ImageView)convertView.findViewById(R.id.deviceImageView);
        TextView deviceNameTextView = (TextView)convertView.findViewById(R.id.deviceNameTextView);
        TextView deviceTopicTextView = (TextView)convertView.findViewById(R.id.deviceTopicTextView);
        final Switch deviceSwitch = (Switch)convertView.findViewById(R.id.deviceSwiitch);
        powerController = new PowerController(getContext());

        deviceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deviceSwitch.isChecked()){
                    //turn on the device
                    powerController.pubOn(devices.get(position).getMqttTopic());
                }else{
                    //turn off the device
                    powerController.pubOff(devices.get(position).getMqttTopic());
                }
            }
        });


        if(devices.get(position).getImage() != null) {
            byte[] bytes = devices.get(position).getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            deviceImageView.setImageBitmap(bitmap);
        }


        deviceNameTextView.setText(devices.get(position).getName());
        deviceTopicTextView.setText(devices.get(position).getMqttTopic());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity)getContext();
                main.setCurrentDevice(devices.get(position));
            }
        });

        return convertView;
    }



}
