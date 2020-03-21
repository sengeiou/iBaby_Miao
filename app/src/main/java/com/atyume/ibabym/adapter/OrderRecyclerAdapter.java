package com.atyume.ibabym.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.List;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.MyOrderViewHolder> {

    private List<MyOrderList> mDatas;
    private Context mContext;


    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnMyItemClickListener {
        void myClick(View v, int pos);
    }

    public OrderRecyclerAdapter(Context mContext, List<MyOrderList> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    /**
     * 绑定布局文件，返回一个viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_view_item, parent,false);
        MyOrderViewHolder viewHolder = new MyOrderViewHolder(view);
        return viewHolder;
    }


    /**
     * 填充onCreaterViewHolder方法中返回对holder中对控件
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyOrderViewHolder holder, final int position) {

        holder.textView_Title.setText((mDatas.get(position)).getTitle());
        holder.textView_IsFinish.setText((mDatas.get(position)).getIsfinish());
        holder.textView_TakeOrderTime.setText((mDatas.get(position)).getTake_Ordertime());
        holder.textView_OrderTime.setText((mDatas.get(position)).getOrderTime());
        if (listener != null) {
            holder.textView_Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * 在Holder中对控件findviewbyid
     */
    class MyOrderViewHolder extends RecyclerView.ViewHolder {

        TextView textView_Title;
        TextView textView_TakeOrderTime;
        TextView textView_OrderTime;
        TextView textView_IsFinish;

        public MyOrderViewHolder(View itemView) {
            super(itemView);
            textView_Title = ((TextView) itemView.findViewById(R.id.take_order_title));
            textView_IsFinish = ((TextView) itemView.findViewById(R.id.order_isfinish));
            textView_TakeOrderTime = ((TextView) itemView.findViewById(R.id.take_order_time));
            textView_OrderTime = ((TextView) itemView.findViewById(R.id.order_time));

        }
    }
}

