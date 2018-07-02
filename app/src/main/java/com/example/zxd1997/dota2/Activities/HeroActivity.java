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
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.WikiContent;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeroActivity extends AppCompatActivity {
    private final int PARSE = 1;
    private final int IMAGE = 2;
    TextView text;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d("waht", "handleMessage: " + msg.obj.toString());
            switch (msg.what) {
                case PARSE: {
                    WikiContent wikiContent = new Gson().fromJson(msg.obj.toString(), WikiContent.class);
                    Spanned spanned;
                    String t = wikiContent.getQuery().getPages().get(0).getRevisions().get(0).getContent();
                    String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
                    String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
                    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
                    Matcher m_script = p_script.matcher(t);
                    t = m_script.replaceAll("");
                    Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
                    Matcher m_style = p_style.matcher(t);
                    t = m_style.replaceAll("");
                    int index = t.lastIndexOf("<table class=\"navbox\" style=\"width:auto;border-spacing:0\">");
                    t = t.substring(0, index - 1);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        spanned = Html.fromHtml(t, Html.FROM_HTML_MODE_COMPACT, new NetworkImageGetter(), null);
                    } else
//                        spanned = Html.fromHtml(wikiContent.getParse().getText(), new NetworkImageGetter(), null);
                        spanned = Html.fromHtml(t);
                    text.setText(spanned);
                    break;
                }
                case IMAGE: {
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
        String name = intent.getStringExtra("name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SimpleDraweeView head = findViewById(R.id.head);
        head.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id, R.drawable.class))).build());
        text = findViewById(R.id.hero_text);
        OKhttp.getFromService("http://dota.huijiwiki.com/api.php?action=query&format=json&formatversion=2&prop=revisions&titles=" + name + "&rvprop=content&rvparse=true", handler, PARSE);
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
    private final static class LoadImage extends AsyncTask<Object, Void, Bitmap> {
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
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
            }
        }
    }

    private final class NetworkImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) { // TODO Auto-generated method stub
            LevelListDrawable d = new LevelListDrawable();
            new LoadImage().execute(source, d);
            return d;
        }
    }
}
