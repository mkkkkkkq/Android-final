package com.example.youownme.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ReceiveSaver {
    private static final String RECEIVE_FILE_NAME ="receive.txt" ;
    private Context context;
    private ArrayList<Record> arrayListReceive=new ArrayList();

    public ArrayList<Record> getArrayListReceive() {
        return arrayListReceive;
    }
    public ReceiveSaver(Context context){
        this.context=context;
    }

    public void receiveSave(){
        ObjectOutputStream oos = null;
        mySort();
        try {
            //MODE_PRIVATE：默认操作模式，写入内容会覆盖原文件内容
            oos = new ObjectOutputStream(context.openFileOutput(RECEIVE_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListReceive);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receiveLoad(){
        ObjectInputStream ois = null;
        arrayListReceive=new ArrayList<>();
        try {
            ois=new ObjectInputStream(context.openFileInput(RECEIVE_FILE_NAME));
            arrayListReceive=(ArrayList<Record>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Record> getEachReasonList(String reason){
        ArrayList<Record> a = new ArrayList<>();
        for(int i=0 ;i<arrayListReceive.size();i++)
            if(arrayListReceive.get(i).getReason().equals(reason)){
                a.add(arrayListReceive.get(i));
            }
        return a;
    }
    public int getOldPosition(Record record){
        int p=0;
        for(int i=0;i<arrayListReceive.size();i++){
            if(arrayListReceive.get(i).equals(record))
                p=i;
        }
        return p;
    }
    public void mySort(){
        Collections.sort(arrayListReceive, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                String date1=o1.getTime();
                String date2=o2.getTime();
                String[] a=new String[3];
                String[] b=new String[3];
                a = date1.split("/");
                b = date2.split("/");
                if(Integer.parseInt(a[0]) > Integer.parseInt(b[0]))
                    return 1;
                else if(Integer.parseInt(a[0]) < Integer.parseInt(b[0]))
                    return -1;
                else{
                    if(Integer.parseInt(a[1]) > Integer.parseInt(b[1]))
                        return 1;
                    else if(Integer.parseInt(a[1]) < Integer.parseInt(b[1]))
                        return -1;
                    else {
                        if(Integer.parseInt(a[2]) > Integer.parseInt(b[2]))
                            return 1;
                        else
                            return -1;
                    }
                }
            }
        });
    }
}
