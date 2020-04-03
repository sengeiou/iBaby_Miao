package com.atyume.ibabym.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;

import com.atyume.ibabym.utils.MyLiveList;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MyLiveList> mDatas;
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
            View view = mInflater.inflate(R.layout.user_view_miao_item,null);
            MyViewHolder viewHolder = new MyViewHolder(view);
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
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (EMPTY_VIEW != itemViewType) {
            ((MyViewHolder)holder).textView_Title.setText((mDatas.get(position)).getTitle());
            ((MyViewHolder)holder).textView_Source.setText((mDatas.get(position)).getSource());
            if (listener!=null) {
                ((MyViewHolder)holder).textView_Title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v,position);
                    }
                });


                // set LongClick
                ((MyViewHolder)holder).textView_Title.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listener.mLongClick(v,position);
                        return true;
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

    /**
     * 在Holder中对控件findviewbyid
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView_Title;
        TextView textView_Source;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_Title = ((TextView) itemView.findViewById(R.id.re_title));
            textView_Source = ((TextView) itemView.findViewById(R.id.re_source));

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



