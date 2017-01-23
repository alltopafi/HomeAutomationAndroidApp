package com.example.jesse.bedroomlight;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.jesse.bedroomlight.fragments.AddDeviceFragment;
import com.example.jesse.bedroomlight.fragments.DevicesFragment;
import com.example.jesse.bedroomlight.fragments.HomeFragment;
import com.example.jesse.bedroomlight.fragments.Settings;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;
import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private MqttClient client;
    private ArrayList<Device> devicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicesList = new ArrayList<Device>();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DevicesFragment fragment = new DevicesFragment();
        fragmentTransaction.add(R.id.mainFrame, fragment);
        fragmentTransaction.commit();


    }


    public void addDeviceButtonClicked(MenuItem item){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddDeviceFragment fragment = new AddDeviceFragment();
        fragmentTransaction.add(R.id.mainFrame, fragment);
        fragmentTransaction.commit();

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void homeButtonClicked(MenuItem item){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HomeFragment fragment = new HomeFragment();
        fragmentTransaction.add(R.id.mainFrame, fragment);
        fragmentTransaction.commit();

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void devicesButtonClicked(MenuItem item){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DevicesFragment fragment = new DevicesFragment();
        fragmentTransaction.add(R.id.mainFrame, fragment);

        //if if devices is the fragment in view we don't want to commit


        fragmentTransaction.commit();

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void settingsButtonClicked(MenuItem item){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Settings fragment = new Settings();
        fragmentTransaction.add(R.id.mainFrame, fragment);
        fragmentTransaction.commit();

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

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

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

//        setupConnection(this);


    }

    public void setupConnection(){


        SharedPreferences sharedPref = this.getSharedPreferences("AppInfo", MODE_PRIVATE);

        String HOST_IP = sharedPref.getString("hostIPAddress","10.0.0.11");
        String HOST_Port = sharedPref.getString("portNumber","1883");
        String ClientID = sharedPref.getString("clientID","android_client1");


        try {

            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient("tcp://" + HOST_IP + ":" + HOST_Port, ClientID, persistence);
            client.connect();

            if(connect()) {
                Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
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

public ArrayList<Device> getDevicesList(){
    return devicesList;
}



}
