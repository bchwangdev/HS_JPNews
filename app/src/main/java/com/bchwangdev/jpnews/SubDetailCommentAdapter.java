package com.bchwangdev.jpnews;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubDetailCommentAdapter extends RecyclerView.Adapter<SubDetailCommentAdapter.ViewHolder>{

    private float mTextSize;
    //아이템리스트
    private ArrayList<mComment> mData = new ArrayList<>();

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SubDetailCommentAdapter(ArrayList<mComment> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recyclerview_sub_comment, parent, false);
        return new SubDetailCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mComment item = mData.get(position);
        Picasso.get().load(item.getImage()).into(holder.ivCommentImage);
        holder.tvCommentNickName.setText(item.getNickName());
        holder.tvCommentDate.setText(item.getDate());
        holder.tvCommentGood.setText(item.getGood());
        holder.tvCommentBad.setText(item.getBad());
        holder.tvCommentContent.setText(item.getContent());
        //★텍스트크기 변경하기
        mTextSize = SubDetailActivity.textSize;
        holder.tvCommentNickName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        holder.tvCommentDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        holder.tvCommentGood.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        holder.tvCommentBad.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        holder.tvCommentContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCommentImage;
        TextView tvCommentNickName, tvCommentDate, tvCommentGood, tvCommentBad, tvCommentContent;

        ViewHolder(final View itemView) {
            super(itemView);
            //int pos = getAdapterPosition() ; //여기서도 setOnClickListener 코드 가능
            // 뷰 객체에 대한 참조. (hold strong reference)
            ivCommentImage = itemView.findViewById(R.id.ivCommentImage);
            tvCommentNickName = itemView.findViewById(R.id.tvCommentNickName);
            tvCommentDate = itemView.findViewById(R.id.tvCommentDate);
            tvCommentGood = itemView.findViewById(R.id.tvCommentGood);
            tvCommentBad = itemView.findViewById(R.id.tvCommentBad);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
        }
    }

}
