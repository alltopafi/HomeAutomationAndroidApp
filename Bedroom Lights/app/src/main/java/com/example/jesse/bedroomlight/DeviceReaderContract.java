package com.example.jesse.bedroomlight;

import android.provider.BaseColumns;

/**
 * Created by Jesse on 1/22/17.
 */

public final class DeviceReaderContract {

        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private DeviceReaderContract() {

        }

        /* Inner class that defines the table contents */
        public static class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "devicesTable";
            public static final String COLUMN_NAME_NAME = "name_device";
            public static final String COLUMN_NAME_TOPIC = "topic_device";
            public static final String COLUMN_NAME_IMAGE = "image_device";


            public void createTable() {
                final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                            FeedEntry._ID + " INTEGER PRIMARY KEY," +
                            FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                            FeedEntry.COLUMN_NAME_TOPIC + " TEXT,"+
                            FeedEntry.COLUMN_NAME_IMAGE + " TEXT)";
            }

            public void dropTable() {
               final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
            }
        }
    }




