package com.example.jesse.bedroomlight.fragments;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.jesse.bedroomlight.MainActivity;
import com.example.jesse.bedroomlight.R;

import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    Context context;
    EditText ipAddress;
    EditText portNumber;
    EditText clientID;

    private static final Pattern PARTIAl_IP_ADDRESS =
            Pattern.compile("^((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])\\.){0,3}"+
                    "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])){0,1}$");



    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity();
        ipAddress = (EditText) view.findViewById(R.id.ipAddressInput);
        portNumber = (EditText) view.findViewById(R.id.portNumberInput);
        clientID = (EditText) view.findViewById(R.id.clientIDInput);

        ipAddress.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void beforeTextChanged(CharSequence s,int start,int count,int after) {}

            private String mPreviousText = "";
            @Override
            public void afterTextChanged(Editable s) {
                if(PARTIAl_IP_ADDRESS.matcher(s).matches()) {
                    mPreviousText = s.toString();
                } else {
                    s.replace(0, s.length(), mPreviousText);
                }
            }
        });






        setInputs();


        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveSettingsToFile(view);
                try{
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }

                ((MainActivity)getActivity()).setupConnection(getActivity());

                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();

                fragmentTransaction.remove(Settings.this);
                fragmentTransaction.commit();



            }
        });


        return view;
    }

    public void saveSettingsToFile(View view){
        SharedPreferences sharedPref = context.getSharedPreferences("AppInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(ipAddress.length() != 0 && portNumber.length() != 0 && clientID.length() != 0) {

            editor.putString("hostIPAddress", ipAddress.getText().toString());
            editor.putString("portNumber",portNumber.getText().toString());
            editor.putString("clientID",clientID.getText().toString());
            editor.apply();

            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        }else{

            editor.putString("hostIPAddress", "10.0.0.11");
            editor.putString("portNumber","1883");
            editor.putString("clientID","android_client1");
            editor.apply();

            Toast.makeText(context,"invalid fields using default values",Toast.LENGTH_SHORT).show();
        }
    }

    private void setInputs(){
        SharedPreferences sharedPref = context.getSharedPreferences("AppInfo", MODE_PRIVATE);

        ipAddress.setText(sharedPref.getString("hostIPAddress","10.0.0.11"));
        portNumber.setText(sharedPref.getString("portNumber","1883"));
        clientID.setText(sharedPref.getString("clientID","android_client1"));
    }



}
