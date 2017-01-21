package com.example.jesse.bedroomlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesse on 1/20/17.
 */

public class DevicesManager extends ArrayAdapter {

    private List<Device> devices = new ArrayList<Device>();
    private int resource;
    private LayoutInflater inflater;

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

        ImageView deviceImageView = (ImageView)convertView.findViewById(R.id.deviceImageView);
        TextView deviceNameTextView = (TextView)convertView.findViewById(R.id.deviceNameTextView);
        TextView deviceTopicTextView = (TextView)convertView.findViewById(R.id.deviceTopicTextView);
        final Switch deviceSwitch = (Switch)convertView.findViewById(R.id.deviceSwiitch);

        deviceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PowerController powerController = new PowerController(getContext());
                if(deviceSwitch.isChecked()){
                    Toast.makeText(getContext(),devices.get(position).getName(),Toast.LENGTH_SHORT).show();
                    //turn on the device
                    powerController.pubOn(devices.get(position).getMqttTopic());
                }else{
                    //turn off the device
                    Toast.makeText(getContext(),devices.get(position).getMqttTopic(),Toast.LENGTH_SHORT).show();
                    powerController.pubOff(devices.get(position).getMqttTopic());
                }
            }
        });

        deviceImageView.setImageResource(devices.get(position).getImage());
        deviceNameTextView.setText(devices.get(position).getName());
        deviceTopicTextView.setText(devices.get(position).getMqttTopic());



        return convertView;
    }
}
