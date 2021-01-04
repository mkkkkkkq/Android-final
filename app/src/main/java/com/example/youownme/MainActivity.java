package com.example.youownme;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.youownme.data.Record;
import com.example.youownme.fragment.HomeFragment;
import com.example.youownme.fragment.ReceiveFragment;
import com.example.youownme.fragment.SendFragment;
import com.example.youownme.listener.AddListen;


public class MainActivity extends AppCompatActivity {
    //toolbar
    private Toolbar myToolbar;
    private TextView myToolbar_title;
    private ImageView myToolbar_add;
    private int flag=1;
    //3个fragment对象
    private HomeFragment homeFragment;
    private SendFragment sendFragment;
    private ReceiveFragment receiveFragment;
    //3个切换界面按钮
    private TextView tv_send;
    private TextView tv_receive;
    private RelativeLayout rl_home;
    private ImageView im_home;
    //添加记录按钮
    private ImageView im_add;
    private ImageView im_receive;
    private ImageView im_send;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initView();
        showHomeFragment();
        initButtonListen();
    }

    //状态栏
    private void initToolbar(){
        myToolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        myToolbar_title=findViewById(R.id.mytoolbar_title);
        myToolbar_add=findViewById(R.id.image_toolbar_add);
        myToolbar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "未定义功能(状态栏的“+”号)", Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(){
        //页面切换：随礼按钮以及响应
        tv_send=findViewById(R.id.menu_text_send);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                tv_receive.setTextColor(getResources().getColor(R.color.colorDarkGray));
                tv_send.setTextColor(getResources().getColor(R.color.colorMenuClick));
                showSendFragment();
            }
        });
        //页面切换：首页按钮以及响应
        rl_home=findViewById(R.id.menu_rl_home);
        rl_home.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                tv_receive.setTextColor(getResources().getColor(R.color.colorDarkGray));
                tv_send.setTextColor(getResources().getColor(R.color.colorDarkGray));
                showHomeFragment();
            }
        });
        im_home=findViewById(R.id.menu_image_home);
        im_home.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                tv_receive.setTextColor(getResources().getColor(R.color.colorDarkGray));
                tv_send.setTextColor(getResources().getColor(R.color.colorDarkGray));
                showHomeFragment();
            }
        });
        //页面切换：收礼按钮以及响应
        tv_receive=findViewById(R.id.menu_text_receive);
        tv_receive.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                tv_receive.setTextColor(getResources().getColor(R.color.colorMenuClick));
                tv_send.setTextColor(getResources().getColor(R.color.colorDarkGray));
                showReceiveFragment();
            }
        });
    }
    //响应添加记录的按钮
    private void initButtonListen(){
        im_add=findViewById(R.id.image_add_icon);
        im_receive=findViewById(R.id.image_receive_icon);
        im_send=findViewById(R.id.image_send_icon);

        AddListen addListen = new AddListen();
        addListen.initView(im_add,im_receive,im_send);
        im_add.setOnClickListener(addListen);
        //响应添加收礼记录的按钮
        im_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出对话框输入信息
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View  viewDialog=inflater.inflate(R.layout.dialog_add_receive_record,null);
                final EditText name=(EditText) viewDialog.findViewById(R.id.edit_receive_name);
                final EditText money=(EditText)viewDialog.findViewById(R.id.edit_receive_money);
                final EditText reason=(EditText)viewDialog.findViewById(R.id.edit_receive_reason);
                final DatePicker date=(DatePicker) viewDialog.findViewById(R.id.dp_receive_data);
                final String[] reasonStr = new String[1];
                //收礼理由的选择对话框
                reason.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String [] s={"结婚大喜","新造华堂","金榜题名","生日快乐","其他"};
                        final String[] sReason = {"其他"};
                        final AlertDialog.Builder builderChooseReason = new AlertDialog.Builder(MainActivity.this);
                        builderChooseReason.setSingleChoiceItems(s, 4, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sReason[0] =s[which];
                            }
                        });
                        builderChooseReason.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reasonStr[0] = sReason[0];
                                reason.setText(reasonStr[0]);
                            }
                        });
                        builderChooseReason.create().show();
                    }
                });
                builder.setView(viewDialog);
                builder.setTitle("收礼");
                //信信息填写完毕后按“OK”按钮的响应
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Record record = new Record();
                        record.setTime(date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDayOfMonth());
                        if(name.getText().toString().equals(""))
                            record.setName("未知");
                        else
                            record.setName(name.getText().toString());
                        if(reason.getText().toString().equals(""))
                            record.setReason("其他");
                        else
                            record.setReason(reason.getText().toString());
                        if(money.getEditableText().toString().equals(""))
                            record.setMoney((float) 0.01);
                        else
                            record.setMoney(Float.parseFloat(money.getEditableText().toString()));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data",record);
                        receiveFragment.setArguments(bundle);
                        //刷新收礼页面的列表
                        receiveFragment.updateList();
                        //刷新首页下方的列表
                        homeFragment.listviewshow(date.getYear(),date.getMonth(),date.getDayOfMonth());
                    }
                });
                builder.setNegativeButton("Cancel",null);//点击取消
                builder.create().show();//显示dialog的布局
                im_receive.setVisibility(View.GONE);
                im_send.setVisibility(View.GONE);
            }
        });
        //响应添加随礼记录的按钮
        im_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出对话框输入信息
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View viewDialog=inflater.inflate(R.layout.dialog_add_send_record,null);
                final EditText name=(EditText) viewDialog.findViewById(R.id.edit_send_name);
                final EditText money=(EditText)viewDialog.findViewById(R.id.edit_send_money);
                final EditText reason=(EditText)viewDialog.findViewById(R.id.edit_send_reason);
                final DatePicker date=(DatePicker) viewDialog.findViewById(R.id.dp_send_data);
                builder.setView(viewDialog);
                builder.setTitle("随礼");
                //信息填写完毕后按“OK”按钮的响应
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Record record = new Record();
                        record.setTime(date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDayOfMonth());
                        if(name.getText().toString().equals(""))
                            record.setName("未知");
                        else
                            record.setName(name.getText().toString());
                        if(reason.getText().toString().equals(""))
                            record.setReason("无");
                        else
                            record.setReason(reason.getText().toString());
                        if(money.getEditableText().toString().equals(""))
                            record.setMoney((float) 0.01);
                        else
                            record.setMoney(Float.parseFloat(money.getEditableText().toString()));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data",record);
                        sendFragment.setArguments(bundle);
                        //刷新随礼页面的列表
                        sendFragment.addUpdateList();
                        //刷新首页下方的列表
                        homeFragment.listviewshow(date.getYear(),date.getMonth(),date.getDayOfMonth());
                    }
                });
                builder.setNegativeButton("Cancel",null);//点击取消
                builder.create().show();//显示dialog的布局
                im_receive.setVisibility(View.GONE);
                im_send.setVisibility(View.GONE);
            }
        });
    }

    //隐藏全部fragment
    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(null != sendFragment)
            fragmentTransaction.hide(sendFragment);
        if(null != homeFragment)
            fragmentTransaction.hide(homeFragment);
        if(null != receiveFragment)
            fragmentTransaction.hide(receiveFragment);
    }
    //显示随礼fragment
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showSendFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if( null == sendFragment){
            sendFragment=new SendFragment();
            fragmentTransaction.add(R.id.fl,sendFragment);
        }
        hideFragment(fragmentTransaction);
        fragmentTransaction.show(sendFragment);
        fragmentTransaction.commit();

        flag=0;
        invalidateOptionsMenu();
        myToolbar_title.setText("随礼");
    }
    //显示首页fragment
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showHomeFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if( null == sendFragment){
            sendFragment=new SendFragment();
            fragmentTransaction.add(R.id.fl,sendFragment);
        }
        if( null == receiveFragment){
            receiveFragment=new ReceiveFragment();
            fragmentTransaction.add(R.id.fl,receiveFragment);
        }
        if( null == homeFragment){
            homeFragment=new HomeFragment();
            fragmentTransaction.add(R.id.fl,homeFragment);
        }
        hideFragment(fragmentTransaction);
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();

        flag=1;
        invalidateOptionsMenu();
        myToolbar_title.setText("主页");
    }
    //显示收礼fragment
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showReceiveFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if( null == receiveFragment){
            receiveFragment=new ReceiveFragment();
            fragmentTransaction.add(R.id.fl,receiveFragment);
        }
        hideFragment(fragmentTransaction);
        fragmentTransaction.show(receiveFragment);
        fragmentTransaction.commit();

        flag=0;
        invalidateOptionsMenu();
        myToolbar_title.setText("收礼>");
    }

    //状态栏中的选项栏菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mytoolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch(flag){
            case 0:
                menu.findItem(R.id.mytoolbar_statistics).setVisible(false);
                menu.findItem(R.id.mytoolbar_search).setVisible(true);
                break;
            case 1:
                menu.findItem(R.id.mytoolbar_statistics).setVisible(true);
                menu.findItem(R.id.mytoolbar_search).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mytoolbar_search: {
                Toast.makeText(this, "暂时未定义功能(搜索)", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.mytoolbar_statistics: {
                Toast.makeText(this, "暂未定义功能", Toast.LENGTH_LONG).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}