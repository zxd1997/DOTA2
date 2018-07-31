package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zxd1997.dota2.Adapters.ItemAdapter;
import com.example.zxd1997.dota2.Beans.Attribute;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.GridItemDecoration;
import com.example.zxd1997.dota2.Utils.ImageGetter;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
            t = t.substring(t.indexOf("<span class=\"mw-headline\" id=") + 59, t.indexOf("<div style=\"clear:both\"></div>") + 30);
            String regEx_script = "(<[\\s]*?table[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?table[\\s]*?>)|(<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?script[\\s]*?>)|(<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?style[\\s]*?>)|(<p>当前[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?p>)|(<div style=\"background:#111;color:#fff;[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>&#160;)|(<div class=\"plainlinks hlist tnavbar mini[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>)";
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(t);
            t = m_script.replaceAll("");
            t = t.replaceAll("<div style=\"display-block;clear:both;overflow: hidden;margin-bottom:1em; background-color: #d1d1d1;\">", "&nbsp;");
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
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> names,
                                           List<View> elements,
                                           List<View> snapshots) {
                super.onSharedElementEnd(names, elements, snapshots);
                for (final View view : elements) {
                    if (view instanceof SimpleDraweeView) {
                        view.post(() -> {
                            view.setVisibility(View.VISIBLE);
                            view.requestLayout();
                        });
                    }
                }
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        if (id == 0) {
            startActivity(new Intent(ItemActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        SimpleDraweeView head = findViewById(R.id.item_header);
        head.setTransitionName("item_" + id);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.items);
        int order = intent.getIntExtra("name", 0);
        name = typedArray.getString(order);
        typedArray.recycle();
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
//                TextView textView=findViewById(R.id.item_attr);
                Item item = null;
                for (Map.Entry<String, Item> entry : MainActivity.items.entrySet()) {
                    if (entry.getValue().getId() == id) {
                        item = entry.getValue();
                        break;
                    }
                }
//                SparseIntArray
//                assert item!=null;
//                StringBuilder s=new StringBuilder();
//                s.append(item.getDname()).append("\n" ).append(item.getCost()).append("\n").append("mc").append(item.getMc()).append("\n").append("cd").append(item.getCd()).append("\n");
//                if (item.getAttrib()!=null)
//                for (Item.Attribute attribute:item.getAttrib()){
//                    s.append(attribute.getHeader()).append(" ").append(attribute.getFooter()).append("\n");
//                }
//                if (item.getComponents()!=null)
//                for (String s1:item.getComponents()){
//                    s.append(s1).append("\n");
//                }
//                textView.setText(s);
                TextView cost = findViewById(R.id.cost);
//                TextView cd = findViewById(R.id.cd);
//                TextView mc = findViewById(R.id.mc);
                assert item != null;
                cost.setText(String.valueOf(item.getCost()));
//                cd.setText(item.getCd().equals("false") ? "0" : item.getCd());
//                mc.setText(item.getMc().equals("false") ? "0" : item.getMc());
                StringBuilder s1 = new StringBuilder();
                if (item.getAttrib() != null) {
                    TextView area = findViewById(R.id.attr_area);
                    area.setVisibility(View.VISIBLE);
                    for (Attribute attribute : item.getAttrib()) {
                        s1.append(attribute.getHeader()).append(" ").append(attribute.getValue() == null ? "" : attribute.getValue()).append(" ").append(attribute.getFooter() == null ? "" : attribute.getFooter()).append("\n");
                    }
                    area.setText(s1);
                }
                if (item.getComponents() != null) {
                    RecyclerView recyclerView = findViewById(R.id.components);
                    TextView com = findViewById(R.id.com);
                    com.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    List<Item> items = new ArrayList<>();
                    for (String s : item.getComponents()) {
                        items.add(MainActivity.items.get(s));
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ItemActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.addItemDecoration(new GridItemDecoration());
                    recyclerView.setLayoutManager(new GridLayoutManager(ItemActivity.this, items.size()));
                    recyclerView.setAdapter(new ItemAdapter(ItemActivity.this, items));
                }
                Resources resources = getResources();
                Configuration conf = new Configuration(resources.getConfiguration());
                Locale tmp = conf.locale;
                conf.locale = Locale.SIMPLIFIED_CHINESE;
                OKhttp.getFromService("https://dota.huijiwiki.com/wiki/" + new Resources(resources.getAssets(), resources.getDisplayMetrics(), conf).obtainTypedArray(R.array.items).getString(order), handler, PARSE);
                conf.locale = tmp;
                new Resources(resources.getAssets(), resources.getDisplayMetrics(), conf);
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
            Intent intent = new Intent(ItemActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
