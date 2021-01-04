package com.example.youownme.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.youownme.R;
import com.example.youownme.data.Record;
import com.example.youownme.data.SendSaver;

import java.util.ArrayList;
import java.util.List;

public class SendExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> list;

    private SendSaver sendSaver;
    private ArrayList<Record> eachYearList ;

    public SendExpandListAdapter(Context context, List<String> list) {
        this.context=context;
        this.list=list;
        sendSaver = new SendSaver(context);
        sendSaver.sendLoad();
    }

    //组数（String）的数量
    @Override
    public int getGroupCount() {
        return list.size();
    }

    //每一组的子列表数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return sendSaver.getEachYearList(list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String year= (String)sendSaver.getYearList().get(groupPosition);
        eachYearList = sendSaver.getEachYearList(year);
        View view;
        if(null==convertView){
            view= View.inflate(context,R.layout.list_item_send_year,null);
        }
        else{
            view=convertView;
        }
        //布局中的图片加载为此时book对象的图片id
        ((TextView)view.findViewById(R.id.text_send_year)).setText(year+"年");
        ((TextView)view.findViewById(R.id.text_send_year_num)).setText(String.valueOf(eachYearList.size()));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String year= (String)sendSaver.getYearList().get(groupPosition);
        eachYearList = sendSaver.getEachYearList(year);
        //获取集合或数组对应的位置（position）的对象
        Record record= (Record) eachYearList.get(childPosition);
        View view;
        if(null==convertView){
            view= View.inflate(context,R.layout.list_item_record,null);
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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private String reasonControl(String reason) {
        if(reason.length() > 10)
            reason=reason.substring(0,10)+"...";
        return reason;
    }

    private String moneyToString(float money){
        String str = Float.toString(money);
        String[] a;

        if(str.contains(".") == false) {
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
