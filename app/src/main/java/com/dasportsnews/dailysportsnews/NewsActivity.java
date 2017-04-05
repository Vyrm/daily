package com.dasportsnews.dailysportsnews;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsActivity extends AppCompatActivity {

    private ImageView img;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Log.d("parse", "news activity");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        img = (ImageView) findViewById(R.id.newsImage);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        new ParsePage().execute();
    }

    class ParsePage extends AsyncTask<Void, Void, Void> {
        String pictureSrc;
        Element element;

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert doc != null;
            Element picture = doc.getElementsByClass("single-post-image").select("img").first();
            pictureSrc = picture.absUrl("src");

            //picture in Bitmap
/*            try {
                InputStream in = new java.net.URL(pictureSrc).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }*/

            element = doc.getElementsByClass("entry-content").first();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Picasso.with(NewsActivity.this).load(pictureSrc).placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image).into(img);




            LinearLayout parentLayout = (LinearLayout) findViewById(R.id.newsContent);
            LayoutInflater layoutInflater = getLayoutInflater();
            View view;
            Elements children = element.children();
            for (Element ch : children) {
                if (ch.tagName().equals("p")) {
                    view = layoutInflater.inflate(R.layout.text_layout, parentLayout, false);
                    TextView textView = (TextView) view.findViewById(R.id.text_layout);
                    textView.setText(ch.text());
                    parentLayout.addView(textView);
                }
                if (ch.tagName().equals("h1")){
                    view = layoutInflater.inflate(R.layout.h_text_layout, parentLayout, false);
                    TextView textView = (TextView) view.findViewById(R.id.h_text_layout);
                    textView.setText(ch.text());
                    parentLayout.addView(textView);
                }
                if (ch.tagName().equals("div") && ch.className().equals("jetpack-video-wrapper")){
                    String url = ch.child(0).absUrl("src");

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.video_layout);

                    if (fragment == null) {
                        fragment = new YouTubeFragment();
                        fragmentManager.beginTransaction()
                                .add(R.id.video_layout, fragment)
                                .commit();
                    }


                }
            }
        }
    }
}
