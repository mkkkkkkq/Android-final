package com.example.youownme.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.youownme.R;
import com.example.youownme.data.ReceiveSaver;
import com.example.youownme.data.Record;
import com.example.youownme.data.SendSaver;

import java.util.ArrayList;
import java.util.List;

public class ReceiveReasonMenuAdapter extends ArrayAdapter {
    private int resourceId;
    private ReceiveSaver receiveSaver;
    private ArrayList<Record> list;

    public ReceiveReasonMenuAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        receiveSaver = new ReceiveSaver(context);
        receiveSaver.receiveLoad();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取集合或数组对应的位置（position）的对象
        String str= (String) getItem(position);
        View view;
        if(null==convertView){
            view= LayoutInflater.from(getContext()).inflate(this.resourceId,parent,false);
        }
        else{
            view=convertView;
        }
        if(str.equals("全部"))
            list = receiveSaver.getArrayListReceive();
        else
            list = receiveSaver.getEachReasonList(str);
        ((TextView)view.findViewById(R.id.text_receive_reason)).setText(str);
        ((TextView)view.findViewById(R.id.text_receive_reasonnum)).setText(String.valueOf(list.size()));
        //返回单行布局对象（已加载图片与文字）
        return view;
    }
}
