package com.jnu.youownme.dataprocessor;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OutDataBank {

        private ArrayList<Person> arrayListOut=new ArrayList<>();
        private Context context;
        private final String OUT_FILE_NAME="out.txt";
        public OutDataBank(Context context)
        {
            this.context=context;
        }
        public ArrayList<Person> getOut() {
            return arrayListOut;
        }


        public void Save()
        {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(context.openFileOutput(OUT_FILE_NAME,Context.MODE_PRIVATE));
                oos.writeObject(arrayListOut);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void Load()
        {
            ObjectInputStream ois = null;
            arrayListOut=new ArrayList<>();
            try {
                ois = new ObjectInputStream(context.openFileInput(OUT_FILE_NAME));
                arrayListOut = (ArrayList<Person>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
