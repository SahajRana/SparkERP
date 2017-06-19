package com.students.spark.androsoft.spark.adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.students.spark.androsoft.spark.R;
import com.students.spark.androsoft.spark.drawer.InformationReclyclerDrawer;

import java.util.Collections;
import java.util.List;


/**
 * Created by Sahaj on 2/7/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private LayoutInflater inflator;

    List<InformationReclyclerDrawer> data= Collections.EMPTY_LIST;
    public Context context;
    public ClickListener mClickListener;
    public View myBackground;
    public SparseBooleanArray selectedItems;
    public static int selected_position=0;

    public RecyclerAdapter(Context context,List<InformationReclyclerDrawer> data){
        inflator = LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {                        //Creates new viewHolder to represent the item
        View view=inflator.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        InformationReclyclerDrawer current= data.get(position);
        holder.titlea.setText(current.title);
        holder.icona.setImageResource(current.iconID);
        if(selected_position == position){
            // Here I am just highlighting the background
            holder.titlea.setTextColor(Color.parseColor("#434343"));
            holder.itemView.setBackgroundResource(R.color.navi_drawer_on_selection);
        }else{
            holder.titlea.setTextColor(Color.parseColor("#545454"));
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }
    public interface ClickListener{
        public  void itemClicked(View view,int position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ViewPager mViewPager;
        TextView titlea;
        ImageView icona;
        //public View myBackground=new View(context,drawer_list_selector,R.id.myBackground);


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
             mViewPager = (ViewPager) itemView.findViewById(R.id.pager);
            titlea=(TextView)itemView.findViewById(R.id.listText);
            icona=(ImageView)itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener!=null){
                notifyItemChanged(selected_position);
                selected_position = getAdapterPosition();
                notifyItemChanged(selected_position);
                mClickListener.itemClicked(v,getAdapterPosition());
            }

        }
    }

}