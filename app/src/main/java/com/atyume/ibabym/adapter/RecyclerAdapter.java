package com.atyume.ibabym.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;

import com.atyume.ibabym.utils.MyLiveList;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private List<MyLiveList> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;



    private OnMyItemClickListener listener;
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(View v, int pos);
        void mLongClick(View v,int pos);
    }

    public RecyclerAdapter(Context mContext,List<MyLiveList> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 绑定布局文件，返回一个viewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.user_view_miao_item,null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    /**
     * 填充onCreaterViewHolder方法中返回对holder中对控件
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.textView_Title.setText((mDatas.get(position)).getTitle());
        holder.textView_Source.setText((mDatas.get(position)).getSource());
        if (listener!=null) {
            holder.textView_Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v,position);
                }
            });


            // set LongClick
            holder.textView_Title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.mLongClick(v,position);
                    return true;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



//     * ************添加删除增加Item的方法*************

    public void addItem(int position){
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mDatas.size());
    }

    public void onclickItem(int position){

    }
    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mDatas.size());

    }
}


/**
 * 在Holder中对控件findviewbyid
 */
class MyViewHolder extends RecyclerView.ViewHolder{

    TextView textView_Title;
    TextView textView_Source;

    public MyViewHolder(View itemView) {
        super(itemView);
        textView_Title = ((TextView) itemView.findViewById(R.id.re_title));
        textView_Source = ((TextView) itemView.findViewById(R.id.re_source));

    }

}
