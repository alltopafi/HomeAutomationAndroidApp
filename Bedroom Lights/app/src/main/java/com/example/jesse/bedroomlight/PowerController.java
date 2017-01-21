package com.example.jesse.bedroomlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jesse on 1/20/17.
 */

public class PowerController {

    Context context;
    MqttClient client;

    public PowerController(Context context){
        this.context = context;
        setupConnection(context);
    }

    public void pubOn(String topic){
        String payload = "1";
        try {
            client.publish(topic, payload.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pubOff(String topic){
        String payload = "0";
        try {
            client.publish(topic, payload.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void setupConnection(Context context){


        SharedPreferences sharedPref = context.getSharedPreferences("AppInfo", MODE_PRIVATE);

        String HOST_IP = sharedPref.getString("hostIPAddress","10.0.0.11");
        String HOST_Port = sharedPref.getString("portNumber","1883");
        String ClientID = sharedPref.getString("clientID","android_client1");


        try {

            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient("tcp://" + HOST_IP + ":" + HOST_Port, ClientID, persistence);
            client.connect();

            if(connect()) {
                Toast.makeText(context,"Connected",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private boolean connect() {
        try {

            String topics[] = {"ledStatus"};
            int qos[] = {1};
            client.subscribe(topics, qos);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }


}
