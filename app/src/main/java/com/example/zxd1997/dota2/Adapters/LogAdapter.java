package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Beans.TeamFightCast;
import com.example.zxd1997.dota2.Fragments.Match.TeamFightDialogFragment;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "type";
    private final int KILL = 0;
    private final int RUNE = 1;
    private final int BUYBACK = 2;
    private final int ROSHAN = 3;
    private final int COURIER = 4;
    private final int TOWER = 6;
    private final int AEGIS = 8;
    private final int CHAT = 9;
    private final int FIRST_BLOOD = 7;
    private final Context context;
    private final List<Match.Objective> logs;

    public LogAdapter(Context context, List<Match.Objective> logs) {
        this.context = context;
        this.logs = logs;
    }


    @Override
    public int getItemViewType(int position) {
//        Log.d("type", "getItemViewType: " + logs.get(position).getType());
        switch (logs.get(position).getType()) {
            case "kill": {
                return KILL;
            }
            case "building_kill": {
                return TOWER;
            }
            case "CHAT_MESSAGE_COURIER_LOST": {
                return COURIER;
            }
            case "rune_pickup": {
                return RUNE;
            }
            case "buyback_log": {
                return BUYBACK;
            }
            case "CHAT_MESSAGE_ROSHAN_KILL": {
                return ROSHAN;
            }
            case "CHAT_MESSAGE_FIRSTBLOOD": {
                return FIRST_BLOOD;
            }
            case "CHAT_MESSAGE_AEGIS": {
                return AEGIS;
            }
            case "chat": {
                return CHAT;
            }
            default: {
                return 5;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("type", "onCreateViewHolder: " + viewType);
        switch (viewType) {
            case KILL:
            case TOWER:
            case COURIER:
            case ROSHAN: {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_kill, parent, false));
            }
            case RUNE:
                return new ViewHolderNotKill(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_rune, parent, false));
            case BUYBACK:
            case FIRST_BLOOD:
            case CHAT:
            case AEGIS: {
                return new ViewHolderNotKill(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_log, parent, false));
            }
            default:
                return new TeamFight(LayoutInflater.from(parent.getContext()).inflate(R.layout.teamfight, parent, false));
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final StringBuilder tt = Tools.getTime(logs.get(position).getTime());
//        Log.d("type", "onBindViewHolder: " + getItemViewType(position));
        switch (getItemViewType(position)) {
            case KILL: {
                Match.Objective kill = logs.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + kill.getPlayer_slot(), R.color.class)));
//                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(kill.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(kill.getHero_id(), R.drawable.class))).build(), viewHolder.killer);
                if (kill.getPlayer_slot() < 100) {
//                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build());
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build(), viewHolder.kill_sign);
                } else {
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build(), viewHolder.kill_sign);
                }
//                viewHolder.killed.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + MainActivity.heroes.get(kill.getKey()).getId(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + MainActivity.heroes.get(kill.getKey()).getId(), R.drawable.class))).build(), viewHolder.killed);
                viewHolder.killed.setVisibility(View.VISIBLE);
                viewHolder.name.setText(kill.getName());
                viewHolder.time.setText(tt);
                break;
            }
            case TOWER: {
                Match.Objective o = logs.get(position);
//                Log.d("type", "onBindViewHolder: " + o.getKey() + " " + o.getUnit());
                ViewHolder viewHolder = (ViewHolder) holder;
//                Log.d(TAG, "onBindViewHolder: " + o.getKey());
                viewHolder.log.setText(context.getString(Tools.getResId(o.getKey(), R.string.class)));
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.time.setText(tt);
                viewHolder.killed.setVisibility(View.GONE);
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(o.getHero_id(), R.drawable.class))).build(), viewHolder.killer);
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + o.getPlayer_slot(), R.color.class)));
                if (o.getPlayer_slot() < 100) {
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build(), viewHolder.kill_sign);
                } else {
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build(), viewHolder.kill_sign);
                }
                viewHolder.name.setText(o.getName());
                break;
            }
            case RUNE: {
                Match.Objective rune = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + rune.getPlayer_slot(), R.color.class)));
