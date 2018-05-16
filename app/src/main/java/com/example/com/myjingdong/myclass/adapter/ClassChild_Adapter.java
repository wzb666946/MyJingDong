package com.example.com.myjingdong.myclass.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.click.OnItreamClickListener;
import com.example.com.myjingdong.inter.OnItemClickListenner;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ClassChild_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater layoutInflater;
    private Context context;
    private List<ChildBean.DataBean> list;
    private List<Boolean> isClicks;
    private OnListItemClickListener onListItemClickListener;
    public ClassChild_Adapter(Context context, List<ChildBean.DataBean> list) {
        layoutInflater = layoutInflater.from(context);
        this.context = context;
        this.list = list;
        isClicks = new ArrayList<>();
    }
    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }
    public interface OnListItemClickListener{
        void OnItemClick(ChildBean.DataBean dataBean);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载条目布局
        View view=layoutInflater.inflate(R.layout.class_right_childlist,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        String s = list.get(position).getImages();
        String[] url = s.split("\\|");
        viewHolder.img.setImageURI(url[1]);
        //设置数据
        viewHolder.text.setText(list.get(position).getSubhead());
        viewHolder.price_yj.setText("原价"+list.get(position).getPrice());
        viewHolder.price_yj.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        final ChildBean.DataBean databean = list.get(position);
        viewHolder.price.setText("优惠价"+list.get(position).getBargainPrice());
        //设置点击
        viewHolder.class_right_childlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onListItemClickListener!=null){
                    onListItemClickListener.OnItemClick(databean);
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
        private final TextView price;
        private final LinearLayout class_right_childlist;
        private final TextView price_yj;

        public ViewHolder(View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.classchild_price);
            img=itemView.findViewById(R.id.claschild_sdv);
            text=itemView.findViewById(R.id.classchild_title);
            price_yj=itemView.findViewById(R.id.classchild_price_yj);
            class_right_childlist=itemView.findViewById(R.id.class_right_childlist);
        }
    }
    public  void refresh(List<ChildBean.DataBean> temlist){
        this.list.clear();
        this.list.addAll(temlist);
    }
    //加载更多
    public  void loadMore(List<ChildBean.DataBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
