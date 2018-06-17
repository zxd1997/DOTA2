package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.example.zxd1997.dota2.Views.MapView;
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
    private final RecyclerView recyclerView;
    private int expended = -1;

    public LogAdapter(Context context, List<Match.Objective> logs, RecyclerView recyclerView) {
        this.context = context;
        this.logs = logs;
        this.recyclerView = recyclerView;
    }

    public int getExpended() {
        return expended;
    }

    public void setExpended(int expended) {
        this.expended = expended;
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
        StringBuilder tt = Tools.getTime(logs.get(position).getTime());
        Log.d("type", "onBindViewHolder: " + getItemViewType(position));
        switch (getItemViewType(position)) {
            case KILL: {
                Match.Objective kill = logs.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + kill.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(kill.getHero_id(), "drawable", context.getPackageName()))).build());
                if (kill.getPlayer_slot() < 100) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build());
                }
                viewHolder.killed.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + MainActivity.heroes.get(kill.getKey()).getId(), "drawable", context.getPackageName()))).build());
                viewHolder.killed.setVisibility(View.VISIBLE);
                viewHolder.name.setText(kill.getName() == null ? context.getString(R.string.anonymous) : kill.getName());
                viewHolder.time.setText(tt);
                break;
            }
            case TOWER: {
                Match.Objective o = logs.get(position);
                Log.d("type", "onBindViewHolder: " + o.getKey() + " " + o.getUnit());
                ViewHolder viewHolder = (ViewHolder) holder;
                Log.d(TAG, "onBindViewHolder: " + o.getKey());
                viewHolder.log.setText(context.getString(context.getResources().getIdentifier(o.getKey(), "string", context.getPackageName())));
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.time.setText(tt);
                viewHolder.killed.setVisibility(View.GONE);
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(o.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + o.getPlayer_slot(), "color", context.getPackageName())));
                if (o.getPlayer_slot() < 100) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build());
                }
                viewHolder.name.setText(o.getName() == null ? context.getString(R.string.anonymous) : o.getName());
                break;
            }
            case RUNE: {
                Match.Objective rune = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + rune.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(rune.getHero_id(), "drawable", context.getPackageName()))).build());
                Log.d(TAG, "onBindViewHolder: " + rune.getTime() + " " + rune.getType() + " " + rune.getKey());
                viewHolder.name.setText(rune.getName() == null ? context.getString(R.string.anonymous) : rune.getName());
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
                viewHolder.name.setText(buyback.getName() == null ? context.getString(R.string.anonymous) : buyback.getName());
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
                viewHolder.name.setText(fb.getName() == null ? context.getString(R.string.anonymous) : fb.getName());
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
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + aegis.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(aegis.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(aegis.getName() == null ? context.getString(R.string.anonymous) : aegis.getName());
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
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + roshan.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(roshan.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(roshan.getName() == null ? context.getString(R.string.anonymous) : roshan.getName());
                if (roshan.getTeam() == 2) {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build());
                } else {
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build());
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
                    viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_logo)).build());
                    viewHolder.color.setBackgroundColor(context.getResources().getColor(R.color.win));
                    courier.setName(context.getString(R.string.radiant));
                    courier.setHero_id("radiant_logo");
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.radiant_kill)).build());
                    Drawable drawable = context.getDrawable(R.drawable.direcourier);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                    r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.dire_courier));
                } else {
                    viewHolder.killer.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_logo)).build());
                    viewHolder.color.setBackgroundColor(context.getResources().getColor(R.color.lose));
                    courier.setName(context.getString(R.string.dire));
                    courier.setHero_id("dire_logo");
                    viewHolder.kill_sign.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(R.drawable.dire_kill)).build());
                    Drawable drawable = context.getDrawable(R.drawable.radiantcourier);
                    Objects.requireNonNull(drawable).setBounds(0, 0, 45, 45);
                    r.setSpan(new ImageSpan(drawable), 0, r.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    t.append(context.getString(R.string.killed)).append(r).append(context.getString(R.string.radiant_courier));
                }
                viewHolder.name.setText(courier.getName() == null ? context.getString(R.string.anonymous) : courier.getName());
                viewHolder.log.setText(t);
                viewHolder.time.setText(tt);
                viewHolder.log.setVisibility(View.VISIBLE);
                viewHolder.killed.setVisibility(View.GONE);
                break;
            }
            case CHAT: {
                Match.Objective chat = logs.get(position);
                ViewHolderNotKill viewHolder = (ViewHolderNotKill) holder;
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + chat.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.who.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(chat.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.name.setText(chat.getName() == null ? context.getString(R.string.anonymous) : chat.getName());
                viewHolder.log.setText(String.format(":%s", chat.getKey()));
                viewHolder.time.setText(tt);
                break;
            }
            default: {
                final List<Point> points = new ArrayList<>();
                float x;
                float y;
                final TeamFight teamFight = (TeamFight) holder;
                final Match.TeamFight teamfight = (Match.TeamFight) logs.get(position);
                tt.append(" - ").append(Tools.getTime(teamfight.getEnd()));
                teamFight.start_end.setText(tt);
                int radiant_deaths = 0;
                int dire_deaths = 0;
                int radiant_gold_delta = 0;
                int dire_gold_delta = 0;
                int i = 0;
                for (Match.TeamFight.TeamFightPlayer player : teamfight.getPlayers()) {
                    if (i < 5) {
                        radiant_deaths += player.getDeaths();
                        radiant_gold_delta += player.getGold_delta();
                    } else {
                        dire_deaths += player.getDeaths();
                        dire_gold_delta += player.getGold_delta();
                    }
                    if (player.getDeaths() > 0) {
                        for (Map.Entry<Integer, Map<Integer, Integer>> entry : player.getDeaths_pos().entrySet()) {
                            x = entry.getKey();
                            y = 0;
                            for (Map.Entry<Integer, Integer> entry1 : entry.getValue().entrySet()) {
                                y = entry1.getKey();
                            }
                            if (i <= 4)
                                points.add(new Point(x, y, context.getResources().getColor(R.color.win)));
                            else
                                points.add(new Point(x, y, context.getResources().getColor(R.color.lose)));
                        }

                    }
                    i++;
                }
                SpannableString death = new SpannableString(" ");
                Drawable drawable = context.getDrawable(R.drawable.player_death);
                Objects.requireNonNull(drawable).setBounds(0, 0, 40, 42);
                death.setSpan(new ImageSpan(drawable), 0, death.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString gold = new SpannableString(" ");
                drawable = context.getDrawable(R.drawable.gold);
                Objects.requireNonNull(drawable).setBounds(0, 0, 50, 34);
                gold.setSpan(new ImageSpan(drawable), 0, gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString radiant_death = new SpannableString(radiant_deaths + "");
                SpannableString dire_death = new SpannableString(dire_deaths + "");
                SpannableString radiant_gold = new SpannableString(radiant_gold_delta + "");
                SpannableString dire_gold = new SpannableString(dire_gold_delta + "");
                SpannableString up = new SpannableString(" ");
                drawable = context.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
                up.setSpan(new ImageSpan(drawable), 0, up.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString down = new SpannableString(" ");
                drawable = context.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
                down.setSpan(new ImageSpan(drawable), 0, down.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                dire_death.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, dire_death.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                radiant_death.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, radiant_death.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (radiant_gold_delta > 0) {
                    radiant_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, radiant_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    teamFight.radiant_gold_delta.setText(new SpannableStringBuilder().append(up).append(radiant_gold).append(" ").append(gold));
                } else {
                    radiant_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, radiant_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    teamFight.radiant_gold_delta.setText(new SpannableStringBuilder().append(down).append(radiant_gold).append(" ").append(gold));
                }
                if (dire_gold_delta > 0) {
                    dire_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, dire_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    teamFight.dire_gold_delta.setText(new SpannableStringBuilder().append(gold).append(" ").append(dire_gold).append(up));
                } else {
                    dire_gold.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, dire_gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    teamFight.dire_gold_delta.setText(new SpannableStringBuilder().append(gold).append(" ").append(dire_gold).append(down));
                }
                if (radiant_gold_delta - dire_gold_delta > 0) {
                    teamFight.teamfight_win.setText(String.format("%s %ss", context.getString(R.string.radiant), context.getString(R.string.win)));
                    teamFight.teamfight_win.setTextColor(context.getResources().getColor(R.color.win));
                } else {
                    teamFight.teamfight_win.setText(String.format("%s %ss", context.getString(R.string.dire), context.getString(R.string.win)));
                    teamFight.teamfight_win.setTextColor(context.getResources().getColor(R.color.lose));
                }
                if (Math.abs(Math.abs(radiant_gold_delta) - Math.abs(dire_gold_delta)) < 250) {
                    teamFight.teamfight_win.setText(context.getString(R.string.drew));
                    teamFight.teamfight_win.setTextColor(context.getResources().getColor(R.color.high));
                }
                teamFight.radiant_death.setText(new SpannableStringBuilder().append(death).append(" Death:").append(radiant_death));
                teamFight.dire_death.setText(new SpannableStringBuilder().append("Death:").append(dire_death).append(" ").append(death));
                final CastAdapter castAdapter = new CastAdapter(context, teamfight.getPlayers(), 'c');
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 10);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (castAdapter.getItemViewType(position) == -1 || castAdapter.getItemViewType(position) == 11 || castAdapter.getItemViewType(position) == 12) ? 10 : 1;
                    }
                });
                teamFight.teamfight_list.setLayoutManager(gridLayoutManager);
                teamFight.teamfight_list.setAdapter(castAdapter);
                teamFight.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.scrollBy(0, teamFight.tf.getTop());
                        if (teamFight.team_fight_detail.getVisibility() == View.GONE) {
                            Log.d(TAG, "onClick: GONE");
                            if (expended != -1) {
                                TeamFight viewHolder = (TeamFight) recyclerView.getChildViewHolder(recyclerView.getChildAt(expended));
                                viewHolder.team_fight_detail.setVisibility(View.GONE);
                                recyclerView.smoothScrollToPosition(position);
                            }
                            teamFight.map.setPoints(points);
                            teamFight.map.invalidate();
                            expended = position;
                            teamFight.team_fight_detail.setVisibility(View.VISIBLE);
                        } else {
                            Log.d(TAG, "onClick: VISIBLE");
                            teamFight.team_fight_detail.setVisibility(View.GONE);
                            expended = -1;
                        }
//                            notifyItemChanged(position,"");
                        recyclerView.smoothScrollToPosition(position);
                    }

                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView killer;
        final SimpleDraweeView killed;
        final SimpleDraweeView kill_sign;
        final View color;
        final TextView name;
        final TextView log;
        final TextView time;

        public void setVisibility(boolean isVisible) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                param.height = getAdapterPosition() == getItemCount() - 1 ? 1 : 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }

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

        private Point(float x, float y, int color) {
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

    public class ViewHolderNotKill extends RecyclerView.ViewHolder {
        final SimpleDraweeView who;
        final View color;
        final TextView name;
        final TextView log;
        final TextView time;

        public void setVisibility(boolean isVisible) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                param.height = getAdapterPosition() == getItemCount() - 1 ? 1 : 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }

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
        final LinearLayout tf;
        final TextView radiant_gold_delta;
        final TextView radiant_death;
        final TextView start_end;
        final TextView dire_death;
        final TextView dire_gold_delta;
        final View itemView;
        final TextView teamfight_win;
        final MapView map;
        final RecyclerView teamfight_list;
        View team_fight_detail;

        TeamFight(final View itemView) {
            super(itemView);
            tf = itemView.findViewById(R.id.tf);
            this.itemView = itemView;
            teamfight_win = itemView.findViewById(R.id.teamfight_win);
            team_fight_detail = itemView.findViewById(R.id.team_fight_detail);
            radiant_gold_delta = itemView.findViewById(R.id.radiant_gold_delta);
            radiant_death = itemView.findViewById(R.id.radiant_death);
            start_end = itemView.findViewById(R.id.start_end);
            dire_death = itemView.findViewById(R.id.dire_death);
            dire_gold_delta = itemView.findViewById(R.id.dire_gold_delta);
            team_fight_detail = itemView.findViewById(R.id.team_fight_detail);
            map = itemView.findViewById(R.id.imageView6);
            teamfight_list = itemView.findViewById(R.id.team_fight_list);
            teamfight_list.setNestedScrollingEnabled(false);
        }
    }
}
