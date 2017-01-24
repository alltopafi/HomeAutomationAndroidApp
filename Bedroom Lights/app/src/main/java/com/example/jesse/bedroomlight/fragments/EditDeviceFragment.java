package com.example.jesse.bedroomlight.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.example.jesse.bedroomlight.DeviceReaderContract;
import com.example.jesse.bedroomlight.DeviceReaderDBHelper;
import com.example.jesse.bedroomlight.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeviceFragment extends Fragment {

    private final static int PICK_IMAGE = 100;
    Uri imageURI;
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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateButton()) {
                    Toast.makeText(getActivity(), "Updated Device", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
                //TODO remove the fragment from view
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteDevice()){
                    Toast.makeText(getActivity(), "Deleted Device", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Failed to Delete Device", Toast.LENGTH_SHORT).show();
                }
            }
            //TODO remove the fragment from view
        });

        deviceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
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

    public boolean updateButton(){
        DeviceReaderDBHelper dbHelper = new DeviceReaderDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String oldName = name;


        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DeviceReaderContract.FeedEntry.COLUMN_NAME_NAME, deviceName.getText().toString());
        values.put(DeviceReaderContract.FeedEntry.COLUMN_NAME_TOPIC, deviceTopic.getText().toString());

        byte[] byteArray = imageViewToByteArray(deviceImage);
        values.put(DeviceReaderContract.FeedEntry.COLUMN_NAME_IMAGE,byteArray);

        // Which row to update, based on the title
        String selection = DeviceReaderContract.FeedEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = {oldName};


        int count = db.update(
                DeviceReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count ==1){
            return true;
        }else{
            return false;
        }
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
            if(deviceImage != null) deviceImage.setImageURI(imageURI);
        }
    }

    private byte[] imageViewToByteArray(ImageView imageView){

        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
        byte[] bytes = os.toByteArray();
        return bytes;
    }

    public boolean deleteDevice(){

        DeviceReaderDBHelper dbHelper = new DeviceReaderDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = DeviceReaderContract.FeedEntry.COLUMN_NAME_NAME + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { name };
        // Issue SQL statement.
        int status = db.delete(DeviceReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);

        if(status == 1){
            return true;
        }else{
            return false;
        }
    }

}
