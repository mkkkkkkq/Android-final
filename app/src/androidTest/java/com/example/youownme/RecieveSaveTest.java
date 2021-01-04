package com.example.youownme;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.youownme.data.ReceiveSaver;
import com.example.youownme.fragment.HomeFragment;

import org.junit.Assert;
import org.junit.Test;

public class RecieveSaveTest {
    @Test
    public void compareDateTest(){
        Context context= ApplicationProvider.getApplicationContext();
        ReceiveSaver receiveSaver=new ReceiveSaver(context);
        String[] a=new String[3];
        a=receiveSaver.compareDate("2020/20/20","2020/20/20");
        Assert.assertEquals("2020",a[0]);
    }
}
