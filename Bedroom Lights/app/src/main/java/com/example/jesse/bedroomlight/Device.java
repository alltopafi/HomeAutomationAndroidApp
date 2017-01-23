package com.example.jesse.bedroomlight;

/**
 * Created by Jesse on 1/20/17.
 */

public class Device {

    private String name;
    private int state;
    private String mqttTopic;
    private byte[] image;

    public Device(String name, String mqttTopic){
        this.name = name;
        this.mqttTopic = mqttTopic;
        state = 0;
    }

    public Device(String name, String mqttTopic, byte[] image){
        this.name = name;
        this.mqttTopic = mqttTopic;
        this.image = image;
        state = 0;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    @Override
    public String toString(){
        return "Device named "+name+" has the topic of "+mqttTopic+" and it's state is "+state+".";
    }
}
