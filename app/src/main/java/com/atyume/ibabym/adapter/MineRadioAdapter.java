package com.atyume.ibabym.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineRadioAdapter extends RecyclerView.Adapter<MineRadioAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<MyLiveList> mMyLiveList;
    private OnItemClickListener mOnItemClickListener;
    private OnMyItemClickListener listener;

    public MineRadioAdapter(Context context) {
        this.context = context;
    }


    public void notifyAdapter(List<MyLiveList> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<MyLiveList> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_live, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyLiveList myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.mTvTitle.setText(myLive.getTitle());
        holder.mTvSource.setText(myLive.getSource());
        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.mCheckBox.setVisibility(View.GONE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);

            if (myLive.isSelect()) {
                holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
            } else {
                holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
            }
        });
        holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.myClick(v,position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<MyLiveList> myLiveList);

    }

    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(View v, int pos);
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radio_img)
        ImageView mRadioImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_source)
        TextView mTvSource;
        @BindView(R.id.root_view)
        RelativeLayout mRootView;
        @BindView(R.id.check_box)
        ImageView mCheckBox;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
