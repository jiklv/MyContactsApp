package com.peacedanik.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Contact.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contact> contacts = new ArrayList<>();
    MainActivity mainActivity;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView firstNameTextView;
        public TextView lastNameTextView;
        public TextView emailTextView;
        public TextView phoneNumberTextView;


        public MyViewHolder(View view) {
            super(view);
            firstNameTextView = view.findViewById(R.id.firstNameTextView);
            lastNameTextView = view.findViewById(R.id.lastNameTextView);
            emailTextView = view.findViewById(R.id.emailTextView);
            phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        }
    }
    public ContactAdapter(Context context, ArrayList<Contact> contacts, MainActivity mainActivity){
        this.contacts = contacts;
        this.context = context;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Contact contact = contacts.get(position);

        holder.firstNameTextView.setText(contact.getFirstName());
        holder.lastNameTextView.setText(contact.getLastName());
        holder.emailTextView.setText(contact.getEmail());
        holder.phoneNumberTextView.setText(contact.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                mainActivity.addAndEditContact(true, contact, position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


}
