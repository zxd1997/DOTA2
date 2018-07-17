package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Transition;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.ImageGetter;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemActivity extends AppCompatActivity {
    private final int PARSE = 20;
    int id;
    String name;
    TextView textView;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String t = msg.obj.toString();
            t = t.substring(t.indexOf("<span class=\"mw-headline\" id="), t.indexOf("<div style=\"clear:both\"></div>") + 30);
            String regEx_script = "(<[\\s]*?table[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?table[\\s]*?>)|(<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?script[\\s]*?>)|(<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?style[\\s]*?>)|(<p>当前[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?p>)|(<div style=\"background:#111;color:#fff;[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>&#160;)|(<div class=\"plainlinks hlist tnavbar mini[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>)";
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(t);
            t = m_script.replaceAll("");
            t = t.replaceAll("<div class=\"floatnone\">", "");
            t = t.replaceAll("<div class=\"center\">", "");
            t = t.replaceAll("</div>", "");
            t = t.replace("[[file:|center|x22px|link=]]", "");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                textView.setText(Html.fromHtml(t, Html.FROM_HTML_MODE_COMPACT, new ImageGetter(textView), null));
            else textView.setText(Html.fromHtml(t, new ImageGetter(textView), null));
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //Fresco Shared Element
        getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP)); // 进入
        getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP)); // 返回
        setContentView(R.layout.activity_item);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        if (id == 0) {
            startActivity(new Intent(ItemActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        SimpleDraweeView head = findViewById(R.id.item_header);
        head.setTransitionName("item_" + id);
        name = intent.getStringExtra("name");
        head.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + id, R.drawable.class))).build());
        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                textView = findViewById(R.id.item_text);
//                ViewPager viewPager = findViewById(R.id.hero_pager);
//                TabLayout tabLayout = findViewById(R.id.tabs_hero);
//                List<Fragment> fragments = new ArrayList<>();
//                fragments.add(HeroDetailFragment.newInstance(id, name));
//                fragments.add(HeroStatFragment.newInstance(id, name));
//                TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
//                viewPager.setAdapter(tabFragmentAdapter);
//                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//                tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
                OKhttp.getFromService("https://dota.huijiwiki.com/wiki/" + name, handler, PARSE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
