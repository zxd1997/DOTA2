package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int PLAYER = 1;
    final int RADIANT_HEADER = 2;
    final int DIRE_HEADER = 3;
    final int DETAIL = 4;
    Match match;
    List<Content> contents = new LinkedList<>();
    Context context;

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
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case RADIANT_HEADER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radiant_header, parent, false);
                viewHolder = new RadiantHolder(view);
                break;
            }
            case DIRE_HEADER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dire_header, parent, false);
                viewHolder = new RadiantHolder(view);
                break;
            }
            case PLAYER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            case DETAIL: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_detail, parent, false);
                viewHolder = new DetailHolder(view);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).type;
    }

    public SpannableString getS(double pct) {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case RADIANT_HEADER: {
                RadiantHolder viewHolder = (RadiantHolder) holder;
                if (match.isRadiant_win()) {
                    viewHolder.winorlose.setText("WIN");
                    viewHolder.winorlose.setTextColor(Color.WHITE);
                } else {
                    viewHolder.winorlose.setText("LOSE");
                    viewHolder.winorlose.setTextColor(Color.BLACK);
                }
                viewHolder.total_kill.setText(match.getRadiant_score() + "");
                break;
            }
            case DIRE_HEADER: {
                RadiantHolder viewHolder = (RadiantHolder) holder;
                if (!match.isRadiant_win()) {
                    viewHolder.winorlose.setText("WIN");
                    viewHolder.winorlose.setTextColor(Color.WHITE);
                } else {
                    viewHolder.winorlose.setText("LOSE");
                    viewHolder.winorlose.setTextColor(Color.BLACK);
                }
                viewHolder.total_kill.setText(match.getDire_score() + "");
                break;
            }
            case PLAYER: {
                final Match.PPlayer p = (Match.PPlayer) contents.get(position).object;
                final ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.player_hero.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + p.getHero_id(), "drawable", context.getPackageName()))).build());
                viewHolder.player.setText(p.getPersonaname() == null ? "Anonymous" : p.getPersonaname());
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
                            contents.add(position + 1, new Content(DETAIL, contents.get(position).object));
                            notifyItemInserted(position + 1);
                            notifyItemRangeChanged(position, contents.size());
                            contents.get(position).extended = true;
                        } else {
                            notifyItemRemoved(position + 1);
                            contents.remove(position + 1);
                            notifyItemRangeChanged(position, contents.size());
                            contents.get(position).extended = false;
                        }
                    }
                });
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color", context.getPackageName())));
                viewHolder.k.setText(k);
                double in_battle = p.isRadiant() ? ((double) (p.getKills() + p.getAssists()) / (double) match.getRadiant_score()) * 100 : ((double) (p.getKills() + p.getAssists()) / (double) match.getDire_score()) * 100;
                DecimalFormat df = new DecimalFormat("0.0");
                viewHolder.in_battle.setText("In Battle:" + df.format(in_battle) + "%");
                viewHolder.damage.setText("Damage:" + p.getHero_damage());
                viewHolder.item_0.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getItem_0(), "drawable", context.getPackageName()))).build());
                viewHolder.item_1.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getItem_1(), "drawable", context.getPackageName()))).build());
                viewHolder.item_2.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getItem_2(), "drawable", context.getPackageName()))).build());
                viewHolder.item_3.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getItem_3(), "drawable", context.getPackageName()))).build());
                viewHolder.item_4.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getItem_4(), "drawable", context.getPackageName()))).build());
                viewHolder.item_5.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getItem_5(), "drawable", context.getPackageName()))).build());
                break;
            }
            case DETAIL: {
                final Match.PPlayer p = (Match.PPlayer) contents.get(position).object;
                final DetailHolder viewHolder = (DetailHolder) holder;
                viewHolder.gpm.setText("GPM:" + p.getGold_per_min());
                viewHolder.xpm.setText("XPM:" + p.getXp_per_min());
                viewHolder.level.setText("Level:" + p.getLevel());
                viewHolder.towerDamage.setText("Tower Damage:" + p.getTower_damage());
                viewHolder.heroHealing.setText("Hero Healing:" + p.getHero_healing());
                viewHolder.lastHits.setText("Last Hits:" + p.getLast_hits());
                viewHolder.denies.setText("Denies:" + p.getDenies());
                viewHolder.gpm_ben.setText(new SpannableStringBuilder("GPM:").append(getS(p.getBenchmarks().getGold_per_min().getPct())));
                viewHolder.xpm_ben.setText(new SpannableStringBuilder("XPM:").append(getS(p.getBenchmarks().getXp_per_min().getPct())));
                viewHolder.hdm_ben.setText(new SpannableStringBuilder("Damage/Min:").append(getS(p.getBenchmarks().getHero_damage_per_min().getPct())));
                viewHolder.kdm_ben.setText(new SpannableStringBuilder("Kills/Min:").append(getS(p.getBenchmarks().getKills_per_min().getPct())));
                viewHolder.td_ben.setText(new SpannableStringBuilder("Tower Damage:").append(getS(p.getBenchmarks().getTower_damage().getPct())));
                viewHolder.backpack_0.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getBackpack_0(), "drawable", context.getPackageName()))).build());
                viewHolder.backpack_1.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getBackpack_1(), "drawable", context.getPackageName()))).build());
                viewHolder.backpack_2.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + p.getBackpack_2(), "drawable", context.getPackageName()))).build());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class Content {
        int type;
        Object object;
        boolean extended;

        public Content(int type, Object object) {
            this.extended = false;
            this.type = type;
            this.object = object;
        }
    }

    class RadiantHolder extends RecyclerView.ViewHolder {
        TextView total_kill;
        TextView winorlose;
        public RadiantHolder(View itemView) {
            super(itemView);
            total_kill = itemView.findViewById(R.id.total_kill);
            winorlose = itemView.findViewById(R.id.winorlose);
        }
    }

    class DetailHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView backpack_0;
        SimpleDraweeView backpack_1;
        SimpleDraweeView backpack_2;
        TextView xpm;
        TextView gpm;
        TextView level;
        TextView towerDamage;
        TextView heroHealing;
        TextView lastHits;
        TextView denies;
        TextView gpm_ben;
        TextView xpm_ben;
        TextView kdm_ben;
        TextView hdm_ben;
        TextView td_ben;

        public DetailHolder(View itemView) {
            super(itemView);
            backpack_0 = itemView.findViewById(R.id.backpack_0);
            backpack_1 = itemView.findViewById(R.id.backpack_1);
            backpack_2 = itemView.findViewById(R.id.backpack_2);
            level = itemView.findViewById(R.id.lvl);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView player_hero;
        TextView player;
        TextView k;
        TextView kk;
        TextView in_battle;
        TextView damage;
        SimpleDraweeView item_0;
        SimpleDraweeView item_1;
        SimpleDraweeView item_2;
        SimpleDraweeView item_3;
        SimpleDraweeView item_4;
        SimpleDraweeView item_5;
        View color;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
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
        }
    }
}
