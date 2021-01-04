package com.example.youownme.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.youownme.BrowseMainActivity;
import com.example.youownme.R;
import com.example.youownme.adapter.SendExpandListAdapter;
import com.example.youownme.data.Record;
import com.example.youownme.data.SendSaver;

import java.util.ArrayList;

public class SendFragment extends Fragment {
    private SendExpandListAdapter sendAdapter;
    private SendSaver sendSaver;

    private ArrayList<Record> eachYearList ;
    private ExpandableListView listViewSend;
    public SendFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_send, container, false);
        initData();
        initView(view);
        return view;
    }
    private void initData(){
        sendSaver = new SendSaver(this.getContext());
        sendSaver.sendLoad();
    }

    private void initView(View view){
        //对列表设置适配器
        listViewSend=view.findViewById(R.id.listview_send);
        listViewSend.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String year= (String)sendSaver.getYearList().get(groupPosition);
                eachYearList = sendSaver.getEachYearList(year);
                Intent intent = new Intent(SendFragment.this.getContext(), BrowseMainActivity.class);
                intent.putExtra("send_data",eachYearList.get(childPosition));
                intent.putExtra("send_position",sendSaver.getOldPosition(eachYearList.get(childPosition)));
                startActivityForResult(intent,1);
                return true;
            }
        });
        sendAdapter= new SendExpandListAdapter(SendFragment.this.getContext(), sendSaver.getYearList());
        listViewSend.setAdapter(sendAdapter);
        for(int i = 0;i<sendAdapter.getGroupCount();i++)
            listViewSend.expandGroup(i);
    }
    //页面跳转结束后
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1: {//修改
                if (101 == resultCode) {
                    int pos = data.getIntExtra("change_send_position", 0);
                    Record mid = (Record) data.getSerializableExtra("change_send_data");
                    sendSaver.getArrayListSend().get(pos).setReason(mid.getReason());
                    sendSaver.getArrayListSend().get(pos).setName(mid.getName());
                    sendSaver.getArrayListSend().get(pos).setMoney(mid.getMoney());
                    sendSaver.getArrayListSend().get(pos).setTime(mid.getTime());
                    sendSaver.sendSave();
                    //出现toast
                    Toast.makeText(SendFragment.this.getContext(), "修改成功", Toast.LENGTH_LONG).show();
                    refresh();
                }
                //删除
                else if (102 == resultCode) {
                    sendSaver.getArrayListSend().remove(data.getIntExtra("delete_send_position", 0));
                    sendSaver.sendSave();
                    //出现toast
                    Toast.makeText(SendFragment.this.getContext(), "删除成功", Toast.LENGTH_LONG).show();
                    refresh();
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void addUpdateList(){
        Bundle bundle = this.getArguments();
        sendSaver.getArrayListSend().add((Record) bundle.getSerializable("data"));
        sendSaver.sendSave();
        refresh();

    }
    public void refresh(){
        sendAdapter = new SendExpandListAdapter(SendFragment.this.getContext(), sendSaver.getYearList());
        listViewSend.setAdapter(sendAdapter);
        for(int i = 0;i<sendAdapter.getGroupCount();i++)
            listViewSend.expandGroup(i);
    }
}

