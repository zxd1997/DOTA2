package com.example.zxd1997.dota2.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.ImageGetter;
import com.example.zxd1997.dota2.Utils.OKhttp;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameInfoActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    Handler handler = new Handler(msg -> {
        String t = msg.obj.toString();
        t = t.substring(t.indexOf("<div class=\"mw-parser-output\">"), t.indexOf("<div class=\"printfooter\">"));
//        (<[\s]*?table[^>]*?>[\s\S]*?<[\s]*?/[\s]*?table[\s]*?>)|
        String regEx_script = "(<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?script[\\s]*?>)|(<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?style[\\s]*?>)|(<p>当前[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?p>)|(<div style=\"background:#111;color:#fff;[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>&#160;)|(<div class=\"plainlinks hlist tnavbar mini[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>)";
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(t);
        t = m_script.replaceAll("");
        t = t.replaceAll("<div style=\"display-block;clear:both;overflow: hidden;margin-bottom:1em; background-color: #d1d1d1;\">", "&nbsp;");
        t = t.replaceAll("<div class=\"floatnone\">", "");
        t = t.replaceAll("<div class=\"center\">", "");
        t = t.replaceAll("</div>", "");
        t = t.replaceAll("2/2d/Talent.png", "3/34/Talentb.png");
        t = t.replaceAll("Talent.png", "Talentb.png");
        t = t.replace("[[file:|center|x22px|link=]]", "");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            textView.setText(Html.fromHtml(t, Html.FROM_HTML_MODE_COMPACT, new ImageGetter(textView), null));
        else textView.setText(Html.fromHtml(t, new ImageGetter(textView), null));
        progressBar.setVisibility(View.GONE);
        return true;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.info);
        String name = getIntent().getStringExtra("name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        OKhttp.getFromService("https://dota.huijiwiki.com/wiki/" + name, handler, 222);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
