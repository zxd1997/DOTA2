package com.example.zxd1997.dota2.Fragments.Main;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Adapters.HeroInfoAdapter;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.HeroAbility;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.OKhttp;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeroDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroDetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "name";
    private final int PARSE = 1;
    View view;
    //    private TextView text;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            Log.d("waht", "handleMessage: " + msg.obj.toString());
            switch (msg.what) {
                case PARSE: {
                    List<Object> part = new ArrayList<>();
//                    Spanned spanned;
                    String t = msg.obj.toString();
                    String talent = t.substring(t.indexOf("<tr style=\"color:#000\">"), t.lastIndexOf("<tr style=\"color:#000\">"));
                    List<String> talents = new ArrayList<>();
                    String level_regEx = "(<div style=\"width:40px;display:table-cell;[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>)";
//                    <div style="width:40px;display:table-cell;text-align: center;padding: 4px;margin:0px 4px;background:#000;color: #fff;vertical-align:middle;">20</div>
                    Pattern p_level = Pattern.compile(level_regEx, Pattern.CASE_INSENSITIVE);
                    Matcher m_level = p_level.matcher(talent);
                    talent = m_level.replaceAll("");
                    talent = talent.replace("<div class=\"floatnone\">", "");
                    talent = talent.replace("<div class=\"center\">", "");
                    talent = talent.replace("</div>", "");
                    String reg = "(<div class=\"quiet\"[\\s\\S]*?>)|(（[\\s\\S]*?）)";
                    Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
                    while (talent.contains("<div class=\"quiet\"")) {
                        if (talent.indexOf("<div class=\"quiet\"", 2) == -1) break;
                        String s = talent.substring(talent.indexOf("<div class=\"quiet\""), talent.indexOf("<div class=\"quiet\"", 2));
                        Matcher m = p.matcher(s);
                        s = m.replaceAll("");
                        talents.add(s);
                        talent = talent.substring(talent.indexOf("<div class=\"quiet\"", 2));
                    }
                    Matcher m = p.matcher(talent);
                    talent = m.replaceAll("");
                    talents.add(talent);
//                    Log.d("talent", "handleMessage: " + talent);
                    part.add(talents);
//                        <div class="quiet" id="talent_20_2" style="padding: 6px; width: 180px; text-align: left; font-size: 12px; vertical-align: middle; display: table-cell; background-color: rgb(136, 136, 136);" onclick="talent(3,2)"><span class="ability_icon"><div class="center"><div class="floatnone"><img width="22" height="22" alt="Spellicons antimage blink.png" src="https://huiji-thumb.huijistatic.com/dota/uploads/thumb/8/8a/Spellicons_antimage_blink.png/22px-Spellicons_antimage_blink.png" srcset="https://huiji-thumb.huijistatic.com/dota/uploads/thumb/8/8a/Spellicons_antimage_blink.png/33px-Spellicons_antimage_blink.png 1.5x, https://huiji-thumb.huijistatic.com/dota/uploads/thumb/8/8a/Spellicons_antimage_blink.png/44px-Spellicons_antimage_blink.png 2x" data-file-height="128" data-file-width="128"></div></div></span><a title="闪烁（敌法师）" class="mw-redirect" href="/wiki/%E9%97%AA%E7%83%81%EF%BC%88%E6%95%8C%E6%B3%95%E5%B8%88%EF%BC%89">闪烁（敌法师）</a>后在原地留下一个不可控制的幻象</div>
//                    part.add(ttt);
//                    String  t1=t.substring(t.indexOf("花絮</span>") + 15, t.lastIndexOf("<table class=\"navbox\" style=\"width:auto;border-spacing:0\">") - 1).replaceAll("\\r\\n", "");
                    t = t.replaceAll("2/2d/Talent.png", "3/34/Talentb.png");
                    t = t.replaceAll("Talent.png", "Talentb.png");
                    t = t.substring(t.indexOf("简介") + 15, t.indexOf("<div style=\"clear:both\"></div>") + 30);
                    String regEx_script = "(<[\\s]*?table[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?table[\\s]*?>)|(<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?script[\\s]*?>)|(<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?style[\\s]*?>)|(<p>当前[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?p>)|(<div style=\"background:#111;color:#fff;[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>&#160;)|(<div class=\"plainlinks hlist tnavbar mini[^>]*?>[\\s\\S]*?<[\\s]*?/[\\s]*?div>)";
                    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
                    Matcher m_script = p_script.matcher(t);
                    t = m_script.replaceAll("");
                    t = t.replaceAll("<div class=\"floatnone\">", "");
                    t = t.replaceAll("<div class=\"center\">", "");
                    t = t.replaceAll("</div>", "");
                    t = t.replace("[[file:|center|x22px|link=]]", "");
                    while (t.contains("clear:both")) {
                        String tt = "<div style=\"" + t.substring(0, t.indexOf("clear:both"));
                        tt = tt.substring(0, tt.lastIndexOf("<div style="));
                        t = t.substring(t.indexOf("clear:both") + 10);
//                        Log.d("html", "handleMessage: " + tt);
                        part.add(tt);
                    }
//                    t1=t1.replaceAll("</div>","");
//                    part.add(t1);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new HeroInfoAdapter(part));
                    progressBar.setVisibility(View.GONE);
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
    private Hero hero;
    private TextView attack;
    private TextView cur_str;
    private TextView cur_agi;
    private TextView cur_int;
    private TextView health;
    private TextView health_regen;
    private TextView mana;
    private TextView mana_regen;
    private int id;
    private String name;

    public HeroDetailFragment() {
        // Required empty public constructor
    }

    public static HeroDetailFragment newInstance(int id, String name) {
        HeroDetailFragment fragment = new HeroDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            name = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hero_detail, container, false);
        SimpleDraweeView icon = view.findViewById(R.id.icon_card);
        progressBar = view.findViewById(R.id.progressBar);
        hero = MainActivity.heroStats.get(id);
//                icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id + "_icon", R.drawable.class))).build());
        Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + id + "_icon", R.drawable.class))).build(), icon);
        recyclerView = view.findViewById(R.id.parts);
        TextView str = view.findViewById(R.id.str);
        TextView agi = view.findViewById(R.id.agi);
        TextView inte = view.findViewById(R.id.inte);
        TextView hero_attr = view.findViewById(R.id.hero_card_name);
        SpannableStringBuilder attr = new SpannableStringBuilder();
        attr.append(getString(Tools.getResId(hero.getAttack_type(), R.string.class))).append("  ");
        Drawable drawable1 = Objects.requireNonNull(getContext()).getDrawable(Tools.getResId("icon_" + hero.getPrimary_attr(), R.drawable.class));
        SpannableString r1 = new SpannableString(" ");
        Objects.requireNonNull(drawable1).setBounds(0, 0, 56, 56);
        r1.setSpan(new ImageSpan(drawable1), 0, r1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        attr.append(r1).append(" ").append(getString(Tools.getResId(hero.getPrimary_attr() + "1", R.string.class))).append(" ").append(getString(R.string.hero));
        hero_attr.setText(attr);
        str.setText(String.format("%s + %s", String.valueOf(hero.getBase_str()), hero.getStr_gain()));
        agi.setText(String.format("%s + %s", String.valueOf(hero.getBase_agi()), hero.getAgi_gain()));
        inte.setText(String.format("%s + %s", String.valueOf(hero.getBase_int()), hero.getInt_gain()));
        attack = view.findViewById(R.id.attack);
        cur_str = view.findViewById(R.id.cur_str);
        cur_agi = view.findViewById(R.id.cur_agi);
        cur_int = view.findViewById(R.id.cur_int);
        health = view.findViewById(R.id.health);
        health_regen = view.findViewById(R.id.health_regen);
        mana = view.findViewById(R.id.mana);
        mana_regen = view.findViewById(R.id.mana_regen);
        armor = view.findViewById(R.id.armor);
        magic_res = view.findViewById(R.id.magic_resist);
        attack_rate = view.findViewById(R.id.attack_rate);
        spell_amp = view.findViewById(R.id.spell_amp);
        move_speed = view.findViewById(R.id.move_speed);
        level = view.findViewById(R.id.lvl);
        TextView info = view.findViewById(R.id.info);
        SpannableStringBuilder s = new SpannableStringBuilder();
        for (String t : hero.getRoles()) {
            Drawable drawable = getContext().getDrawable(Tools.getResId("icon_" + t.toLowerCase(), R.drawable.class));
            SpannableString r = new SpannableString(" ");
            Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
            r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            s.append(r).append(" ").append(getString(Tools.getResId(t, R.string.class))).append("  ");
        }
        info.setText(s);
        SeekBar seekBar = view.findViewById(R.id.seekBar1);
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
        TextView turn_rate = view.findViewById(R.id.turn_rate);
        turn_rate.setText(getString(R.string.turn_rate_1f, hero.getTurn_rate()));
        TextView attack_range = view.findViewById(R.id.attack_range);
        TextView p_s = view.findViewById(R.id.projectile_speed);
        attack_range.setText(getString(R.string.attack_range_1_d, hero.getAttack_range()));
        p_s.setText(getString(R.string.projectile_speed_1_s, !hero.getAttack_type().equals("Melee") ? hero.getProjectile_speed() + "" : getString(R.string.instant)));
        setLevel(1);
        RecyclerView skills = view.findViewById(R.id.skills);
        skills.setLayoutManager(new GridLayoutManager(getContext(), 6));
        HeroAbility heroAbility = MainActivity.heroAbilities.get(hero.getName());
        List<Cast> abilities = new ArrayList<>();
        for (String t : heroAbility.getAbilities()) {
            Log.d("ability", "onCreateView: " + t);
//            if (!t.equals("generic_hidden"))
            for (Map.Entry<String, String> entry : MainActivity.ability_ids.entrySet()) {
                if (entry.getValue().equals(t)) {
//                    Log.d("ability", "onCreateView: "+Integer.valueOf(entry.getKey()));
                    abilities.add(new Cast(0, entry.getKey(), 20));
                    break;
                }
            }
        }
        if (hero.getId() == 74) {
            for (int i = 0; i < 7; i++) {
                for (Map.Entry<String, String> entry : MainActivity.ability_ids.entrySet()) {
                    if (entry.getValue().equals(heroAbility.getTalents().get(i).getName())) {
//                        Log.d("ability", "onCreateView: "+Integer.valueOf(entry.getKey()));
                        abilities.add(new Cast(0, entry.getKey(), 20));
                        break;
                    }
                }
            }
        }
        skills.setAdapter(new CastAdapter(getContext(), abilities));
//        text = view.findViewById(R.id.hero_text);
        progressBar.setVisibility(View.VISIBLE);
        OKhttp.getFromService("https://dota.huijiwiki.com/wiki/" + name, handler, PARSE);
        return view;
    }

    private void setLevel(int level) {
//        Log.d("lvl", "setLevel: " + level);
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


}
