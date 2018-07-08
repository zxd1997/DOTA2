package com.example.zxd1997.dota2.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeroActivity extends AppCompatActivity {
    private final int PARSE = 1;
    private ProgressBar progressBar;
    private Hero hero;
    private TextView attack;
    private TextView cur_str;
    private TextView cur_agi;
    private TextView cur_int;
    private TextView health;
    private TextView health_regen;
    private TextView mana;
    private TextView mana_regen;
    private TextView text;
    private final Handler handler = new Handler(new Handler.Callback() {
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
                    String regEx_script = "(<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>)|(<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>)|(<p>当前[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?p>)|(<div style=\"background:#111;color:#fff;[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?div>)|(<div class=\"plainlinks hlist tnavbar mini[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?div>)";
                    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
                    Matcher m_script = p_script.matcher(t);
                    t = m_script.replaceAll("");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        spanned = Html.fromHtml(t, Html.FROM_HTML_MODE_COMPACT, new NetworkImageGetter(), null);
                    } else
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
    private TextView armor;
    private TextView magic_res;
    private TextView attack_rate;
    private TextView spell_amp;
    private TextView move_speed;
    private TextView level;
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
        progressBar = findViewById(R.id.progressBar);
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
        hero = MainActivity.heroStats.get(id);
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
                SimpleDraweeView icon = findViewById(R.id.icon_card);
//                icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id + "_icon", R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id + "_icon", R.drawable.class))).build(), icon);
                TextView str = findViewById(R.id.str);
                TextView agi = findViewById(R.id.agi);
                TextView inte = findViewById(R.id.inte);
                str.setText(String.format("%s + %s", String.valueOf(hero.getBase_str()), hero.getStr_gain()));
                agi.setText(String.format("%s + %s", String.valueOf(hero.getBase_agi()), hero.getAgi_gain()));
                inte.setText(String.format("%s + %s", String.valueOf(hero.getBase_int()), hero.getInt_gain()));
                attack = findViewById(R.id.attack);
                cur_str = findViewById(R.id.cur_str);
                cur_agi = findViewById(R.id.cur_agi);
                cur_int = findViewById(R.id.cur_int);
                health = findViewById(R.id.health);
                health_regen = findViewById(R.id.health_regen);
                mana = findViewById(R.id.mana);
                mana_regen = findViewById(R.id.mana_regen);
                armor = findViewById(R.id.armor);
                magic_res = findViewById(R.id.magic_resist);
                attack_rate = findViewById(R.id.attack_rate);
                spell_amp = findViewById(R.id.spell_amp);
                move_speed = findViewById(R.id.move_speed);
                level = findViewById(R.id.lvl);
                SeekBar seekBar = findViewById(R.id.seekBar1);
                level.setText(getString(R.string.level, 1));
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        level.setText(getString(R.string.level, progress + 1));
                        setLevel(progress + 1);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                TextView turn_rate = findViewById(R.id.turn_rate);
                turn_rate.setText(getString(R.string.turn_rate_1f, hero.getTurn_rate()));
                TextView attack_range = findViewById(R.id.attack_range);
                TextView p_s = findViewById(R.id.projectile_speed);
                attack_range.setText(getString(R.string.attack_range_1_d, hero.getAttack_range()));
                p_s.setText(getString(R.string.projectile_speed_1_s, hero.getProjectile_speed() > 0 ? hero.getProjectile_speed() + "" : getString(R.string.instant)));
                setLevel(1);
                text = findViewById(R.id.hero_text);
                progressBar.setVisibility(View.VISIBLE);
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

    private void setLevel(int level) {
        Log.d("lvl", "setLevel: " + level);
        double str = (double) hero.getBase_str() + hero.getStr_gain() * (level - 1);
        double agi = (double) hero.getBase_agi() + hero.getAgi_gain() * (level - 1);
        double inte = (double) hero.getBase_int() + hero.getInt_gain() * (level - 1);
        double health_gain = 18;
        int mana_gain = 12;
        double hp_regen = 0.0055;
        double m_regen = 0.018;
        double armor_gain = 0.16;
        double magic_res_gain = 0.0008;
        double attack_rate_amp = 1;
        double spell_amp_amp = 0.0007;
        double move_speed_amp = 0.0005;
        switch (hero.getPrimary_attr()) {
            case "str": {
                attack.setText(getString(R.string.attack, Math.round((hero.getBase_attack_min() + Math.floor(str) + hero.getBase_attack_max() + Math.floor(str)) / 2), (int) (hero.getBase_attack_min() + Math.floor(str)), (int) (hero.getBase_attack_max() + Math.floor(str))));
                health_gain *= 1.25;
                hp_regen *= 1.25;
                magic_res_gain *= 1.25;
                break;
            }
            case "agi": {
                attack.setText(getString(R.string.attack, Math.round((hero.getBase_attack_min() + Math.floor(agi) + hero.getBase_attack_max() + Math.floor(agi)) / 2), (int) (hero.getBase_attack_min() + Math.floor(agi)), (int) (hero.getBase_attack_max() + Math.floor(agi))));
                armor_gain *= 1.25;
                attack_rate_amp *= 1.25;
                move_speed_amp *= 1.25;
                break;
            }
            case "int": {
                attack.setText(getString(R.string.attack, Math.round((hero.getBase_attack_min() + Math.floor(inte) + hero.getBase_attack_max() + Math.floor(inte)) / 2), (int) (hero.getBase_attack_min() + Math.floor(inte)), (int) (hero.getBase_attack_max() + Math.floor(inte))));
                mana_gain *= 1.25;
                m_regen *= 1.25;
                spell_amp_amp *= 1.25;
                break;
            }
        }
        cur_str.setText(getString(R.string.str, Math.round(str)));
        cur_agi.setText(getString(R.string.agi, Math.round(agi)));
        cur_int.setText(getString(R.string.inte, Math.round(inte)));
        health.setText(getString(R.string.health, (hero.getBase_health() + Math.floor(str) * health_gain)));
        health_regen.setText(getString(R.string.health_regen_2f, hero.getBase_health_regen() * ((double) 1 + hp_regen * Math.floor(str))));
        mana.setText(getString(R.string.mana_1_d, (int) (hero.getBase_mana() + Math.floor(inte) * mana_gain)));
        mana_regen.setText(getString(R.string.mana_regen_2f, hero.getBase_mana_regen() * ((double) 1 + m_regen * Math.floor(inte))));
        armor.setText(getString(R.string.armor_2f, hero.getBase_armor() + armor_gain * agi));
        double mr = (double) 1 - (((double) 1 - (double) hero.getBase_mr() / 100) * ((double) 1 - magic_res_gain * str));
        magic_res.setText(String.format("%s%%", getString(R.string.magic_resistance_2f, mr * 100)));
        spell_amp.setText(String.format("%s%%", getString(R.string.spell_amplification_2f, inte * spell_amp_amp * 100)));
        move_speed.setText(getString(R.string.movement_speed_2f, (double) hero.getMove_speed() * ((double) 1 + agi * move_speed_amp)));
        double a_r = hero.getAttack_rate() / ((double) 100 + agi * attack_rate_amp) * 100;
        attack_rate.setText(getString(R.string.attack_rate_2f, a_r, hero.getAttack_rate()));
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

    /**** 异步加载图片 **/
    @SuppressLint("StaticFieldLeak")
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
