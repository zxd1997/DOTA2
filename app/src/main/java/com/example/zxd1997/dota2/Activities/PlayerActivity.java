package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Fragments.Player.PlayerHeroFragment;
import com.example.zxd1997.dota2.Fragments.Player.PlayerMatchFragment;
import com.example.zxd1997.dota2.Fragments.Player.PlayerOtherFragment;
import com.example.zxd1997.dota2.Fragments.Player.PlayerOverviewFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Update;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerActivity extends AppCompatActivity {
    private Player player;
    private static final int PLAYER_INFO = 1;
    private ProgressBar progressBar;

    public Player getPlayer() {
        return player;
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        ViewServer.get(this).setFocusedWindow(this);
//    }
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            Log.d("msg", "handleMessage: " + msg.obj.toString());
            if (msg.obj.toString().contains("rate limit exceeded")) {
                Toast.makeText(PlayerActivity.this, R.string.api_rate, Toast.LENGTH_LONG).show();
            } else {
                player = new Gson().fromJson(msg.obj.toString(), Player.class);
                Objects.requireNonNull(getSupportActionBar()).setTitle(MyApplication.getContext().getString(R.string.player) + "：" + (player.getName() != null ? player.getName() : player.getPersonaname()));
                List<Fragment> fragments = new ArrayList<>();
                TabLayout tabLayout = findViewById(R.id.player_tab);
                fragments.add(PlayerOverviewFragment.newInstance());
                fragments.add(PlayerMatchFragment.newInstance());
                fragments.add(PlayerHeroFragment.newInstance());
                fragments.add(PlayerOtherFragment.newInstance());
                TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
                ViewPager mViewPager = findViewById(R.id.player_pager);
                mViewPager.setAdapter(tabFragmentAdapter);
                mViewPager.setOffscreenPageLimit(fragments.size());
                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
                progressBar.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewServer.get(this).addWindow(this);
        Update.setDensity(this, getApplication());
        getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP)); // 进入
        getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP)); // 返回
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> names,
                                           List<View> elements,
                                           List<View> snapshots) {
                super.onSharedElementEnd(names, elements, snapshots);
                progressBar.setVisibility(View.GONE);
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
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);
        if (id == 0) {
            Intent intent1 = new Intent(PlayerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        OKhttp.getFromService(getString(R.string.api) + getString(R.string.players) + id, handler, PLAYER_INFO);
    }

    @Override
    protected void onResume() {
        progressBar.setVisibility(View.GONE);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        ViewServer.get(this).removeWindow(this);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(PlayerActivity.this, SettingsActivity.class);
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
