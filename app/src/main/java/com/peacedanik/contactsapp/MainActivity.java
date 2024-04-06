package com.peacedanik.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;

import java.util.ArrayList;
import Contact.Contact;
import Data.ContactAppDatabase;

public class MainActivity extends AppCompatActivity {
    private ContactAdapter contactAdapter;
    private ArrayList <Contact> contacts = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAppDatabase contactAppDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        contactAppDatabase = Room.databaseBuilder(getApplicationContext(), ContactAppDatabase.class, "ContactsDB").allowMainThreadQueries().build();

        contacts.addAll(contactAppDatabase.getContactDao().getAll());
        contactAdapter = new ContactAdapter(this,contacts,MainActivity.this);

    }
}