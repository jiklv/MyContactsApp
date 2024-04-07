package com.peacedanik.contactsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addAndEditContact(false,null,-1);
            }
        });

    }

    void addAndEditContact(final boolean isUpdate,final Contact contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView newContactTitle = view.findViewById(R.id.newContactTextView);
        final EditText firstNameEditText = view.findViewById(R.id.firstNameEditText);
        final EditText lastNameEditText = view.findViewById(R.id.lastNameEditText);
        final EditText emailEditText = view.findViewById(R.id.emailEditText);
        final EditText phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);

        newContactTitle.setText(!isUpdate ? "Add Contact":"Edit Contact");

        if(isUpdate&& contact!=null){
            firstNameEditText.setText(contact.getFirstName());
            lastNameEditText.setText(contact.getLastName());
            emailEditText.setText(contact.getEmail());
            phoneNumberEditText.setText(contact.getPhoneNumber());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(isUpdate ? "Delete" : "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {

                                    deleteContact(contact, position);
                                } else {

                                    dialogBox.cancel();

                                }

                            }
                        });
        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();


        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstNameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter first name!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(lastNameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter last name!", Toast.LENGTH_SHORT).show();
                    return;
                }  else if (TextUtils.isEmpty(emailEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter email!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(phoneNumberEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(phoneNumberEditText.getText().toString())
                        &&TextUtils.isEmpty(firstNameEditText.getText().toString())
                        && TextUtils.isEmpty(lastNameEditText.getText().toString())
                        && TextUtils.isEmpty(emailEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter fields!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if (isUpdate && contact != null) {
                    updateContact(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), emailEditText.getText().toString() ,phoneNumberEditText.getText().toString() ,position);
                } else {
                    createContact(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), emailEditText.getText().toString() ,phoneNumberEditText.getText().toString());
                }
            }
        });


    }
    private void deleteContact(Contact contact, int position) {

        contacts.remove(position);
        contactAppDatabase.getContactDao().deleteContact(contact);
        contactAdapter.notifyDataSetChanged();
    }

    private void updateContact(String firstName, String lastName, String email , String phoneNumber, int position) {

        Contact contact = contacts.get(position);

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);

        contactAppDatabase.getContactDao().updateContact(contact);

        contacts.set(position, contact);

        contactAdapter.notifyDataSetChanged();
    }

    private void createContact(String firstName, String lastName, String email , String phoneNumber) {

        long id = contactAppDatabase.getContactDao().addContact(new Contact(firstName, lastName,email,phoneNumber, 0));


        Contact contact = contactAppDatabase.getContactDao().getContact(id);

        if (contact != null) {
            contacts.add(0, contact);
            contactAdapter.notifyDataSetChanged();
        }

    }
    }
