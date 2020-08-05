package com.example.andriodlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<Message> msgList = new ArrayList<>();
    Button btnSend;
    Button btnReceive;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        listView = (ListView)findViewById(R.id.lstView);
        editText = (EditText)findViewById(R.id.chatEditText);
        btnSend = (Button)findViewById(R.id.SendBtn);
        btnReceive = (Button)findViewById(R.id.ReceiveBtn);

        btnSend.setOnClickListener(c -> {

                     String sendMessage = editText.getText().toString();
                if ( !sendMessage.equals("")){
                    db.insertData(sendMessage, true);
                    editText.setText("");
                    msgList.clear();
                    //Message model = new Message(sendMessage, true);
                   // msgList.add(model);
                    viewData();

                    //ChatAdapter adt = new ChatAdapter(msgList, getApplicationContext());
                    //listView.setAdapter(adt);
                    //editText.setText("");
                }

        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendMessage = editText.getText().toString();
                if ( !sendMessage.equals("")){
                    Message model = new Message(sendMessage, false);
                    msgList.add(model);

                    ChatAdapter adt = new ChatAdapter(msgList, getApplicationContext());
                    listView.setAdapter(adt);
                    editText.setText("");
                }
            }
        });

        listView.setOnItemLongClickListener( (p, b, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setPositiveButton("Yes", (click, arg) -> {
                        msgList.remove(position);
                        ChatAdapter myAdapter = new ChatAdapter(msgList,getApplicationContext());
                        listView.setAdapter(myAdapter);
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })
                    .setMessage("The selected row is "+position +"\nThe database id is "+id)
                    .setView(getLayoutInflater().inflate(R.layout.alert_dialog, null) )

                    //Show the dialog
                    .create().show();

            return true;
        });
    }

    //view data
    public void viewData(){
     Cursor cursor = db.viewData();
     if(cursor.getCount() !=0){
         while (cursor.moveToNext()){
             Message msg = new Message(cursor.getString(1), cursor.getInt(2)== 0? true:false);
             msgList.add(msg);
             ChatAdapter adt = new ChatAdapter(msgList, getApplicationContext());
             listView.setAdapter(adt);
         }
     }
    }
}





