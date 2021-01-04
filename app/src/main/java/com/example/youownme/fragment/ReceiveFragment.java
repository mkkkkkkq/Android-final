package com.example.youownme.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youownme.BrowseMainActivity;
import com.example.youownme.adapter.RecordListAdapter;
import com.example.youownme.R;
import com.example.youownme.adapter.ReceiveReasonMenuAdapter;
import com.example.youownme.data.Record;
import com.example.youownme.data.ReceiveSaver;

import java.util.ArrayList;

public class ReceiveFragment extends Fragment {
    private ReceiveSaver receiveSaver;

    private RecordListAdapter receiveListAdapter;
    private ListView listViewReceive;
    private ArrayList<Record> receiveShowList;

    private ReceiveReasonMenuAdapter reasonListAdapter;
    private ListView listViewReason;
    ArrayList<String> reasonList;

    private TextView tv_menu;
    private TextView tv_reason_title;
    public ReceiveFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_receive, container, false);
        initData();
        initView(view);
        initListen();
        return view;
    }

    private void initData(){
        receiveSaver =new ReceiveSaver(this.getContext());
        receiveSaver.receiveLoad();
        receiveShowList=receiveSaver.getArrayListReceive();
    }
    private void initView(View view){
        tv_menu = getActivity().findViewById(R.id.mytoolbar_title);
        tv_reason_title = view.findViewById(R.id.text_reason_title);
        listViewReceive=view.findViewById(R.id.listview_receive);
        receiveListAdapter= new RecordListAdapter(this.getContext(),R.layout.list_item_record, receiveShowList);
        listViewReceive.setAdapter(receiveListAdapter);

        reasonList = new ArrayList<>();
        reasonList.add("全部");
        reasonList.add("结婚大喜");
        reasonList.add("新造华堂");
        reasonList.add("金榜题名");
        reasonList.add("生日快乐");
        reasonList.add("其他");
        listViewReason=view.findViewById(R.id.listView_receive_reason);
        reasonListAdapter = new ReceiveReasonMenuAdapter(this.getContext(),R.layout.list_item_receive_reson, reasonList);
        listViewReason.setAdapter(reasonListAdapter);
    }
    private void initListen(){
        listViewReceive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BrowseMainActivity.class);
                intent.putExtra("send_data",receiveShowList.get(position));
                intent.putExtra("send_position",receiveSaver.getOldPosition(receiveShowList.get(position)));
                startActivityForResult(intent,2);
            }
        });
        tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_menu.getText().equals("收礼>")){
                    if(listViewReason.getVisibility() == View.GONE)
                        listViewReason.setVisibility(View.VISIBLE);
                    else
                        listViewReason.setVisibility(View.GONE);
                }
            }
        });
        listViewReason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = reasonList.get(position);
                tv_reason_title.setText(str);
                //获取需要显示的列表
                if(str.equals("全部"))
                    receiveShowList = receiveSaver.getArrayListReceive();
                else
                    receiveShowList = receiveSaver.getEachReasonList(str);
                //刷新显示的列表

                receiveListAdapter= new RecordListAdapter(ReceiveFragment.this.getContext(),R.layout.list_item_record, receiveShowList);
                listViewReceive.setAdapter(receiveListAdapter);
                //隐藏收礼原因的列表
                listViewReason.setVisibility(View.GONE);
            }
        });
    }

    //页面跳转后
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //请求码
        switch (requestCode){
            //修改
            case 2:{
                if(101 == resultCode){
                    int pos=data.getIntExtra("change_send_position",0);
                    Record mid=(Record) data.getSerializableExtra("change_send_data");
                    receiveSaver.getArrayListReceive().get(pos).setReason(mid.getReason());
                    receiveSaver.getArrayListReceive().get(pos).setName(mid.getName());
                    receiveSaver.getArrayListReceive().get(pos).setMoney(mid.getMoney());
                    receiveSaver.getArrayListReceive().get(pos).setTime(mid.getTime());
                    receiveSaver.receiveSave();
                    //出现toast
                    Toast.makeText(ReceiveFragment.this.getContext(),"修改成功",Toast.LENGTH_LONG).show();
                    //刷新显示的列表
                    refresh();
                }
                //删除
                else if(102 == resultCode){
                    receiveSaver.getArrayListReceive().remove(data.getIntExtra("delete_send_position",0));
                    receiveSaver.receiveSave();
                    //出现toast
                    Toast.makeText(ReceiveFragment.this.getContext(),"删除成功",Toast.LENGTH_LONG).show();
                    refresh();
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void updateList(){
        Bundle bundle = this.getArguments();
        receiveSaver.getArrayListReceive().add((Record) bundle.getSerializable("data"));
        receiveSaver.receiveSave();
        refresh();
    }
    public void refresh(){
        receiveListAdapter= new RecordListAdapter(ReceiveFragment.this.getContext(),R.layout.list_item_record, receiveSaver.getArrayListReceive());
        listViewReceive.setAdapter(receiveListAdapter);
        reasonListAdapter = new ReceiveReasonMenuAdapter(this.getContext(),R.layout.list_item_receive_reson, reasonList);
        listViewReason.setAdapter(reasonListAdapter);
        tv_reason_title.setText("全部");
    }
}