package com.example.fencefaultver2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.

    // below variable is for our database name.
    private static final String DB_NAME = "fencedb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "FenceData";

    //below variable is for our current status table
    private static final String TABLE_NAME_2 = "NodeData";

    //below variable is for our phone numbers of GSM modules table
    private static final String TABLE_NAME_3 = "GSMmodules";

    //below variable is for our phone number column
    private static final String PHONE_COL = "phone";

    //below variable is for phone ID
    private static final String PHONE_ID = "id";

    //below variable is for our table name.
    private static final String DATE_COL = "date";

    // below variable is for our id column.
    private static final String AREA_COL = "area";

    // below variable is for our course name column
    private static final String NODE_COL = "node";

    // below variable is for our course tracks column.
    private static final String STATUS_COL = "status";

    // below variable id for our course duration column.
    private static final String TIME_COL = "time";

    // below variable for our course description column.
    private static final String BATTERY_COL = "battery";

    private static final String VOLTAGE_COL = "voltage";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + AREA_COL + " TEXT, "
                + NODE_COL + " TEXT,"
                + STATUS_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + BATTERY_COL + " TEXT,"
                + VOLTAGE_COL + " TEXT,"
                + DATE_COL + " TEXT)";


        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);

        //Creating another table for storing current statuses
        // of the nodes. This table will be used to display
        //statuses of the nodes of their respective areas
        // as displayed on the MainActivity Screen

        String query2 = "CREATE TABLE " + TABLE_NAME_2 + " ("
                + AREA_COL + " TEXT, "
                + NODE_COL + " TEXT,"
                + STATUS_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + BATTERY_COL + " TEXT,"
                + VOLTAGE_COL + " TEXT)";

        db.execSQL(query2);

        //Really unsure if this command was executed
        String query3 = "CREATE UNIQUE INDEX data_idx ON "+TABLE_NAME_2+" ("
                + AREA_COL + ", "
                + NODE_COL + " )";

        db.execSQL(query3);


        String query4 = "CREATE TABLE " + TABLE_NAME_3 + " (" + PHONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PHONE_COL + " TEXT)";

        db.execSQL(query4);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewNode(String area,String node, String status, String time, String battery, String voltage) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(AREA_COL, area);
        values.put(NODE_COL, node);
        values.put(STATUS_COL, status);
        values.put(TIME_COL, time);
        values.put(BATTERY_COL, battery);
        values.put(VOLTAGE_COL, voltage);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
//        db.update(TABLE_NAME, values, "node = ?", new String[]{node});

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void addNewNodeWithDate(String area,String node, String status, String time, String battery, String voltage, String date) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(AREA_COL, area);
        values.put(NODE_COL, node);
        values.put(STATUS_COL, status);
        values.put(TIME_COL, time);
        values.put(BATTERY_COL, battery);
        values.put(VOLTAGE_COL, voltage);
        values.put(DATE_COL, date);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
