package com.example.youownme.listener;

import android.view.View;
import android.widget.ImageView;

public class AddListen implements View.OnClickListener {
    private ImageView im_add;
    private ImageView im_receive;
    private ImageView im_send;

    public void initView(ImageView im_add, ImageView im_receive,ImageView im_send){
        this.im_add=im_add;
        this.im_receive=im_receive;
        this.im_send=im_send;
    }

    @Override
    public void onClick(View v) {
        if(View.VISIBLE==im_receive.getVisibility()){
            im_receive.setVisibility(View.GONE);
            im_send.setVisibility(View.GONE);
        }
        else{
            im_receive.setVisibility(View.VISIBLE);
            im_send.setVisibility(View.VISIBLE);
        }
    }
}