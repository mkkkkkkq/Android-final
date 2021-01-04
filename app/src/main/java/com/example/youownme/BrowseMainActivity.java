package com.example.youownme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youownme.data.Record;

public class BrowseMainActivity extends AppCompatActivity {
    Record record;
    int position;
    TextView tv_name,tv_money,tv_date,tv_reason;
    TextView bt_delete,bt_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_main);

        record=(Record) getIntent().getSerializableExtra("send_data");
        position=getIntent().getIntExtra("send_position",0);
        initView();
    }

    private void initView(){
        tv_date=findViewById(R.id.record_data);
        tv_money=findViewById(R.id.record_money);
        tv_name=findViewById(R.id.record_name);
        tv_reason=findViewById(R.id.record_reason);
        bt_change=findViewById(R.id.button_change);
        bt_delete=findViewById(R.id.button_delete);

        tv_reason.setText(record.getReason());
        tv_name.setText(record.getName());
        tv_money.setText(moneyToString(record.getMoney()));
        tv_date.setText(record.getTime());

        //修改
        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(BrowseMainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(BrowseMainActivity.this);
                View viewDialog=inflater.inflate(R.layout.dialog_add_send_record,null);
                final EditText name=(EditText) viewDialog.findViewById(R.id.edit_send_name);
                final EditText money=(EditText)viewDialog.findViewById(R.id.edit_send_money);
                final EditText reason=(EditText)viewDialog.findViewById(R.id.edit_send_reason);
                final DatePicker date=(DatePicker) viewDialog.findViewById(R.id.dp_send_data);
                name.setText(record.getName());
                money.setText(moneyToString(record.getMoney()).substring(1));
                reason.setText(record.getReason());
                builder.setTitle("修改");
                builder.setView(viewDialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Record record=new Record(name.getText().toString()
                                , reason.getText().toString(),Float.parseFloat(money.getEditableText().toString())
                                ,date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDayOfMonth());
                        Intent intent =new Intent();
                        intent.putExtra("change_send_data",record);
                        intent.putExtra("change_send_position",position);
                        setResult(101,intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel",null);//点击取消
                builder.create().show();//显示dialog的布局
            }
        });
        //删除
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //构造消息对话框
                androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(BrowseMainActivity.this);
                builder.setTitle("询问");
                builder.setMessage("您确定要删除这条吗？");
                builder.setCancelable(true);    //是否能取消
                //正面的按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent();
                        intent.putExtra("delete_send_position",position);
                        setResult(102,intent);
                        finish();
                    }
                });
                //反面的按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                //创建以及显示对话框
                builder.create().show();
            }
        });
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
            else if(a.length == 6){
                str="￥"+a[0];
            }
        }
        return str;
    }
}