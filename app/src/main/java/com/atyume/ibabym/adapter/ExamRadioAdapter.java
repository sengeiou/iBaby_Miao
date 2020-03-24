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
import com.atyume.ibabym.utils.MyExamList;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamRadioAdapter extends RecyclerView.Adapter<ExamRadioAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<MyExamList> mExamList;
    private ExamRadioAdapter.OnItemClickListener mOnItemClickListener;
    private ExamRadioAdapter.OnMyItemClickListener listener;

    public ExamRadioAdapter(Context context) {
        this.context = context;
    }


    public void notifyAdapter(List<MyExamList> myExamList, boolean isAdd) {
        if (!isAdd) {
            this.mExamList = myExamList;
        } else {
            this.mExamList.addAll(myExamList);
        }
        notifyDataSetChanged();
    }

    public List<MyExamList> getMyExamList() {
        if (mExamList == null) {
            mExamList = new ArrayList<>();
        }
        return mExamList;
    }

    @Override
    public ExamRadioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_exam, parent, false);
        ExamRadioAdapter.ViewHolder holder = new ExamRadioAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mExamList.size();
    }

    @Override
    public void onBindViewHolder(final ExamRadioAdapter.ViewHolder holder, final int position) {
        final MyExamList myExam = mExamList.get(holder.getAdapterPosition());
        holder.mTvTitle.setText(myExam.getExamName());
        holder.mTvHos.setText(myExam.getExamHos());
        holder.mTvPrice.setText(myExam.getExamPrice().toString());
        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.mCheckBox.setVisibility(View.GONE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);

            if (myExam.isSelect()) {
                holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
            } else {
                holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mExamList);
            }
        });
        holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.myClick(v,position);
            }
        });
    }

    public void setOnItemClickListener(ExamRadioAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<MyExamList> myExamList);

    }

    public void setOnMyItemClickListener(ExamRadioAdapter.OnMyItemClickListener listener){
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
        @BindView(R.id.m_examName)
        TextView mTvTitle;
        @BindView(R.id.m_examHos)
        TextView mTvHos;
        @BindView(R.id.m_examPrice)
        TextView mTvPrice;
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

