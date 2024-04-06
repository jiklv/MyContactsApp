package com.peacedanik.contactsapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Contact.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contact> contacts = new ArrayList<>();
    MainActivity mainActivity;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }
    public ContactAdapter(Context context, ArrayList<Contact> contacts, MainActivity mainActivity){
    this.contacts = contacts;
    this.context = context;
    this.mainActivity = mainActivity;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
}
