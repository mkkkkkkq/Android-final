package com.example.youownme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.youownme.R;
import com.example.youownme.data.Record;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordListAdapter extends ArrayAdapter {
    private int resourceId;
    public RecordListAdapter(@NonNull Context context, int resource, @NonNull List<Record> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取集合或数组对应的位置（position）的对象
        Record record= (Record) getItem(position);
        View view;
        if(null==convertView){
            view= LayoutInflater.from(getContext()).inflate(this.resourceId,parent,false);
        }
        else{
            view=convertView;
        }
        //布局中的图片加载为此时book对象的图片id
        ((TextView)view.findViewById(R.id.text_name)).setText(record.getName());
        ((TextView)view.findViewById(R.id.text_reason)).setText(reasonControl(record.getReason()));
        ((TextView)view.findViewById(R.id.text_money)).setText(moneyToString(record.getMoney()));
        ((TextView)view.findViewById(R.id.text_data)).setText(record.getTime());
        //返回单行布局对象（已加载图片与文字）
        return view;
    }

    private String reasonControl(String reason) {
        if(reason.length() > 10)
            reason=reason.substring(0,10)+"...";
        return reason;
    }

    private String moneyToString(float money){
        String str = Float.toString(money);
        String[] a;

        if(!str.contains(".")) {
            if(str.length()==7||str.length()==6)
                str="￥"+str;
            else if(str.length()==5)
                str="￥"+str+".0" ;
            else
                str="￥"+str+".00" ;
        }
        else{
            a = str.split("[.]");
            if(a.length < 5){
                if(0==a[1].length())
                    str="￥"+a[0]+".00";
                else if(1==a[1].length())
                    str="￥"+str+"0";
                else
                    str="￥"+a[0]+"."+a[1].substring(0,2);
            }
            else if(a.length == 5){
                str="￥"+a[0]+"."+a[1].substring(0,1);
            }
            else if(a.length > 5){
                str="￥"+a[0];
            }
        }
        return str;
    }
}
