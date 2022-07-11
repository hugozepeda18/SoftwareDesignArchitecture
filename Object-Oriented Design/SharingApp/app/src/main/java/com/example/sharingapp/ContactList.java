package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {
    private static ArrayList<Contact> contacts;

    public ContactList() {
        contacts = new ArrayList<Contact>();
    }

    String FILENAME = "contacts.sav";

    public void setContacts(ArrayList<Contact> contact_list){
        contacts = contact_list;
    }

    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    public ArrayList<String> getAllUsernames(){
        ArrayList<String> usernames = new ArrayList<String>();
        for (Contact i: contacts) {
            usernames.add(i.getUsername());
        }
        return usernames;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void deleteContact(Contact contact){
        for (Contact i: contacts) {
            if(i.getId() == contact.getId()){
                contacts.remove(contact.getId());
                break;
            }
        }
    }

    public Contact getContact(int index){
        return contacts.get(index);
    }

    public int getSize() {
        return contacts.size();
    }

    public int getIndex(Contact contact){
        int index = -1;
        for (Contact i: contacts) {
            index += 1;
            if(i.getId() == contact.getId()){
                break;
            }
        }
        return index;
    }

    public boolean hasContact(Contact contact){
        boolean flag = false;
        for (Contact i: contacts) {
            if(i.getId() == contact.getId()){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public Contact getContactByUsername(String username){
        for (Contact i: contacts) {
            if(i.getUsername().equals(username)){
                return i;
            }
        }
        return null;
    }

    public void loadContacts(Context context){
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type contactType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, contactType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveContacts(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username){
        boolean flag = true;
        for (Contact i: contacts) {
            if(i.getUsername().equals(username)){
                flag = false;
                break;
            }
        }
        return flag;
    }

}
