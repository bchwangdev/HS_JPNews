package com.bchwangdev.jpnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubFavoriteAdapter extends RecyclerView.Adapter<SubFavoriteAdapter.ViewHolder> {

    //아이템리스트
    private ArrayList<mNews> mData = new ArrayList<>();

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SubFavoriteAdapter(ArrayList<mNews> mData) {
        this.mData = mData;
    }

    //클릭리스너 등록
    private SubFavoriteAdapter.OnItemClickListener mListner;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public void setOnClickListener3(SubFavoriteAdapter.OnItemClickListener listener) {
        mListner = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recyclerview_main, parent, false);
        return new SubFavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        mNews item = mData.get(position);
        Picasso.get().load(item.getImage()).into(holder.ivNewsImage);
        holder.tvNewsTitle.setText(item.getTitle());
        holder.tvNewsCompany.setText(item.getDate());//여기서는 회사보다 날짜를 표시하고 싶었음
        holder.tvNewsDate.setText(item.getDate());
        if (mListner != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListner.onItemClicked(position); //타이틀 클릭했을때
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivNewsImage;
        TextView tvNewsTitle;
        TextView tvNewsCompany;
        TextView tvNewsDate;

        ViewHolder(final View itemView) {
            super(itemView);
            //int pos = getAdapterPosition() ; //여기서도 setOnClickListener 코드 가능
            // 뷰 객체에 대한 참조. (hold strong reference)
            cardView = itemView.findViewById(R.id.card_view);
            ivNewsImage = itemView.findViewById(R.id.ivNewsHImage);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsHTitle);
            tvNewsCompany = itemView.findViewById(R.id.tvNewsHCompany);
            tvNewsDate = itemView.findViewById(R.id.tvNewsHDate);
        }
    }
}
