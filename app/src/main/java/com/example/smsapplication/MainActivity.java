package com.example.smsapplication;

import static com.example.smsapplication.R.id.checkBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //initialize variable
    EditText editTextPhone,editTextMessage;
    CheckBox call,busy,emer;
    Button btnSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variable
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
        call=(CheckBox)findViewById(R.id.checkBox);
        busy=(CheckBox)findViewById(R.id.checkBox2);
        emer=(CheckBox)findViewById(R.id.checkBox3);
        btnSent = findViewById(R.id.btnSent);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check condition for permission
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){
                    // when permission is granted
                    // create a method
                    sendSMS();
                }else{
                    //when permission is not granted
                    //request for permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // permission is required
            //call method
            sendSMS();
        }else {
            // when permission is denied
            //display toast msg
            Toast.makeText(this,"Permission is Denied", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendSMS() {
        //get value form editText
        String phone = editTextPhone.getText().toString();
        String message = editTextMessage.getText().toString();
        String Add1 = call.getText().toString();
        String Add2 = busy.getText().toString();
        String Add3 = emer.getText().toString();

        //check condition if string is empty or not
        if (!phone.isEmpty() && !message.isEmpty()){
            // initialize SMS Manager
            SmsManager smsManager = SmsManager.getDefault();
            //send message
            smsManager.sendTextMessage(phone,null,message,null,null);
            //display Toast msg
            Toast.makeText(this,"SMS sent successfully",Toast.LENGTH_SHORT).show();
        }else {
            //when string is empty then display toast msg
            Toast.makeText(this,"please enter phone and message",Toast.LENGTH_SHORT).show();
        }
        if(call.isChecked()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,Add1,null,null);}
        if(busy.isChecked()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,Add2,null,null);}
        if(emer.isChecked()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,Add3,null,null);}

    }
}