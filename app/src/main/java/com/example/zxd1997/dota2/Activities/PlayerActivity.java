package com.example.zxd1997.dota2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zxd1997.dota2.Adapters.TabFragmentAdapter;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.Fragments.Player.PlayerHeroFragment;
import com.example.zxd1997.dota2.Fragments.Player.PlayerMatchFragment;
import com.example.zxd1997.dota2.Fragments.Player.PlayerOtherFragment;
import com.example.zxd1997.dota2.Fragments.Player.PlayerOverviewFragment;
import com.example.zxd1997.dota2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerActivity extends AppCompatActivity {
    private WL wl;
    private Player player;

    public WL getWl() {
        return wl;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(Color.parseColor("#FFCC0000"));
        getWindow().setNavigationBarColor(Color.parseColor("#FFCC0000"));
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);
        wl = (WL) intent.getSerializableExtra("WL");
        player = (Player) intent.getSerializableExtra("player");
        Log.d("id", "onCreate: " + id + " " + player.getPersonaname() + " " + wl.getWin());
        if (player == null) {
            Intent intent1 = new Intent(PlayerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        List<Fragment> fragments = new ArrayList<>();
        TabLayout tabLayout = findViewById(R.id.player_tab);
        fragments.add(PlayerOverviewFragment.newInstance());
        fragments.add(PlayerMatchFragment.newInstance());
        fragments.add(PlayerHeroFragment.newInstance());
        fragments.add(PlayerOtherFragment.newInstance());
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        ViewPager mViewPager = findViewById(R.id.player_pager);
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(PlayerActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
