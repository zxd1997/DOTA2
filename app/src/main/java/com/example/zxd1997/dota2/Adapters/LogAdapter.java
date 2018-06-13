package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int KILL = 0;
    private final int RUNE = 1;
    private final int BUYBACK = 2;
    private final int ROSHAN = 3;
    private final int COURIER = 4;
    private final int TEAM_FIGHT = 5;
    private final int TOWER = 6;
    private final int FIRST_BLOOD = 7;
    private final int AEGIS = 8;
    Context context;
    List<Match.Objective> logs;

    public LogAdapter(Context context, List<Match.Objective> logs) {
        this.context = context;
        this.logs = logs;
    }

    @Override
    public int getItemViewType(int position) {
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
                return TEAM_FIGHT;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case KILL:
            case TOWER:
            case COURIER:
            case ROSHAN: {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_kill, parent, false));
            }
            case RUNE:
            case BUYBACK:
            case AEGIS: {
                return new ViewHolderNotKill(LayoutInflater.from(parent.getContext()).inflate(R.layout.log_log, parent, false));
            }
            default:
                return null;
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
        switch (getItemViewType(position)) {
            case KILL: {
                Match.PPlayer.PObjective kill = (Match.PPlayer.PObjective) logs.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + kill.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + kill.getHero_id(), "drawable", context.getPackageName()))).build());
                if (kill.getPlayer_slot() < 100) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("radiant_kill", "drawable", context.getPackageName()))).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                            context.getResources().getIdentifier("dire_kill", "drawable", context.getPackageName()))).build());
                }
                viewHolder.killed.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + kill.getKey(), "drawable", context.getPackageName()))).build());
                viewHolder.killed.setVisibility(View.VISIBLE);
                viewHolder.name.setText(kill.getName().equals("") ? context.getString(R.string.anonymous) : kill.getName());
                viewHolder.time.setText(tt);
                break;
            }
            case TOWER: {
                break;
            }
            case RUNE: {
                Match.PPlayer.PObjective rune = (Match.PPlayer.PObjective) logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + rune.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + rune.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(rune.getName().equals("") ? context.getString(R.string.anonymous) : rune.getName());
                SpannableStringBuilder t = new SpannableStringBuilder();
                SpannableString r = new SpannableString(" ");
                Drawable drawable = context.getDrawable(context.getResources().getIdentifier("rune_" + rune.getKey(), "drawable", context.getPackageName()));
                drawable.setBounds(0, 0, 40, 40);
                r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.append("Activated").append(r).append(context.getString(context.getResources().getIdentifier("rune_" + rune.getKey(), "string", context.getPackageName())) + " rune");
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                break;
            }
            case BUYBACK: {
                Match.PPlayer.PObjective buyback = (Match.PPlayer.PObjective) logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + buyback.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + buyback.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(buyback.getName().equals("") ? context.getString(R.string.anonymous) : buyback.getName());
                viewHolder.log.setText(R.string.buyback);
                viewHolder.time.setText(tt);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView killer;
        SimpleDraweeView killed;
        SimpleDraweeView kill_sign;
        View color;
        TextView name;
        TextView log;
        TextView time;

        public ViewHolder(View itemView) {
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
        SimpleDraweeView who;
        View color;
        TextView name;
        TextView log;
        TextView time;

        public ViewHolderNotKill(View itemView) {
            super(itemView);
            who = itemView.findViewById(R.id.log_picker);
            color = itemView.findViewById(R.id.log_color);
            name = itemView.findViewById(R.id.log_name);
            log = itemView.findViewById(R.id.do_what);
            time = itemView.findViewById(R.id.log_time);
        }
    }
}
