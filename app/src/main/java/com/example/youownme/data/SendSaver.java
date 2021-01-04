package com.example.youownme.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SendSaver {
    private static final String SEND_FILE_NAME ="send.txt" ;
    private Context context;
    private ArrayList<Record> arrayListSend=new ArrayList();

    public ArrayList<Record> getArrayListSend() {
        return arrayListSend;
    }
    public SendSaver(Context context){
        this.context=context;
    }

    public void sendSave(){
        ObjectOutputStream oos = null;
        mySort();
        try {
            //MODE_PRIVATE：默认操作模式，写入内容会覆盖原文件内容
            oos = new ObjectOutputStream(context.openFileOutput(SEND_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListSend);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendLoad(){
        ObjectInputStream ois = null;
        arrayListSend=new ArrayList<>();
        try {
            ois=new ObjectInputStream(context.openFileInput(SEND_FILE_NAME));
            arrayListSend=(ArrayList<Record>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getYearList(){
        ArrayList<String> strList=new ArrayList<>();
        for(int i=0;i<arrayListSend.size();i++){
            if(!strList.contains(arrayListSend.get(i).getTime().substring(0,4)))
                strList.add(arrayListSend.get(i).getTime().substring(0,4));
        }
        return strList;
    }

    public ArrayList<Record> getEachYearList(String year){
        ArrayList<Record> a = new ArrayList<>();
        for(int i=0 ;i<arrayListSend.size();i++)
            if(arrayListSend.get(i).getTime().substring(0,4).equals(year)){
                a.add(arrayListSend.get(i));
            }
        return a;
    }

    public int getOldPosition(Record record){
        int p=0;
        for(int i=0;i<arrayListSend.size();i++){
            if(arrayListSend.get(i).equals(record))
                p=i;
        }
        return p;
    }

    public void mySort(){
        Collections.sort(arrayListSend, new Comparator<Record>() {
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
