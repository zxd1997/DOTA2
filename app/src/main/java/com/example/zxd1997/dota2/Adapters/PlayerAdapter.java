package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int PLAYER = 1;
    private final int RADIANT_HEADER = 2;
    private final int DIRE_HEADER = 3;
    private final Match match;
    private final List<Content> contents = new LinkedList<>();
    private final Context context;

    public PlayerAdapter(Context context, Match match) {
        this.context = context;
        this.match = match;
        contents.add(new Content(RADIANT_HEADER, null));
        for (int i = 0; i < 5; i++) {
            contents.add(new Content(PLAYER, match.getPlayers().get(i)));
        }
        contents.add(new Content(DIRE_HEADER, null));
        for (int i = 5; i < match.getPlayers().size(); i++) {
            contents.add(new Content(PLAYER, match.getPlayers().get(i)));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RADIANT_HEADER: {
                return new RadiantHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.radiant_header, parent, false));
            }
            case DIRE_HEADER: {
                return new RadiantHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.radiant_header, parent, false));
            }
            default: {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).type;
    }

    private SpannableString getS(double pct) {
        pct *= 100;
        DecimalFormat df = new DecimalFormat("0.0");
        SpannableString t = new SpannableString(df.format(pct) + "%");
        if (pct >= 80) {
            t.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (pct >= 60) {
            t.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.slot_0)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (pct >= 40) {
            t.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.high)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (pct >= 20) {
            t.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.very_high)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            t.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return t;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        switch (getItemViewType(position)) {
            case RADIANT_HEADER: {
                RadiantHolder viewHolder = (RadiantHolder) holder;
                viewHolder.name.setText(R.string.radiant);
                if (match.isRadiant_win()) {
                    viewHolder.win_or_lose.setText(context.getString(R.string.win));
                    viewHolder.win_or_lose.setTextColor(Color.WHITE);
                } else {
                    viewHolder.win_or_lose.setText(context.getString(R.string.lose));
                    viewHolder.win_or_lose.setTextColor(Color.WHITE);
                }
                viewHolder.itemView.setBackground(context.getResources().getDrawable(R.drawable.radiant_header));
                viewHolder.total_kill.setText(String.valueOf(match.getRadiant_score()));
                break;
            }
            case DIRE_HEADER: {
                RadiantHolder viewHolder = (RadiantHolder) holder;
                viewHolder.name.setText(R.string.dire);
                if (!match.isRadiant_win()) {
                    viewHolder.win_or_lose.setText(context.getString(R.string.win));
                    viewHolder.win_or_lose.setTextColor(Color.WHITE);
                } else {
                    viewHolder.win_or_lose.setText(context.getString(R.string.lose));
                    viewHolder.win_or_lose.setTextColor(Color.WHITE);
                }
                viewHolder.itemView.setBackground(context.getResources().getDrawable(R.drawable.dire_header));
                viewHolder.total_kill.setText(String.valueOf(match.getDire_score()));
                break;
            }
            case PLAYER: {
                final Match.PPlayer p = (Match.PPlayer) contents.get(position).object;
                final ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.player_hero.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + p.getHero_id(), R.drawable.class))).build());
                viewHolder.player.setText(p.getName() != null ? p.getName() : p.getPersonaname() == null || p.getPersonaname().equals("") ? context.getResources().getString(R.string.anonymous) : p.getPersonaname());
                if (p.getName() != null || p.getPersonaname() != null) {
                    viewHolder.player.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PlayerActivity.class);
                            intent.putExtra("id", p.getAccount_id());
                            context.startActivity(intent);
                        }
                    });
                }
                SpannableStringBuilder k = new SpannableStringBuilder();
                SpannableString t1 = new SpannableString("K");
                t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                k.append(t1).append("/");
                t1 = new SpannableString("D");
                t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                k.append(t1).append("/");
                t1 = new SpannableString("A");
                t1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                k.append(t1);
                viewHolder.kk.setText(k);
                k = new SpannableStringBuilder();
                t1 = new SpannableString(p.getKills() + "");
                t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                k.append(t1).append("/");
                t1 = new SpannableString(p.getDeaths() + "");
                t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                k.append(t1).append("/");
                t1 = new SpannableString(p.getAssists() + "");
                t1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                k.append(t1);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!contents.get(position).extended) {
                            contents.get(position).extended = true;
                            viewHolder.cardView.setVisibility(View.VISIBLE);
                            notifyItemChanged(position, "");
                        } else {
                            contents.get(position).extended = false;
                            viewHolder.cardView.setVisibility(View.GONE);
                            notifyItemChanged(position, "");
                        }
                    }
                });
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class)));
                viewHolder.k.setText(k);
                float in_battle = p.isRadiant() ? (float) (p.getKills() + p.getAssists()) / match.getRadiant_score() * 100 : (float) (p.getKills() + p.getAssists()) / match.getDire_score() * 100;
                viewHolder.in_battle.setText(String.format("%s%%", context.getString(R.string.in_battle, in_battle)));
                viewHolder.damage.setText(context.getString(R.string.damage_, p.getHero_damage()));
                viewHolder.item_0.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getItem_0(), R.drawable.class))).build());
                viewHolder.item_1.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getItem_1(), R.drawable.class))).build());
                viewHolder.item_2.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getItem_2(), R.drawable.class))).build());
                viewHolder.item_3.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getItem_3(), R.drawable.class))).build());
                viewHolder.item_4.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getItem_4(), R.drawable.class))).build());
                viewHolder.item_5.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getItem_5(), R.drawable.class))).build());
                if (p.getLane_role() > 0) {
                    viewHolder.stuns.setText(context.getString(R.string.stuns, p.getStuns()));
                    SpannableStringBuilder t = new SpannableStringBuilder();
                    switch (p.getLane_role()) {
                        case 1: {
                            SpannableString tt = new SpannableString(" ");
                            Drawable drawable = context.getResources().getDrawable(R.drawable.lane_1);
                            drawable.setBounds(0, 0, 28, 28);
                            tt.setSpan(new ImageSpan(drawable), 0, tt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t.append(tt);
                            break;
                        }
                        case 2: {
                            SpannableString tt = new SpannableString(" ");
                            Drawable drawable = context.getResources().getDrawable(R.drawable.lane_2);
                            drawable.setBounds(0, 0, 28, 28);
                            tt.setSpan(new ImageSpan(drawable), 0, tt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t.append(tt);
                            break;
                        }
                        case 3: {
                            SpannableString tt = new SpannableString(" ");
                            Drawable drawable = context.getResources().getDrawable(R.drawable.lane_3);
                            drawable.setBounds(0, 0, 28, 28);
                            tt.setSpan(new ImageSpan(drawable), 0, tt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t.append(tt);
                            break;
                        }
                        case 4: {
                            SpannableString tt = new SpannableString(" ");
                            Drawable drawable = context.getResources().getDrawable(R.drawable.lane_4);
                            drawable.setBounds(0, 0, 28, 28);
                            tt.setSpan(new ImageSpan(drawable), 0, tt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            t.append(tt);
                            break;
                        }
                    }
                    if (p.isIs_roaming()) {
                        SpannableString tt = new SpannableString(" ");
                        Drawable drawable = context.getResources().getDrawable(R.drawable.lane_roam);
                        drawable.setBounds(0, 0, 28, 28);
                        tt.setSpan(new ImageSpan(drawable), 0, tt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        t.append(" ").append(tt);
                    }
                    viewHolder.lane.setText(t);
                }
                viewHolder.gpm.setText(context.getString(R.string.gpm, p.getGold_per_min()));
                viewHolder.xpm.setText(context.getString(R.string.xpm, p.getXp_per_min()));
                viewHolder.level.setText(context.getString(R.string.lvl, p.getLevel()));
                viewHolder.towerDamage.setText(context.getString(R.string.td, p.getTower_damage()));
                viewHolder.heroHealing.setText(context.getString(R.string.hh, p.getHero_healing()));
                viewHolder.lastHits.setText(context.getString(R.string.lh, p.getLast_hits()));
                viewHolder.denies.setText(context.getString(R.string.dn, p.getDenies()));
                viewHolder.gpm_ben.setText(new SpannableStringBuilder("GPM:").append(getS(p.getBenchmarks().getGold_per_min().getPct())));
                viewHolder.xpm_ben.setText(new SpannableStringBuilder("XPM:").append(getS(p.getBenchmarks().getXp_per_min().getPct())));
                viewHolder.hdm_ben.setText(new SpannableStringBuilder(context.getString(R.string.dpm)).append(getS(p.getBenchmarks().getHero_damage_per_min().getPct())));
                viewHolder.kdm_ben.setText(new SpannableStringBuilder(context.getString(R.string.kpm)).append(getS(p.getBenchmarks().getKills_per_min().getPct())));
                viewHolder.td_ben.setText(new SpannableStringBuilder("Tower Damage:").append(getS(p.getBenchmarks().getTower_damage().getPct())));
                viewHolder.backpack_0.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getBackpack_0(), R.drawable.class))).build());
                viewHolder.backpack_1.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getBackpack_1(), R.drawable.class))).build());
                viewHolder.backpack_2.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + p.getBackpack_2(), R.drawable.class))).build());
                if (p.getAdditional_units() != null) {
                    Match.PPlayer.Unit unit = p.getAdditional_units().get(0);
                    if (unit.getUnitname().equals("spirit_bear")) {
                        viewHolder.bear.setVisibility(View.VISIBLE);
                        viewHolder.bear_item_0 = viewHolder.itemView.findViewById(R.id.bear_item_0);
                        viewHolder.bear_item_1 = viewHolder.itemView.findViewById(R.id.bear_item_1);
                        viewHolder.bear_item_2 = viewHolder.itemView.findViewById(R.id.bear_item_2);
                        viewHolder.bear_item_3 = viewHolder.itemView.findViewById(R.id.bear_item_3);
                        viewHolder.bear_item_4 = viewHolder.itemView.findViewById(R.id.bear_item_4);
                        viewHolder.bear_item_5 = viewHolder.itemView.findViewById(R.id.bear_item_5);
                        viewHolder.bear_backpack_0 = viewHolder.itemView.findViewById(R.id.bear_backpack_0);
                        viewHolder.bear_backpack_1 = viewHolder.itemView.findViewById(R.id.bear_backpack_1);
                        viewHolder.bear_backpack_2 = viewHolder.itemView.findViewById(R.id.bear_backpack_2);
                        viewHolder.bear_item_0.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getItem_0(), R.drawable.class))).build());
                        viewHolder.bear_item_1.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getItem_1(), R.drawable.class))).build());
                        viewHolder.bear_item_2.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getItem_2(), R.drawable.class))).build());
                        viewHolder.bear_item_3.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getItem_3(), R.drawable.class))).build());
                        viewHolder.bear_item_4.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getItem_4(), R.drawable.class))).build());
                        viewHolder.bear_item_5.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getItem_5(), R.drawable.class))).build());
                        viewHolder.bear_backpack_0.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getBackpack_0(), R.drawable.class))).build());
                        viewHolder.bear_backpack_1.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getBackpack_1(), R.drawable.class))).build());
                        viewHolder.bear_backpack_2.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + unit.getBackpack_2(), R.drawable.class))).build());
                    }
                }
                if (p.getPermanent_buffs() != null && p.getPermanent_buffs().size() > 0) {
                    final int HEADER = -1;
                    final int BUFF = 8;
                    TypedArray typedArray = Objects.requireNonNull(context).getResources().obtainTypedArray(R.array.buff);
                    int i = 0;
                    List<Cast> buffs = new ArrayList<>();
                    for (Match.PPlayer.Buff buff : p.getPermanent_buffs()) {
                        if (buff.getPermanent_buff() < 6) {
                            if (i == 0)
                                buffs.add(new Cast(Color.BLACK, context.getString(R.string.buffs), HEADER));
                            buffs.add(new Cast(buff.getStack_count(), String.valueOf(typedArray.getResourceId(buff.getPermanent_buff(), 0)), BUFF));
                            i++;
                        }
                    }
                    typedArray.recycle();
                    if (buffs.size() > 0) {
                        viewHolder.buff.setVisibility(View.VISIBLE);
                        viewHolder.recyclerView = viewHolder.itemView.findViewById(R.id.buffs);
                        final CastAdapter castAdapter = new CastAdapter(context, buffs);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return castAdapter.getItemViewType(position) == -1 ? 3 : 1;
                            }
                        });
                        viewHolder.recyclerView.setLayoutManager(gridLayoutManager);
                        viewHolder.recyclerView.setAdapter(castAdapter);
                        viewHolder.recyclerView.setNestedScrollingEnabled(false);
                    }
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class Content {
        final int type;
        final Object object;
        boolean extended;

        Content(int type, Object object) {
            this.extended = false;
            this.type = type;
            this.object = object;
        }
    }

    class RadiantHolder extends RecyclerView.ViewHolder {
        final TextView total_kill;
        final TextView win_or_lose;
        final TextView name;

        RadiantHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView8);
            total_kill = itemView.findViewById(R.id.total_kill);
            win_or_lose = itemView.findViewById(R.id.winorlose);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView player_hero;
        final TextView player;
        final TextView k;
        final TextView kk;
        final TextView in_battle;
        final TextView damage;
        final TextView lane;
        final SimpleDraweeView item_0;
        final SimpleDraweeView item_1;
        final SimpleDraweeView item_2;
        final SimpleDraweeView item_3;
        final SimpleDraweeView item_4;
        final SimpleDraweeView item_5;
        final TextView stuns;
        final View color;
        final View itemView;
        final ConstraintLayout cardView;
        final SimpleDraweeView backpack_0;
        final SimpleDraweeView backpack_1;
        final SimpleDraweeView backpack_2;
        final TextView xpm;
        final TextView gpm;
        final TextView level;
        final TextView towerDamage;
        final TextView heroHealing;
        final TextView lastHits;
        final TextView denies;
        final TextView gpm_ben;
        final TextView xpm_ben;
        final TextView kdm_ben;
        final TextView hdm_ben;
        final TextView td_ben;
        SimpleDraweeView bear_item_0;
        SimpleDraweeView bear_item_1;
        SimpleDraweeView bear_item_2;
        SimpleDraweeView bear_item_3;
        SimpleDraweeView bear_item_4;
        SimpleDraweeView bear_item_5;
        SimpleDraweeView bear_backpack_0;
        SimpleDraweeView bear_backpack_1;
        SimpleDraweeView bear_backpack_2;
        RecyclerView recyclerView;
        final ViewStub buff;
        final ViewStub bear;
        ViewHolder(View itemView) {
            super(itemView);
            bear=itemView.findViewById(R.id.bear);
            buff=itemView.findViewById(R.id.buff_rec);
            this.itemView = itemView;
            lane = itemView.findViewById(R.id.lane);
            color = itemView.findViewById(R.id.color_hero);
            player_hero = itemView.findViewById(R.id.player_hero);
            player = itemView.findViewById(R.id.player_name);
            k = itemView.findViewById(R.id.k);
            kk = itemView.findViewById(R.id.kk);
            in_battle = itemView.findViewById(R.id.in_battle);
            damage = itemView.findViewById(R.id.damage);
            item_0 = itemView.findViewById(R.id.item_0);
            item_1 = itemView.findViewById(R.id.item_1);
            item_2 = itemView.findViewById(R.id.item_2);
            item_3 = itemView.findViewById(R.id.item_3);
            item_4 = itemView.findViewById(R.id.item_4);
            item_5 = itemView.findViewById(R.id.item_5);
            cardView = itemView.findViewById(R.id.player_detail);
            backpack_0 = itemView.findViewById(R.id.backpack_0);
            backpack_1 = itemView.findViewById(R.id.backpack_1);
            backpack_2 = itemView.findViewById(R.id.backpack_2);
            level = itemView.findViewById(R.id.lvl);
            stuns = itemView.findViewById(R.id.stuns);
            towerDamage = itemView.findViewById(R.id.td);
            heroHealing = itemView.findViewById(R.id.hh);
            lastHits = itemView.findViewById(R.id.lh);
            denies = itemView.findViewById(R.id.dn);
            xpm = itemView.findViewById(R.id.xpm);
            gpm = itemView.findViewById(R.id.gpm);
            gpm_ben = itemView.findViewById(R.id.gpm_ben);
            xpm_ben = itemView.findViewById(R.id.xpm_ben);
            kdm_ben = itemView.findViewById(R.id.kpm_ben);
            hdm_ben = itemView.findViewById(R.id.hdm_ben);
            td_ben = itemView.findViewById(R.id.td_ben);
        }
    }
}
