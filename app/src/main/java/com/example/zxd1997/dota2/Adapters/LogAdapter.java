package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Objects;

public class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int KILL = 0;
    private final int RUNE = 1;
    private final int BUYBACK = 2;
    private final int ROSHAN = 3;
    private final int COURIER = 4;
    private final int TOWER = 6;
    private final int AEGIS = 8;
    private final int FIRST_BLOOD = 7;
    private final Context context;
    private final List<Match.Objective> logs;

    public LogAdapter(Context context, List<Match.Objective> logs) {
        this.context = context;
        this.logs = logs;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("type", "getItemViewType: " + logs.get(position).getType());
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
            default: {
                int TEAM_FIGHT = 5;
                return TEAM_FIGHT;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("type", "onCreateViewHolder: " + viewType);
        switch (viewType) {
            case KILL:
            case TOWER:
            case COURIER:
            case ROSHAN: {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_kill, parent, false));
            }
            case RUNE:
            case BUYBACK:
            case FIRST_BLOOD:
            case AEGIS: {
                return new ViewHolderNotKill(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_log, parent, false));
            }
            default:
                return new TeamFight(LayoutInflater.from(parent.getContext()).inflate(R.layout.teamfight, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int time = Math.abs(logs.get(position).getTime());
        int h = time / 3600;
        int m = time % 3600 / 60;
        int s = time % 3600 % 60;
        StringBuilder tt = new StringBuilder();
        if (logs.get(position).getTime() < 0) tt.append("-");
        if (h > 0) {
            tt.append((h < 10) ? "0" + h + ":" : h + ":");
        }
        tt.append((m < 10) ? "0" + m + ":" : m + ":");
        tt.append((s < 10) ? "0" + s : s);
        Log.d("type", "onBindViewHolder: " + getItemViewType(position));
        switch (getItemViewType(position)) {
            case KILL: {
                Match.Objective kill = logs.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + kill.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(kill.getHero_id(), "drawable", context.getPackageName()))).build());
                if (kill.getPlayer_slot() < 100) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("radiant_kill", "drawable", context.getPackageName()))).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("dire_kill", "drawable", context.getPackageName()))).build());
                }
                viewHolder.killed.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + MainActivity.heroes.get(kill.getKey()).getId(), "drawable", context.getPackageName()))).build());
                viewHolder.killed.setVisibility(View.VISIBLE);
                viewHolder.name.setText(kill.getName().equals("") ? context.getString(R.string.anonymous) : kill.getName());
                viewHolder.time.setText(tt);
                break;
            }
            case TOWER: {
                Match.Objective o = logs.get(position);
                Log.d("type", "onBindViewHolder: " + o.getKey() + " " + o.getUnit());
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.log.setText(context.getString(context.getResources().getIdentifier(o.getKey(), "string", context.getPackageName())));
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.time.setText(tt);
                viewHolder.killed.setVisibility(View.GONE);
                Log.d("type", "onBindViewHolder: " + o.getName() + " " + o.getHero_id() + " " + o.getUnit());
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(o.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + o.getPlayer_slot(), "color", context.getPackageName())));
                if (o.getPlayer_slot() < 100) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("radiant_kill", "drawable", context.getPackageName()))).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("dire_kill", "drawable", context.getPackageName()))).build());
                }
                viewHolder.name.setText(o.getName().equals("") ? context.getString(R.string.anonymous) : o.getName());
                break;
            }
            case RUNE: {
                Match.Objective rune = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + rune.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(rune.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(rune.getName().equals("") ? context.getString(R.string.anonymous) : rune.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(context.getResources().getIdentifier("rune_" + rune.getKey(), "drawable", context.getPackageName()));
                Objects.requireNonNull(drawable).setBounds(0, 0, 40, 40);
                r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.append(context.getString(R.string.activated)).append(r).append(context.getString(context.getResources().getIdentifier("rune_" + rune.getKey(), "string", context.getPackageName()))).append(" ").append(context.getString(R.string.rune));
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                break;
            }
            case BUYBACK: {
                Match.Objective buyback = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + buyback.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(buyback.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(buyback.getName().equals("") ? context.getString(R.string.anonymous) : buyback.getName());
                viewHolder.log.setText(R.string.buyback);
                viewHolder.time.setText(tt);
                break;
            }
            case FIRST_BLOOD: {
                Match.Objective fb = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + fb.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(fb.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(fb.getName().equals("") ? context.getString(R.string.anonymous) : fb.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(context.getResources().getIdentifier("bloodsplattersmall2", "drawable", context.getPackageName()));
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
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + aegis.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(aegis.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(aegis.getName().equals("") ? context.getString(R.string.anonymous) : aegis.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(context.getResources().getIdentifier("aegis_icon", "drawable", context.getPackageName()));
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
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + roshan.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(roshan.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(roshan.getName().equals("") ? context.getString(R.string.anonymous) : roshan.getName());
                if (roshan.getTeam() == 2) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("radiant_kill", "drawable", context.getPackageName()))).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("dire_kill", "drawable", context.getPackageName()))).build());
                }
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(context.getResources().getIdentifier("roshan", "drawable", context.getPackageName()));
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
                if (courier.getPlayer_slot() == 5) {
                    courier.setPlayer_slot(6);
                    courier.setName(context.getString(R.string.dire));
                    courier.setHero_id("dire_logo");
                } else {
                    courier.setPlayer_slot(5);
                    courier.setName(context.getString(R.string.radiant));
                    courier.setHero_id("radiant_logo");
                }
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + courier.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(courier.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(courier.getName().equals("") ? context.getString(R.string.anonymous) : courier.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                if (courier.getTeam() == 3) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("radiant_kill", "drawable", context.getPackageName()))).build());
                    Drawable drawable = context.getDrawable(context.getResources().getIdentifier("direcourier", "drawable", context.getPackageName()));
                    Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                    r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.dire_courier));
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("dire_kill", "drawable", context.getPackageName()))).build());
                    Drawable drawable = context.getDrawable(context.getResources().getIdentifier("radiantcourier", "drawable", context.getPackageName()));
                    Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                    r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.radiant_courier));
                }
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.killed.setVisibility(View.GONE);
                break;
            }
            default: {
                TeamFight teamFight = (TeamFight) holder;
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
        ConstraintLayout team_fight_detail;
        TextView radiant_gold_delta;
        TextView radiant_death;
        TextView start_end;
        TextView dire_death;
        TextView dire_gold_delta;
        View itemView;

        TeamFight(View itemView) {
            super(itemView);
            this.itemView = itemView;
            team_fight_detail = itemView.findViewById(R.id.team_fight_detail);
            radiant_gold_delta = itemView.findViewById(R.id.radiant_gold_delta);
            radiant_death = itemView.findViewById(R.id.radiant_death);
            start_end = itemView.findViewById(R.id.start_end);
            dire_death = itemView.findViewById(R.id.dire_death);
            dire_gold_delta = itemView.findViewById(R.id.dire_gold_delta);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (team_fight_detail.getVisibility() == View.GONE) {
                        team_fight_detail.setVisibility(View.VISIBLE);
                    } else {
                        team_fight_detail.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
