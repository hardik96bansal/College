package com.example.hardik.colleges;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Hardik on 11/14/2016.
 */
public class ReviewList extends ArrayAdapter<String> {

    private String[] name;
    private String[] address;
    private Activity context;

    public ReviewList(Activity context,String[] name,String[] address){
        super(context,R.layout.review,name);
        this.context=context;
        this.name=name;
        this.address=address;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inf=context.getLayoutInflater();
        View listViewItem=inf.inflate(R.layout.review,null,true);
        TextView tname=(TextView) listViewItem.findViewById(R.id.rev_name);
        TextView tadd=(TextView) listViewItem.findViewById(R.id.rev_review);

        tname.setText(name[position]);
        tadd.setText(address[position]);

        return listViewItem;
    }
}
