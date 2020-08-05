package com.example.andriodlabs;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ChatRoomActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "ChatRoomActivity";
    private ArrayList<Message> elements = new ArrayList<>(  );
    private MyListAdapter myAdapter;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView list = findViewById(R.id.lstView);
        Button send = findViewById(R.id.SendBtn);
        Button receive = findViewById(R.id.ReceiveBtn);
        EditText chattext = findViewById(R.id.chatEditText);

        loadDataFromDatabase();

        send.setOnClickListener((button) -> {
                    String Text = chattext.getText().toString();
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(DatabaseHelper.COL_CHAT, Text);
                    newRowValues.put(DatabaseHelper.COL_SENT, 1);
                    long newId = db.insert(DatabaseHelper.TABLE_NAME, null, newRowValues);
                    Message newChat = new Message(Text,true, newId);
                    elements.add(newChat);
                    myAdapter.notifyDataSetChanged();
                    chattext.setText("");
                }
        );

        receive.setOnClickListener((button) -> {
                    String Text = chattext.getText().toString();
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(DatabaseHelper.COL_CHAT, Text);
                    newRowValues.put(DatabaseHelper.COL_SENT, 0);
                    long newId = db.insert(DatabaseHelper.TABLE_NAME, null, newRowValues);
                    Message newChat = new Message(Text,false, newId);
                    elements.add(newChat);
                    myAdapter.notifyDataSetChanged();
                    chattext.setText("");
                }
        );

        list.setOnItemLongClickListener( (p, b, pos, id) -> {
            Message selectedChat = elements.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")


                    .setMessage("The selected row is:" + pos +
                            "\nThe database id id:" + selectedChat.getId())

                    .setPositiveButton("Yes", (click, arg) -> {


                        deleteChat(selectedChat);
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })

                    .setNegativeButton("No", (click, arg) -> { })


                    .create().show();
            return true;
        });

        list.setAdapter(myAdapter = new MyListAdapter());
    }

    private void loadDataFromDatabase()
    {
        //get a database connection:
        DatabaseHelper dbOpener = new DatabaseHelper(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {DatabaseHelper.COL_ID, DatabaseHelper.COL_CHAT, DatabaseHelper.COL_SENT};
        //query all the results from the database:
        Cursor results = db.query(false, DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results,DatabaseHelper.VERSION_NUM);
        //Now the results object has rows of results that match the query.
        //find the column indices:
        int chatColumnIndex = results.getColumnIndex(DatabaseHelper.COL_CHAT);
        int sentColIndex = results.getColumnIndex(DatabaseHelper.COL_SENT);
        int idColIndex = results.getColumnIndex(DatabaseHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String chat = results.getString(chatColumnIndex);
            boolean sent = results.getInt(sentColIndex) > 0;
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            elements.add(new Message(chat, sent, id));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }



    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return elements.size(); }

        public Message getItem(int position) {return elements.get(position); }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View newView = convertView;
            LayoutInflater inflater = getLayoutInflater();

            Message m = getItem(position);
            if(m.getSend())

                newView = inflater.inflate(R.layout.activity_send, parent, false);

            else
            {
                newView = inflater.inflate(R.layout.activity_receive, parent, false);

            }


            TextView tView = newView.findViewById(R.id.messageText);
            tView.setText( m.getMessage());


            return newView;
        }

        public long getItemId(int position) {return getItem(position).getId();}
    }

    protected void deleteChat(Message c)
    {
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    public void printCursor(Cursor c, int version){
        Log.i(ACTIVITY_NAME , "Version: " + db.getVersion());
        Log.i(ACTIVITY_NAME , "Number of columns: " + c.getColumnCount());
        for (int x = 0 ; x < c.getColumnCount(); x++) {
            Log.i(ACTIVITY_NAME, "Name of columns: " + c.getColumnName(x).toString());
        }
        Log.i(ACTIVITY_NAME , "Number of results: " + c.getCount());
        for (int x = 0 ; x < c.getCount(); x++){
            c.moveToPosition(x);
            Log.i(ACTIVITY_NAME , "Message  " + x +":"+ c.getString(c.getColumnIndex(DatabaseHelper.COL_CHAT )) + " issend:");
            if(c.getInt(c.getColumnIndex(DatabaseHelper.COL_SENT)) == 1){
                Log.i(ACTIVITY_NAME,"True");
            }
            else {
                Log.i(ACTIVITY_NAME,"False");
            }
        }
        c.moveToFirst();


    }


}





