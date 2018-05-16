package com.example.com.myjingdong.myclass.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.com.myjingdong.R;
import com.example.com.myjingdong.click.OnItreamClickListener;
import com.example.com.myjingdong.myclass.bean.RightBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class Child_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater layoutInflater;
    private Context context;
    private List<RightBean.DataBean.ListBean> list;
    private List<Boolean> isClicks;
    private OnItreamClickListener onItreamClickListener;
    public Child_Adapter(Context context, List<RightBean.DataBean.ListBean> list) {
        layoutInflater = layoutInflater.from(context);
        this.context = context;
        this.list = list;
        isClicks = new ArrayList<>();
    }
    public void setOnItemClickListener(OnItreamClickListener onItemClickListener){
        this.onItreamClickListener=onItemClickListener;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载条目布局
        View view=layoutInflater.inflate(R.layout.child_view,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.img.setImageURI(list.get(position).getIcon());
        //设置数据
        viewHolder.text.setText(list.get(position).getName());
        //设置点击
        viewHolder.child_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItreamClickListener!=null){
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


        private final SimpleDraweeView img;
        private final TextView text;
        private final LinearLayout child_view;

        public ViewHolder(View itemView) {
            super(itemView);
            child_view=itemView.findViewById(R.id.child_view);
            img=itemView.findViewById(R.id.child_img);
            text=itemView.findViewById(R.id.child_text);
        }
    }
}
