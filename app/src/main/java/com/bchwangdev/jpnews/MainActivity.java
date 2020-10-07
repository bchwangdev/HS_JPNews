package com.bchwangdev.jpnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.AlertDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private final String URL = "https://news.yahoo.co.jp/ranking/access/video"; //파싱할 홈페이지의 URL주소
    private String newsImage, newsTitle, newsCompany, newsDate, newsDetailUrl;

    ProgressBar progressBar;
    Button btnRefresh;

    AdView mAdView;
    Toolbar toolbar;

    RecyclerView recyclerView;
    MainAdapter mAdapter;

    ArrayList<mNewsHeader> arrNewsH = new ArrayList<>();

    //툴바표시하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //툴바아이템클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnStar:
                //폴더 가져오기
//                Intent myFileIntent = new Intent(this, FolderActivity.class);
//                startActivityForResult(myFileIntent, 1);
//                //광고 표시
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                mInterstitialAd.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //▼findViewById
        progressBar = findViewById(R.id.progressBar);
        btnRefresh = findViewById(R.id.btnRefresh);

        //▼툴바
        toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        //▼인터넷연결확인
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No Internet Connection");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", null);
            alertDialogBuilder.show();
            btnRefresh.setVisibility(View.VISIBLE);
            return;
        }

        //▼광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        //▼리사이클러뷰 설정
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter(arrNewsH);
        mAdapter.setOnClickListener2(this);
        recyclerView.setAdapter(mAdapter);

        //▼데이터가져오기
        JsoupAsyncTask jsoup = new JsoupAsyncTask();
        jsoup.execute();
    }

    @Override
    public void onTitleClicked(int position) {
        Intent myDetailIntent = new Intent(this, SubDetailActivity.class);
        myDetailIntent.putExtra("newsDetailUrl", arrNewsH.get(position).getDetailUrl());
        startActivity(myDetailIntent);
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btnRefresh:
                //화면 다시 불러오기
                this.recreate();
                break;
        }
    }

    //▼크롤링 뉴스헤더 가져오기
    protected class JsoupAsyncTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            btnRefresh.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Document doc = Jsoup.connect(URL).get();
                Elements datas = doc.select(".newsFeed_item");
                for (Element elem : datas) {
                    newsImage = elem.select("img").attr("src");
                    newsTitle = elem.select(".newsFeed_item_title").text();
                    newsCompany = elem.select(".newsFeed_item_media").text();
                    newsDate = elem.select(".newsFeed_item_date").text();
                    newsDetailUrl = elem.select("a").attr("href");
                    arrNewsH.add(new mNewsHeader(newsImage, newsTitle, newsCompany, newsDate, newsDetailUrl));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
