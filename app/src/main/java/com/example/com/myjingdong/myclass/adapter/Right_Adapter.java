package com.example.com.myjingdong.myclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.com.myjingdong.R;
import com.example.com.myjingdong.click.OnItreamClickListener;
import com.example.com.myjingdong.myclass.bean.RightBean;
import com.example.com.myjingdong.myclass.ui.MainChildActivity;

import java.util.List;

public class Right_Adapter extends BaseExpandableListAdapter{
    private Context context;
    private List<RightBean.DataBean> list;

    public Right_Adapter(Context context, List<RightBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GruopHolder gruopHolder;
        if(convertView==null){
            convertView= LinearLayout.inflate(context, R.layout.group,null);
            gruopHolder=new GruopHolder();
            gruopHolder.textView=convertView.findViewById(R.id.group_text);
            convertView.setTag(gruopHolder);
        }else{
            gruopHolder= (GruopHolder) convertView.getTag();
        }
        //赋值
        gruopHolder.textView.setText(list.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if(convertView==null){
            convertView= LinearLayout.inflate(context,R.layout.chidl,null);
            childHolder=new ChildHolder();
            childHolder.recyclerView=convertView.findViewById(R.id.child_rc);
            convertView.setTag(childHolder);
        }else{
            childHolder= (ChildHolder) convertView.getTag();
        }
        List<RightBean.DataBean.ListBean> list2 = list.get(groupPosition).getList();
       //设置适配器
        final Child_Adapter child_adapter=new Child_Adapter(context,list2);
        childHolder.recyclerView.setAdapter(child_adapter);
        //设置布局管理器
        childHolder.recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        //设置点击事件
        child_adapter.setOnItemClickListener(new OnItreamClickListener() {
            @Override
            public void onItemClick(int position) {
                //界面跳转
                Intent intent = new Intent(context, MainChildActivity.class);
                intent.putExtra("cid", list.get(groupPosition).getList().get(childPosition).getPcid()+"");
                context.startActivity(intent);
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class GruopHolder{
        TextView textView;
    }
    class ChildHolder{
        RecyclerView recyclerView;
    }
}