//                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(rune.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(rune.getHero_id(), R.drawable.class))).build(), viewHolder.who);
//                Log.d(TAG, "onBindViewHolder: " + rune.getTime() + " " + rune.getType() + " " + rune.getKey());
                viewHolder.name.setText(rune.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(Tools.getResId("rune_" + rune.getKey(), R.drawable.class));
                Objects.requireNonNull(drawable).setBounds(0, 0, 40, 40);
                r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.append(context.getString(R.string.activated)).append(r).append(context.getString(Tools.getResId("rune_" + rune.getKey(), R.string.class))).append(" ").append(context.getString(R.string.rune));
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                break;
            }
            case BUYBACK: {
                Match.Objective buyback = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + buyback.getPlayer_slot(), R.color.class)));
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(buyback.getHero_id(), R.drawable.class))).build(), viewHolder.who);
                viewHolder.name.setText(buyback.getName());
                viewHolder.log.setText(R.string.buyback);
                viewHolder.time.setText(tt);
                break;
            }
            case FIRST_BLOOD: {
                Match.Objective fb = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + fb.getPlayer_slot(), R.color.class)));
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(fb.getHero_id(), R.drawable.class))).build(), viewHolder.who);
                viewHolder.name.setText(fb.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(R.drawable.bloodsplattersmall2);
                Objects.requireNonNull(drawable).setBounds(0, 0, 40, 40);
                r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.append(r).append(context.getString(R.string.first_blood));
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                break;
            }
            case AEGIS: {
                Match.Objective aegis = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + aegis.getPlayer_slot(), R.color.class)));
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(aegis.getHero_id(), R.drawable.class))).build(), viewHolder.who);
                viewHolder.name.setText(aegis.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(R.drawable.aegis_icon);
                Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.append(r).append(context.getString(R.string.aegis));
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                break;
            }
            case ROSHAN: {
                Match.Objective roshan = logs.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + roshan.getPlayer_slot(), R.color.class)));
