package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeroActivity extends AppCompatActivity {
    private final int PARSE = 1;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    TextView text;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d("waht", "handleMessage: " + msg.obj.toString());
            switch (msg.what) {
                case PARSE: {
                    Spanned spanned;
                    String t = msg.obj.toString();
                    t = t.replaceAll("2/2d/Talent.png", "3/34/Talentb.png");
                    t = t.replaceAll("Talent.png", "Talentb.png");
                    t = t.substring(t.indexOf("简介") + 15, t.indexOf("<div style=\"clear:both\"></div>") + 30) + t.substring(t.indexOf("花絮</span>") + 15, t.lastIndexOf("<table class=\"navbox\" style=\"width:auto;border-spacing:0\">") - 1).replaceAll("\\r\\n", "");
//                    t=t.substring(t.indexOf("bodyContent")-26,t.lastIndexOf("<table class=\"navbox\" style=\"width:auto;border-spacing:0\">")-1);
                    String regEx_script = "(<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>)|(<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>)|(<p>当前[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?p>)|(<div style=\"background:#111;color:#fff;[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?div>)|(<div class=\"plainlinks hlist tnavbar mini[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?div>)";
                    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
                    Matcher m_script = p_script.matcher(t);
                    t = m_script.replaceAll("");
//                    String regEx_skill = "<div style=\"display-block;clear:both;overflow: hidden;margin-bottom:1em; background-color: #d1d1d1;[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?div>";
//                    String regEx_skill = "<(?<HtmlTag>[\\w]+)[^>]*\\sstyle=(?<Quote>[\"']?)float:left;padding-bottom:1em;background:#222;color:#eee;width:300px;margin-right:8px;[\"']?[^>]*>";
//                    Pattern p_skill = Pattern.compile(regEx_skill, Pattern.CASE_INSENSITIVE);
//                    Matcher m_skill = p_skill.matcher(t);
//                    List<Spanned> skills = new ArrayList<>();
//                    while (m_skill.find()) {
//                        CardView cardView=new CardView(getApplicationContext());
//                        TextView textView=new TextView(getApplicationContext());
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                            textView.setText(Html.fromHtml(m_skill.group(), Html.FROM_HTML_MODE_COMPACT, new NetworkImageGetter(), null));
////                            skills.add(Html.fromHtml(m_skill.group(), Html.FROM_HTML_MODE_COMPACT, new NetworkImageGetter(), null));
//                        } else
////                            skills.add(Html.fromHtml(m_skill.group(), new NetworkImageGetter(), null));
//                            textView.setText(Html.fromHtml(m_skill.group(), new NetworkImageGetter(), null));
//                        cardView.addView(textView);
//                        linearLayout.addView(cardView);
//                        Log.d(":whay", "handleMessage: " + m_skill.group());
//                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        spanned = Html.fromHtml(t, Html.FROM_HTML_MODE_COMPACT, new NetworkImageGetter(), null);
                    } else
//                        spanned = Html.fromHtml(wikiContent.getParse().getText(), new NetworkImageGetter(), null);
                        spanned = Html.fromHtml(t, new NetworkImageGetter(), null);

                    Log.d("xxx", "handleMessage: " + spanned);
                    text.setText(spanned);
                    progressBar.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                    break;
                }

            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        progressBar = findViewById(R.id.progressBar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if (id == 0) {
            startActivity(new Intent(HeroActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        linearLayout = findViewById(R.id.ll);
        String name = intent.getStringExtra("name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SimpleDraweeView head = findViewById(R.id.head);
        head.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id, R.drawable.class))).build());
        text = findViewById(R.id.hero_text);
        progressBar.setVisibility(View.VISIBLE);
        OKhttp.getFromService("https://dota.huijiwiki.com/wiki/" + name, handler, PARSE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(HeroActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**** 异步加载图片 **/
    private final class LoadImage extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2);
                mDrawable.setLevel(1);
                CharSequence t = text.getText();
                text.setText(t);
            }
        }
    }

    private final class NetworkImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            LevelListDrawable d = new LevelListDrawable();
            new LoadImage().execute(source, d);
            return d;
        }
    }
}
