package com.example.youownme.data;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    private String name;        //对象名称
    private String reason;      //收礼理由
    private float money;        //金额
    private String time;          //时间

    public Record(){ }
    public Record(String name, String reason, float money, String time){
        setMoney(money);
        setName(name);
        setReason(reason);
        setTime(time);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public float getMoney() {
        return money;
    }
    public void setMoney(float money) {
        this.money = money;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
