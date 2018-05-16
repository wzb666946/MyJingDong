package com.example.com.myjingdong.myclass.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.com.myjingdong.R;
import com.example.com.myjingdong.click.OnItreamClickListener;
import com.example.com.myjingdong.home.bean.CatagoryBean;

import java.util.ArrayList;
import java.util.List;

public class Left_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater layoutInflater;
    private Context context;
    private List<CatagoryBean.DataBean> list;
    private List<Boolean> isClicks;
    private OnItreamClickListener onItreamClickListener;
    public Left_Adapter(Context context, List<CatagoryBean.DataBean> list) {
        layoutInflater = layoutInflater.from(context);
        this.context = context;
        this.list = list;
        isClicks = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            if(i==0){
                isClicks.add(true);
            }
            isClicks.add(false);
        }
    }
    public void setOnItemClickListener(OnItreamClickListener onItemClickListener){
        this.onItreamClickListener=onItemClickListener;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载条目布局
        View view=layoutInflater.inflate(R.layout.left,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        //设置数据
        viewHolder.left_text.setText(list.get(position).getName());
        //判断是否选中
        if(isClicks.get(position)){
            viewHolder.left_text.setTextColor(Color.RED);
        }else{
            viewHolder.left_text.setTextColor(Color.BLACK);
        }
        //设置点击
        viewHolder.left_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItreamClickListener!=null){
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();
                    onItreamClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView left_text;
        private final LinearLayout left_view;

        public ViewHolder(View itemView) {
            super(itemView);
            left_view=itemView.findViewById(R.id.left_view);
            left_text=itemView.findViewById(R.id.left_text);
        }
    }
}
