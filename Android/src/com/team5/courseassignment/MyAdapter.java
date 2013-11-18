package com.team5.courseassignment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class MyAdapter extends ArrayAdapter {

    private Context context;

    private List<FourSquareVenue> list;

    public MyAdapter(Context context,
            List<FourSquareVenue> venues) 
            { 
        super(context, R.layout.row);
        this.context = context;
        this.list = venues;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row,
                    null);

            convertView.setTag(holder);
            holder.string1  = (TextView) convertView
                    .findViewById(R.id.name);



            holder.string2  = (TextView) convertView
                    .findViewById(R.id.categoryId);


        }
                    else
                     {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.string1 .setText((CharSequence) list.get(position));
        holder.string2 .setText((CharSequence) list.get(position));

        return convertView;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    class ViewHolder {
        TextView string1 = null;
        TextView string2 = null;


    }

}