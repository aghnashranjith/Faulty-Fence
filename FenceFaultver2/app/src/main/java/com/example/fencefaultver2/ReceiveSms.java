package com.example.fencefaultver2;

import static java.lang.Math.random;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReceiveSms extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Mwonoose", Toast.LENGTH_SHORT).show();

        double j = random();
        j = j *10000;
        int random_number = (int) j;
        //Get all notifications settings.
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        boolean getAllNoti = sh.getBoolean("A",false);

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgbody = msgs[i].getMessageBody();

                        DBHandler db = new DBHandler(context);
//                        phoneNumbers

//                        Toast.makeText(context, "From: " + msg_from + "\nBody: "+msgbody, Toast.LENGTH_SHORT).show();
                        if(ifGSMModule(msgs[i].getDisplayOriginatingAddress(),context)) {
//                            Toast.makeText(context, "From: " + msg_from + "\nBody: "+msgbody, Toast.LENGTH_SHORT).show();
                            DBHandler dbHandler = new DBHandler(context);
//                            DBHandler2 dbHandler2 = new DBHandler2(context);
                            BufferedReader reader = new BufferedReader(new StringReader(msgbody));

                            String line = reader.readLine();
                            while(line != null) {
                                String[] parts = line.split("\\,"); // String array
                                String Area = parts[0];
                                String Node = parts[1];
                                String Status = parts[2];
                                String Time = parts[3];
                                String Battery = parts[4];
                                String Voltage = parts[5];

                                if (Area.equals("") || Node.equals("") || Status.equals("") || Time.equals("") || Battery.equals("") || Voltage .equals("")) {
                                    Toast.makeText(context, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                //If any of the nodes are not working
                                //we push a notification to the user
                                if(Status.equalsIgnoreCase("Not Working") || getAllNoti)
                                {
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_HIGH);

                                        NotificationManager manager = (NotificationManager)context.getSystemService(NotificationManager.class);
                                        manager.createNotificationChannel(channel);
                                    }
                                    Intent intent1 = new Intent(context, MainActivity.class);

                                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 1000, intent1, PendingIntent.FLAG_CANCEL_CURRENT);

                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myCh")
                                            .setSmallIcon(android.R.drawable.stat_notify_sync)
                                            .setContentTitle("Node in "+Area+" is "+Status)
                                            .setContentText(Area + ", "+Node+" is "+Status)
                                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                                            .setContentIntent(pendingIntent)
                                            .setAutoCancel(true)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                                    Notification notification = builder.build();

                                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                                    notificationManagerCompat.notify(random_number++, notification);
//                                    Toast.makeText(context, "node: "+ Node, Toast.LENGTH_SHORT).show();
                                }
                                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

//                                Toast.makeText(context, "Date: "+date, Toast.LENGTH_SHORT).show();
                                dbHandler.addNewNodeWithDate(Area, Node, Status, Time, Battery, Voltage, date);
//                                dbHandler.addNewNode(Area, Node, Status, Time, Battery, Voltage);
                                dbHandler.updateCurrentStatus(context, Area, Node, Status, Time, Battery, Voltage);
//                                dbHandler2.updateAreaNodes(context, Area, Node, Status, Time, Battery, Voltage);

                                line = reader.readLine();
                            }

                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean ifGSMModule(String displayOriginatingAddress, Context context) {
        DBHandler db = new DBHandler(context);
        String phone;
//        phone = db.readPhones();
        ArrayList<PhoneModal> phoneNumbers = db.readPhones();
//        Toast.makeText(context, "Phone: " + phoneNumbers.size(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < phoneNumbers.size(); i++) {
//            Toast.makeText(context, "Phone: " + phoneNumbers.get(i).getPhoneNumber(), Toast.LENGTH_LONG).show();
            if(phoneNumbers.get(i).getPhoneNumber().equals(displayOriginatingAddress)){
                return true;
            }
        }
        //if Originating address is not a GSM module
        return false;
    }
}