//                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(roshan.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(roshan.getHero_id(), R.drawable.class))).build(), viewHolder.killer);
                viewHolder.name.setText(roshan.getName());
                if (roshan.getTeam() == 2) {
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build(), viewHolder.kill_sign);
                } else {
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build(), viewHolder.kill_sign);
                }
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(R.drawable.roshan);
                Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.roshan));
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.killed.setVisibility(View.GONE);
                break;
            }
            case COURIER: {
                Match.Objective courier = logs.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                if (courier.getTeam() == 3) {
//                    viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_logo)).build());
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_logo)).build(), viewHolder.killer);
                    viewHolder.color.setBackgroundColor(context.getResources().getColor(R.color.win));
                    courier.setName(context.getString(R.string.radiant));
                    courier.setHero_id("radiant_logo");
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build(), viewHolder.kill_sign);
                    Drawable drawable = context.getDrawable(R.drawable.direcourier);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                    r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.dire_courier));
                } else {
//                    viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_logo)).build());
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_logo)).build(), viewHolder.killer);
                    viewHolder.color.setBackgroundColor(context.getResources().getColor(R.color.lose));
                    courier.setName(context.getString(R.string.dire));
                    courier.setHero_id("dire_logo");
                    Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build(), viewHolder.kill_sign);
                    Drawable drawable = context.getDrawable(R.drawable.radiantcourier);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                    r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.radiant_courier));
                }
                viewHolder.name.setText(courier.getName());
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.killed.setVisibility(View.GONE);
                break;
            }
            case CHAT: {
                Match.Objective chat = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                int resid = Tools.getResId("slot_" + chat.getPlayer_slot(), R.color.class);
                if (resid != 0)
                    viewHolder.color.setBackgroundColor(context.getResources().getColor(resid));
//                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(chat.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(chat.getHero_id(), R.drawable.class))).build(), viewHolder.who);
                viewHolder.name.setText(chat.getName());
                viewHolder.log.setText(String.format(":%s", chat.getKey()));
                viewHolder.time.setText(tt);
                break;
            }
            default: {
                final List<Point> points = new ArrayList<>();
                final TeamFight teamFight = (TeamFight) holder;
                teamFight.start_end.setText(tt);
                new Thread(() -> {
                    final int ABILITY = 0;
                    final int ITEM = 1;
                    final int HEADER = -1;
                    final int HERO = 3;
                    final int RADIANT_HEADER = 11;
                    final int TEAMFIGHT_HEADER = 12;
                    final Match.TeamFight teamfight = (Match.TeamFight) logs.get(position);
                    tt.append(" - ").append(Tools.getTime(teamfight.getEnd()));
                    float x;
                    float y;
                    int radiant_deaths = 0;
                    int dire_deaths = 0;
                    int radiant_gold_delta = 0;
                    int dire_gold_delta = 0;
                    int j = 0;
                    final ArrayList<Cast> contents = new ArrayList<>();
                    contents.add(new TeamFightCast(1, "11", 16));
                    contents.add(new TeamFightCast(2, "11", 15, points));
                    contents.add(new Cast(R.drawable.radiant_header, context.getResources().getString(R.string.radiant), RADIANT_HEADER));
                    for (Match.TeamFight.TeamFightPlayer p : teamfight.getPlayers()) {
                        if (j < 5) {
                            radiant_deaths += p.getDeaths();
                            radiant_gold_delta += p.getGold_delta();
                        } else {
                            dire_deaths += p.getDeaths();
                            dire_gold_delta += p.getGold_delta();
                        }
                        if (p.getDeaths() > 0) {
                            for (Map.Entry<Integer, Map<Integer, Integer>> entry : p.getDeaths_pos().entrySet()) {
                                x = entry.getKey();
                                y = 0;
                                for (Map.Entry<Integer, Integer> entry1 : entry.getValue().entrySet()) {
                                    y = entry1.getKey();
                                    points.add(new Point((x * 2 - 136) / 2 * (float) 4 / 510, (y * 2 - 120) / 2 * (float) 4 / 510,
                                            Tools.getResId(p.getHero_id() + "_icon", R.drawable.class)));
                                }
                            }

                        }
                        if (j == 5) {
                            contents.add(new Cast(R.drawable.dire_header, context.getResources().getString(R.string.dire), RADIANT_HEADER));
                        }
                        j++;
                        if (p.getDeaths() > 0 || p.getBuybacks() > 0 || p.getDamage() > 0 || p.getHealing() > 0) {
                            contents.add(new TeamFightCast(0, "", TEAMFIGHT_HEADER, p));
                            if (p.getKilled().size() > 0) {
                                contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.kills), HEADER));
                                for (Map.Entry<String, Integer> entry : p.getKilled().entrySet()) {
                                    Hero hero = MainActivity.heroes.get(entry.getKey());
                                    for (int k = 0; k < entry.getValue(); k++)
                                        contents.add(new Cast(entry.getValue(), "hero_" + hero.getId() + "_icon", HERO));
                                }
                            }
                            if (p.getAbility_uses().size() + p.getItem_uses().size() > 0) {
                                contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
                                for (Map.Entry<String, Integer> entry : p.getAbility_uses().entrySet()) {
                                    for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                                        if (entry1.getValue().equals(entry.getKey())) {
                                            contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                                            break;
                                        }
                                    }
                                }
                                for (Map.Entry<String, Integer> entry : p.getItem_uses().entrySet()) {
                                    Item item = MainActivity.items.get(entry.getKey());
                                    contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                                }
                            }
                        }
                    }
                    final SpannableString death = new SpannableString(" ");
                    Drawable drawable = context.getDrawable(R.drawable.player_death);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 40, 42);
                    death.setSpan(new ImageSpan(drawable), 0, death.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    final SpannableString gold = new SpannableString(" ");
                    drawable = context.getDrawable(R.drawable.gold);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 50, 34);
                    gold.setSpan(new ImageSpan(drawable), 0, gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    final SpannableString radiant_death = new SpannableString(radiant_deaths + "");
                    final SpannableString dire_death = new SpannableString(dire_deaths + "");
                    final SpannableString radiant_gold = new SpannableString(radiant_gold_delta + "");
                    final SpannableString dire_gold = new SpannableString(dire_gold_delta + "");
                    final SpannableString up = new SpannableString(" ");
                    drawable = context.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
                    up.setSpan(new ImageSpan(drawable), 0, up.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    final SpannableString down = new SpannableString(" ");
                    drawable = context.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
                    down.setSpan(new ImageSpan(drawable), 0, down.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    dire_death.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, dire_death.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    radiant_death.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, radiant_death.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    final int radiant_gold_delta_f = radiant_gold_delta;
                    final int dire_gold_delta_f = dire_gold_delta;
                    teamFight.itemView.post(() -> {
                        if (radiant_gold_delta_f > 0) {
                            radiant_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, radiant_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            teamFight.radiant_gold_delta.setText(new SpannableStringBuilder().append(up).append(radiant_gold).append(" ").append(gold));
                        } else {
                            radiant_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, radiant_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            teamFight.radiant_gold_delta.setText(new SpannableStringBuilder().append(down).append(radiant_gold).append(" ").append(gold));
                        }
                        if (dire_gold_delta_f > 0) {
                            dire_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, dire_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            teamFight.dire_gold_delta.setText(new SpannableStringBuilder().append(gold).append(" ").append(dire_gold).append(up));
                        } else {
                            dire_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, dire_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            teamFight.dire_gold_delta.setText(new SpannableStringBuilder().append(gold).append(" ").append(dire_gold).append(down));
                        }
                        if (radiant_gold_delta_f - dire_gold_delta_f > 0) {
                            teamFight.teamfight_win.setText(String.format("%s %s", context.getString(R.string.radiant), context.getString(R.string.win)));
                            teamFight.teamfight_win.setTextColor(context.getResources().getColor(R.color.win));
                        } else {
                            teamFight.teamfight_win.setText(String.format("%s %s", context.getString(R.string.dire), context.getString(R.string.win)));
                            teamFight.teamfight_win.setTextColor(context.getResources().getColor(R.color.lose));
                        }
                        if (Math.abs(Math.abs(radiant_gold_delta_f) - Math.abs(dire_gold_delta_f)) < 250) {
                            teamFight.teamfight_win.setText(context.getString(R.string.drew));
                            teamFight.teamfight_win.setTextColor(context.getResources().getColor(R.color.high));
                        }
                        teamFight.radiant_death.setText(new SpannableStringBuilder().append(death).append(context.getString(R.string.r_d)).append(radiant_death));
                        teamFight.dire_death.setText(new SpannableStringBuilder().append(context.getString(R.string.d_d)).append(dire_death).append(" ").append(death));
                        TeamFightCast t = (TeamFightCast) contents.get(0);
                        t.setId("teamfight_" + position);
                        t.setWin(teamFight.teamfight_win.getText());
                        t.setDire_death(teamFight.dire_death.getText());
                        t.setDire_gold(teamFight.dire_gold_delta.getText());
                        t.setRadiant_death(teamFight.radiant_death.getText());
                        t.setRadiant_gold(teamFight.radiant_gold_delta.getText());
                        t.setTime1(teamFight.start_end.getText());
                        teamFight.itemView.setOnClickListener(v -> {
                            TeamFightDialogFragment instance = TeamFightDialogFragment.newInstance(contents);
                            instance.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                        });
                    });
                }).start();
            }
        }
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView killer;
        final SimpleDraweeView killed;
        final SimpleDraweeView kill_sign;
        final View color;
        final TextView name;
        final TextView log;
        final TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            killer = itemView.findViewById(R.id.log_killer);
            killed = itemView.findViewById(R.id.log_killed);
            kill_sign = itemView.findViewById(R.id.kill);
            color = itemView.findViewById(R.id.log_color);
            name = itemView.findViewById(R.id.log_name);
            log = itemView.findViewById(R.id.what);
            time = itemView.findViewById(R.id.log_time);
        }
    }

    public class Point {
        private float x;
        private float y;
        private int color;

        Point(float x, float y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    class ViewHolderNotKill extends RecyclerView.ViewHolder {
        final SimpleDraweeView who;
        final View color;
        final TextView name;
        final TextView log;
        final TextView time;

        ViewHolderNotKill(View itemView) {
            super(itemView);
            who = itemView.findViewById(R.id.log_picker);
            color = itemView.findViewById(R.id.log_color);
            name = itemView.findViewById(R.id.log_name);
            log = itemView.findViewById(R.id.do_what);
            time = itemView.findViewById(R.id.log_time);
        }
    }

    class TeamFight extends RecyclerView.ViewHolder {
        final TextView radiant_gold_delta;
        final TextView radiant_death;
        final TextView start_end;
        final TextView dire_death;
        final TextView dire_gold_delta;
        final TextView teamfight_win;

        TeamFight(final View itemView) {
            super(itemView);
            teamfight_win = itemView.findViewById(R.id.teamfight_win);
            radiant_gold_delta = itemView.findViewById(R.id.radiant_gold_delta);
            radiant_death = itemView.findViewById(R.id.radiant_death);
            start_end = itemView.findViewById(R.id.start_end);
            dire_death = itemView.findViewById(R.id.dire_death);
            dire_gold_delta = itemView.findViewById(R.id.dire_gold_delta);
        }
    }
}
