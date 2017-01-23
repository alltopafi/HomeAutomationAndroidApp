package com.example.jesse.bedroomlight.fragments;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jesse.bedroomlight.DeviceReaderContract;
import com.example.jesse.bedroomlight.DeviceReaderDBHelper;
import com.example.jesse.bedroomlight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeviceFragment extends Fragment {

    EditText deviceName;
    EditText deviceTopic;
    ImageView deviceImage;
    Button updateButton;
    Button deleteButton;
    String name;

    public EditDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_device, container, false);

        init(view);
        setInitView();

        //TODO set update and delete buttons
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButton();
            }
        });

        return view;
    }


    public void init(View view){
        deviceName = (EditText) view.findViewById(R.id.editDeviceNameEditText);
        deviceTopic = (EditText) view.findViewById(R.id.editDeviceTopicEditText);
        deviceImage = (ImageView) view.findViewById(R.id.editDeviceImageView);
        updateButton = (Button) view.findViewById(R.id.editDeviceUpdateButton);
        deleteButton = (Button) view.findViewById(R.id.editDeviceDeleteButton);
    }

    public void setInitView(){

        Bundle bundle = getArguments();
        if(bundle != null){
            name = bundle.getString("name");
            String topic = bundle.getString("topic");
            byte[] image = bundle.getByteArray("image");
            deviceName.setText(name);
            deviceTopic.setText(topic);
            deviceImage.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
        }
    }

    public void updateButton(){
        DeviceReaderDBHelper dbHelper = new DeviceReaderDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String oldName = name;


// New value for one column
        ContentValues values = new ContentValues();
        values.put(DeviceReaderContract.FeedEntry.COLUMN_NAME_NAME, deviceName.getText().toString());
        values.put(DeviceReaderContract.FeedEntry.COLUMN_NAME_TOPIC, deviceTopic.getText().toString());

        //TODO update the image if the user would like to
//        values.put(DeviceReaderContract.FeedEntry.COLUMN_NAME_IMAGE,deviceImage.)

// Which row to update, based on the title
        String selection = DeviceReaderContract.FeedEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = {oldName};


        int count = db.update(
                DeviceReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);


    }

}
