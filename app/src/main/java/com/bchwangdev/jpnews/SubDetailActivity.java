package com.bchwangdev.jpnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SubDetailActivity extends AppCompatActivity {
    String detailUrl, commentUrl, iFrameUrl, iFUrlTopicId, iFUrlFullPageUrl, iFUrlSpaceId;
    String strNewsDImage, strNewsDTitle, strNewsDContent, strNewsDDate, strNewsDCompany;
    float textSize;

    TextView tvNewsDTitle, tvNewsDContent, tvNewsDDate, tvNewsDCompany;
    TextView tvCommentNickName,tvCommentDate,tvCommentContent,tvCommentGood,tvCommentBad;
    ImageView ivNewsDImage;

    AdView mAdView;
    Toolbar toolbar;

    RecyclerView recyclerView;
    SubDetailCommentAdapter sAdapter;

    ArrayList<mComment> arrComment = new ArrayList<>();

    //툴바표시하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    //툴바아이템클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "뒤로 가기", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.btnTextSizeUp:
//            case R.id.btnTextSizeDown:
//                if (item.getItemId() == R.id.btnTextSizeUp) {
//                    tvNewsDTitle.setTextSize(tvNewsDTitle.getTextSize() + 1);
//                    textSize = tvNewsDContent.getTextSize() + 1;
//                } else {
//                    tvNewsDTitle.setTextSize(tvNewsDTitle.getTextSize() - 1);
//                    textSize = tvNewsDContent.getTextSize() - 1;
//                }
//                tvNewsDContent.setTextSize(textSize);
//                tvNewsDDate.setTextSize(textSize);
//                tvNewsDCompany.setTextSize(textSize);
//                tvCommentNickName.setTextSize(textSize);
//                tvCommentDate.setTextSize(textSize);
//                tvCommentContent.setTextSize(textSize);
//                tvCommentGood.setTextSize(textSize);
//                tvCommentBad.setTextSize(textSize);
//                break;
            case R.id.btnStar:
                //★데이터베이스
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
        setContentView(R.layout.activity_sub_detail);

        ivNewsDImage = findViewById(R.id.ivNewsDImage);
        tvNewsDTitle = findViewById(R.id.tvNewsDTitle);
        tvNewsDContent = findViewById(R.id.tvNewsDContent);
        tvNewsDDate = findViewById(R.id.tvNewsDDate);
        tvNewsDCompany = findViewById(R.id.tvNewsDCompany);
        tvCommentNickName = findViewById(R.id.tvCommentNickName);
        tvCommentDate = findViewById(R.id.tvCommentDate);
        tvCommentContent = findViewById(R.id.tvCommentContent);
        tvCommentGood = findViewById(R.id.tvCommentGood);
        tvCommentBad = findViewById(R.id.tvCommentBad);

        //광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        //툴바
        toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("");

        //인텐트 가져오기
        Intent intent = getIntent();
        detailUrl = intent.getStringExtra("newsDetailUrl");

        //리사이클러뷰 설정
        recyclerView = findViewById(R.id.recyclerViewComment);
        recyclerView.setHasFixedSize(true);//옵션
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //리스트 항목에 구분선 넣어주기
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); //색상지정없을때
        //어답터 세팅
        sAdapter = new SubDetailCommentAdapter(arrComment);
        recyclerView.setAdapter(sAdapter);

        //데이터 가져오기
        SubDetailActivity.JsoupAsyncTask jsoup = new SubDetailActivity.JsoupAsyncTask();
        jsoup.execute();
    }

    //크롤링 뉴스헤더 가져오기
    protected class JsoupAsyncTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Picasso.get().load(strNewsDImage).into(ivNewsDImage);
            tvNewsDTitle.setText(strNewsDTitle);
            tvNewsDContent.setText(strNewsDContent.replace("　", "\n \n"));
            tvNewsDCompany.setText(strNewsDCompany);
            tvNewsDDate.setText(strNewsDDate);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                //뉴스 가져오기
                Document doc1 = Jsoup.connect(detailUrl).get();
                Elements data = doc1.select("article");
                strNewsDImage = data.select("picture").select("source").attr("srcset");
                strNewsDTitle = data.select(".sc-epnACN").text();
                strNewsDContent = data.select(".article_body").select("p").text();
                strNewsDCompany = data.select("footer").select("time").text();
                strNewsDDate = data.select("footer").select("a").text();

                //일단 코멘트페이지 들어가서 iFrameUrl을 만들 정보를 가져와야함
                commentUrl = doc1.select("meta[property=og:url]").attr("content") + "/comments";
                Document doc2 = Jsoup.connect(commentUrl).get();
                iFUrlFullPageUrl = doc2.select(".sc-cHGsZl").attr("data-full-page-url");
                iFUrlTopicId = doc2.select(".sc-cHGsZl").attr("data-topic-id");
                iFUrlSpaceId = doc2.select(".sc-cHGsZl").attr("data-space-id");
                //★댓글 가져오기 -> IFrame에서 가져오는 거였음,, 어려웠음. iFrame select가 안돼서 개고생했음,,
                //https://news.yahoo.co.jp/comment/plugin/v1/full/?sort=plus_points&order=desc&topic_id=20201005-00010001-rtn&full_page_url=https://headlines.yahoo.co.jp/cm/main?d=20201005-00010001-rtn-l26&space_id=2079510507
                iFrameUrl = "https://news.yahoo.co.jp/comment/plugin/v1/full/?";
                iFrameUrl += "sort=plus_points&";
                iFrameUrl += "order=desc&";
                iFrameUrl += "full_page_url=" + iFUrlFullPageUrl + "&";
                iFrameUrl += "topic_id=" + iFUrlTopicId + "&";
                iFrameUrl += "space_id=" + iFUrlSpaceId;

                //★진짜 댓글 가져오기
                Document doc3 = Jsoup.connect(iFrameUrl).get();
                Elements comments = doc3.select(".commentListItem");
                int i = 0;
                for (Element elem : comments) {
                    if (i >= 7) break;
                    mComment comment = new mComment();
                    comment.setImage(elem.select("img").attr("src"));
                    comment.setNickName(elem.select(".name").select("a").text());
                    comment.setDate(elem.select("time").text());
                    comment.setContent(elem.select(".cmtBody").text().replace("<br>", "\n"));
                    comment.setGood(elem.select(".good").select(".userNum").text());
                    comment.setBad(elem.select(".bad").select(".userNum").text());
                    arrComment.add(comment);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
