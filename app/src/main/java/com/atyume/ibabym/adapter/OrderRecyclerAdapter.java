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

public class OrderRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyOrderList> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    private OnMyItemClickListener listener;

    /**
     * 空数据时，显示空布局类型
     */
    private final int EMPTY_VIEW = 1;

    /**
     * 控制空布局的显隐
     */
    private int mEmptyType = 0;

    /**
     * 有项目视图的点击
     */
    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnMyItemClickListener {
        void myClick(View v, int pos);

    }


    public OrderRecyclerAdapter(Context mContext, List<MyOrderList> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        if(mDatas == null|| mDatas.isEmpty()){
            mEmptyType = 1;
        }
        else{
            mEmptyType = 0;
        }

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemViewType = getItemViewType(viewType);
        if (EMPTY_VIEW != itemViewType){
            View view = mInflater.inflate(R.layout.order_view_item, null);
            MyOrderViewHolder viewHolder = new MyOrderViewHolder(view);
            return viewHolder;
        }
        else{
            View view = mInflater.inflate(R.layout.empty_view, null);
            EmptyViewHolder viewHolder = new EmptyViewHolder(view);
            return viewHolder;
        }
    }


    /**
     * 填充onCreaterViewHolder方法中返回对holder中对控件
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (EMPTY_VIEW != itemViewType) {
            ((MyOrderViewHolder)holder).textView_Title.setText((mDatas.get(position)).getTitle());
            ((MyOrderViewHolder)holder).textView_IsFinish.setText((mDatas.get(position)).getIsfinish());
            ((MyOrderViewHolder)holder).textView_TakeOrderTime.setText((mDatas.get(position)).getTake_Ordertime());
            ((MyOrderViewHolder)holder).textView_OrderTime.setText((mDatas.get(position)).getOrderTime());
            if (listener != null) {
                ((MyOrderViewHolder)holder).textView_Title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v, position);
                    }
                });
            }
        }
        else {
            //空视图布局 - 点击事件的回调
            ((EmptyViewHolder) holder).mEmptyTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        return mDatas.size();
        return mDatas != null ? mDatas.size() + mEmptyType : mEmptyType;
    }

    @Override
    public int getItemViewType(int position) {
        if (mEmptyType == EMPTY_VIEW) {
            //空布局的类型
            return EMPTY_VIEW;
        }
        return 0;
    }

    /**
            * 在Holder中对控件findviewbyid
    */
    public class MyOrderViewHolder extends RecyclerView.ViewHolder {

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
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView mEmptyTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            mEmptyTextView = (TextView) itemView.findViewById(R.id.empty_View);
        }
    }
}



