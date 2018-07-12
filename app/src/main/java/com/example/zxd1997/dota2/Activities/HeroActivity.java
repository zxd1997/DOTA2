package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Fragments.Main.HeroDetailFragment;
import com.example.zxd1997.dota2.Fragments.Main.HeroStatFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HeroActivity extends AppCompatActivity {
    private int id;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //Fresco Shared Element
        getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP)); // 进入
        getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP)); // 返回
        setContentView(R.layout.activity_hero);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        if (id == 0) {
            startActivity(new Intent(HeroActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            this.finish();
        }
        SimpleDraweeView head = findViewById(R.id.head);
        head.setTransitionName("hero_" + id);
        name = intent.getStringExtra("name");
//        head.setImageResource(Tools.getResId("hero_" + id, R.drawable.class));
//        Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id, R.drawable.class))).build(),head);
        head.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id, R.drawable.class))).build());
        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                ViewPager viewPager = findViewById(R.id.hero_pager);
                TabLayout tabLayout = findViewById(R.id.tabs_hero);
                List<Fragment> fragments = new ArrayList<>();
                fragments.add(HeroDetailFragment.newInstance(id, name));
                fragments.add(HeroStatFragment.newInstance(id, name));
                TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
                viewPager.setAdapter(tabFragmentAdapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
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
            Intent intent = new Intent(HeroActivity.this, SettingsActivity.class);
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
