package com.bchwangdev.jpnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class SubFavoriteActivity extends AppCompatActivity implements SubFavoriteAdapter.OnItemClickListener{

    ArrayList<mNews> items = new ArrayList<>();

    //데이터베이스
    mNewsDatabase db;

    Toolbar toolbar;
    AdView adView3;

    RecyclerView recyclerView;
    SubFavoriteAdapter adapter;

    //툴바표시하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //툴바아이템클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_favorite);

        db = Room.databaseBuilder(this, mNewsDatabase.class, "news").allowMainThreadQueries().build();
        items = new ArrayList<mNews>(db.mNewsDao().getAll());

        //▼데이터가 없으면 알러트 띄우고 돌려보내기
        if (items.size() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No Data");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    return;
                }
            });
            alertDialogBuilder.show();
        }

        //▼광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView3 = findViewById(R.id.adView3);
        adView3.loadAd(adRequest);

        //▼툴바
        toolbar = findViewById(R.id.toolbar3);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("DataBase");

        //▼리사이클러뷰 설정 - 댓글
        recyclerView = findViewById(R.id.recyclerViewFavorite);
        recyclerView.setHasFixedSize(true);//옵션
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //어답터 세팅
        adapter = new SubFavoriteAdapter(items);
        adapter.setOnClickListener3(this);
        //스와이프 삭제
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

    }


    //▼스와이프 옆으로 밀어서 삭제하기
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            db.mNewsDao().delete(items.get(viewHolder.getAdapterPosition()).getId());
            items.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onItemClicked(int position) {
//        Intent myDetailIntent = new Intent(this, SubDetailActivity.class);
//        myDetailIntent.putExtra("newsDetailUrl", items.get(position).getDetailUrl());
//        startActivity(myDetailIntent);
    }

}