//        db.update(TABLE_NAME, values, "node = ?", new String[]{node});

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    //Reading nodes
    // we have created a new method for reading all the courses.
    public ArrayList<NodeModal> readNodes() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorNodes = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<NodeModal> NodeModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorNodes.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                NodeModalArrayList.add(new NodeModal(cursorNodes.getString(0),
                        cursorNodes.getString(1),
                        cursorNodes.getString(2),
                        cursorNodes.getString(3),
                        cursorNodes.getString(4),
                        cursorNodes.getString(5)));
            } while (cursorNodes.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorNodes.close();
        return NodeModalArrayList;
    }

    public ArrayList<NodeModal> readNodes2() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorNodes = db.rawQuery("SELECT * FROM " + TABLE_NAME_2, null);

        // on below line we are creating a new array list.
        ArrayList<NodeModal> NodeModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorNodes.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                NodeModalArrayList.add(new NodeModal(cursorNodes.getString(0),
                        cursorNodes.getString(1),
                        cursorNodes.getString(2),
                        cursorNodes.getString(3),
                        cursorNodes.getString(4),
                        cursorNodes.getString(5)));
            } while (cursorNodes.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorNodes.close();
        return NodeModalArrayList;
    }

    public long addPhone(String phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONE_COL, phone);
        long shit = db.insert(TABLE_NAME_3, null, values);
        db.close();
        return shit;
    }

    public ArrayList<PhoneModal> readPhones() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorNodes = db.rawQuery("SELECT * FROM " + TABLE_NAME_3, null);

        // on below line we are creating a new array list.
        ArrayList<PhoneModal> PhoneArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorNodes.moveToFirst()) {
            do {

                // on below line we are adding the data from cursor to our array list.
                PhoneArrayList.add(new PhoneModal(cursorNodes.getString(1)));
            } while (cursorNodes.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorNodes.close();
        return PhoneArrayList;
    }

    public void updatePhoneNumber(String originalPhoneNumber,String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PHONE_COL, phoneNumber);

        db.update(TABLE_NAME_3, values, "phone=?", new String[]{originalPhoneNumber});
        db.close();
    }

    public void deletePhone(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_3, "phone=?", new String[]{phoneNumber});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        onCreate(db);
    }

    public void updateCurrentStatus(Context context, String area, String node, String status, String time, String battery, String voltage) {
        //Creating a method that inserts new nodes if found any else updates current statuses of already available nodes;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AREA_COL, area);
        values.put(NODE_COL, node);
        values.put(STATUS_COL, status);
        values.put(TIME_COL, time);
        values.put(BATTERY_COL, battery);
        values.put(VOLTAGE_COL, voltage);

        int isUpdate =  db.update(TABLE_NAME_2, values, "node = ?", new String[]{node});
        if (isUpdate > 0) {
//            Toast.makeText(context, "Update Succesful! isUpdate:", Toast.LENGTH_SHORT).show();
            //do nothing
        } else {
//            Toast.makeText(context, "Reached insert", Toast.LENGTH_SHORT).show();
            db.insert(TABLE_NAME_2, null, values);
            db.close();
        }

    }

    public void clearLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

    public boolean exportDatabase() {
//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        }
        else {
            //We use the Download directory for saving our .csv file.
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists())
            {
                exportDir.mkdirs();
            }

            File file;
            PrintWriter printWriter = null;
            try
            {
                file = new File(exportDir, "FenceFaultLogs.txt");
                file.createNewFile();
                printWriter = new PrintWriter(new FileWriter(file));

                /**This is our database connector class that reads the data from the database.
                 * The code of this class is omitted for brevity.
                 */
                SQLiteDatabase db = this.getReadableDatabase(); //open the database for reading

                /**Let's read the first table of the database.
                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
                 * containing all records of the table (all fields).
                 * The code of this class is omitted for brevity.
                 */
                Cursor curCSV = db.rawQuery("select * from "+TABLE_NAME, null);
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                printWriter.println("Fence Fault Logs");
                printWriter.println("DATE,AREA,NODE,STATUS,TIME,BATTERY,VOLTAGE");
                while(curCSV.moveToNext())
                {
                    String Area = curCSV.getString(0);
                    String Node = curCSV.getString(1);
                    String Status = curCSV.getString(2);
                    String Time = curCSV.getString(3);
                    String Battery = curCSV.getString(4);
                    String Voltage = curCSV.getString(5);
                    String Date = curCSV.getString(6);

                    String record = Date + "," + Area + "," + Node + "," + Status + "," + Time + "," + Battery + "," + Voltage;
                    printWriter.println(record); //write the record in the .csv file
                }

                curCSV.close();
                db.close();
            }

            catch(Exception exc) {
                //if there are any exceptions, return false
                return false;
            }
            finally {
                if(printWriter != null) printWriter.close();
            }

            //If there are no errors, return true.
            return true;
        }
    }













}




////JUNK////
/*
    public void updateCurrentStatus(Context context, String area,String node, String status, String time, String battery, String voltage)
    //Creating a method that inserts new nodes if found any else updates current statuses of already available nodes;
    {
//        try {
//            Toast.makeText(context, "Sett aayi kanum", Toast.LENGTH_SHORT).show();
//        String Query = "INSERT OR REPLACE INTO "+ TABLE_NAME_2 + " VALUES "+"("
//                + area + ", "
//                + node + ", "
//                + status + ", "
//                + time + ", "
//                + battery + ", "
//                + voltage + ")";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(Query);
//        db.close(); } catch (SQLException e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Polinj", Toast.LENGTH_SHORT).show();
//        }

        try {
            //try updating a node
            Toast.makeText(context, "Trying updating", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = this.getWritableDatabase();

            // on below line we are creating a
            // variable for content values.
            ContentValues values = new ContentValues();

            // on below line we are passing all values
            // along with its key and value pair.
            values.put(AREA_COL, area);
            values.put(NODE_COL, node);
            values.put(STATUS_COL, status);
            values.put(TIME_COL, time);
            values.put(BATTERY_COL, battery);
            values.put(VOLTAGE_COL, voltage);

            // after adding all values we are passing
            // content values to our table.
            db.update(TABLE_NAME_2, values, "area = ?", new String[]{area});

            // at last we are closing our
            // database after adding database.
            db.close();
        } catch (Exception e) {
            //If update failed insert
            Toast.makeText(context, "Update failed trying insert", Toast.LENGTH_SHORT).show();
            try {
                SQLiteDatabase db = this.getWritableDatabase();

                // on below line we are creating a
                // variable for content values.
                ContentValues values = new ContentValues();

                // on below line we are passing all values
                // along with its key and value pair.
                values.put(AREA_COL, area);
                values.put(NODE_COL, node);
                values.put(STATUS_COL, status);
                values.put(TIME_COL, time);
                values.put(BATTERY_COL, battery);
                values.put(VOLTAGE_COL, voltage);

                // after adding all values we are passing
                // content values to our table.
                db.insert(TABLE_NAME_2, null, values);

                // at last we are closing our
                // database after adding database.
                db.close();
            } catch (Exception exception) {
                exception.printStackTrace();
                Toast.makeText(context, "Insert Failed too", Toast.LENGTH_SHORT).show();
            }

        }
     } */