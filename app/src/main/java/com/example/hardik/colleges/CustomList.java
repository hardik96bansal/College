package com.example.hardik.colleges;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hardik on 11/14/2016.
 */
public class CustomList extends ArrayAdapter<String> {
    private Integer[] imageid;
    private Integer[] price;
    private String[] name;
    private String[] address;
    private Activity context;

    public CustomList(Activity context,String[] name,String[] address,Integer[] imageid,Integer[] price){
        super(context,R.layout.listlayout,name);
        this.context=context;
        this.name=name;
        this.address=address;
        this.imageid=imageid;
        this.price=price;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inf=context.getLayoutInflater();
        View listViewItem=inf.inflate(R.layout.listlayout,null,true);
        TextView tname=(TextView) listViewItem.findViewById(R.id.ll_name);
        TextView tadd=(TextView) listViewItem.findViewById(R.id.ll_add);
        TextView tprice=(TextView) listViewItem.findViewById(R.id.ll_price);
        ImageView img=(ImageView) listViewItem.findViewById(R.id.main_img);

        tname.setText(name[position]);
        tadd.setText(address[position]);
        tprice.setText("Rs. "+price[position]);
        img.setImageResource(imageid[position]);

        return listViewItem;
    }
}
