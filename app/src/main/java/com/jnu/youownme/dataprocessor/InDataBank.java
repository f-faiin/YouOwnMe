package com.jnu.youownme.dataprocessor;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InDataBank {
    private ArrayList<Person> arrayListIn=new ArrayList<>();
    private Context context;
    private final String IN_FILE_NAME="in.txt";
    public InDataBank(Context context)
    {
        this.context=context;
    }
    public ArrayList<Person> getIn() {
        return arrayListIn;
    }


    public void Save()
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(IN_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListIn);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
        arrayListIn=new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(IN_FILE_NAME));
            arrayListIn = (ArrayList<Person>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}