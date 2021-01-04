package com.example.youownme.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youownme.adapter.RecordListAdapter;
import com.example.youownme.R;
import com.example.youownme.data.ReceiveSaver;
import com.example.youownme.data.Record;
import com.example.youownme.data.SendSaver;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    CalendarView calendarView;
    RelativeLayout rl_receive,rl_send;
    ImageView im_send,im_receive;
    TextView tv_receive_num,tv_send_num;
    ListView lv_receive,lv_send;

    private RecordListAdapter receiveListAdapter,sendListAdapter;
    private SendSaver sendSaver;
    private ReceiveSaver receiveSaver;
    private ArrayList<Record> receiveArrayList,receiveList;
    private ArrayList<Record> sendArrayList,sendList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        initData();
        initView(view);
        initDateChange();
        return view;
    }

    private void initData(){
        receiveArrayList=new ArrayList<>();
        sendArrayList=new ArrayList<>();
        receiveList=new ArrayList<>();
        sendList=new ArrayList<>();
        sendSaver=new SendSaver(this.getContext());
        receiveSaver=new ReceiveSaver(this.getContext());
    }
    private void initView(View view){
        calendarView=view.findViewById(R.id.calendarView);
        rl_receive=view.findViewById(R.id.layout_receive_hang);
        rl_send=view.findViewById(R.id.layout_send_hang);
        im_receive=view.findViewById(R.id.image_home_open_receive);
        im_send=view.findViewById(R.id.image_home_open_send);
        tv_receive_num=view.findViewById(R.id.text_home_receive_num);
        tv_send_num=view.findViewById(R.id.text_home_send_num);
        lv_receive=view.findViewById(R.id.listview_home_receive);
        lv_send=view.findViewById(R.id.listview_home_send);

        //当天的列表显示
        Calendar calendar = Calendar.getInstance();
        listviewshow(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void initDateChange(){
        //日历选中日响应
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                listviewshow(year,month,dayOfMonth);
            }
        });
        //展开选择日期的随礼列表
        rl_send.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if(View.GONE == lv_send.getVisibility()){
                    im_send.setRotation(0);
                    lv_send.setVisibility(View.VISIBLE);
                }
                else{
                    im_send.setRotation(270);
                    lv_send.setVisibility(View.GONE);
                }
            }
        });
        //展开选择日期的收礼列表
        rl_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(View.GONE == lv_receive.getVisibility()){
                    im_receive.setRotation(0);
                    lv_receive.setVisibility(View.VISIBLE);
                }
                else{
                    im_receive.setRotation(270);
                    lv_receive.setVisibility(View.GONE);
                }
            }
        });
    }

    public void listviewshow(int year,int month,int dayOfMonth){
        receiveSaver.receiveLoad();
        receiveArrayList=receiveSaver.getArrayListReceive();
        sendSaver.sendLoad();
        sendArrayList=sendSaver.getArrayListSend();
        //查找当天的记录
        sendList=new ArrayList();
        receiveList=new ArrayList();
        String str=year+"/"+(month+1)+"/"+dayOfMonth;
        for(int i=0;i<sendArrayList.size();i++){
            if(sendArrayList.get(i).getTime().equals(str)){
                sendList.add(sendArrayList.get(i));
            }
        }
        for(int i=0;i<receiveArrayList.size();i++){
            if(receiveArrayList.get(i).getTime().equals(str)){
                receiveList.add(receiveArrayList.get(i));
            }
        }
        //列表的显示
        tv_send_num.setText(String.valueOf(sendList.size()));
        sendListAdapter=new RecordListAdapter(HomeFragment.this.getContext(),R.layout.list_item_record, sendList);
        lv_send.setAdapter(sendListAdapter);
        tv_receive_num.setText(String.valueOf(receiveList.size()));
        receiveListAdapter= new RecordListAdapter(HomeFragment.this.getContext(),R.layout.list_item_record, receiveList);
        lv_receive.setAdapter(receiveListAdapter);
    }
}