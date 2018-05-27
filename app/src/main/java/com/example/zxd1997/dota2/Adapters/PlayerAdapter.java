package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.graphics.Color;
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

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int PLAYER = 1;
    final int RADIANT_HEADER = 2;
    final int DIRE_HEADER = 3;
    Match match;
    List<Match.PPlayer> players;
    Context context;

    public PlayerAdapter(Context context, Match match) {
        this.context = context;
        this.match = match;
        this.players = match.getPlayers();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == RADIANT_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radiant_header, parent, false);
            viewHolder = new RadiantHolder(view);
        } else if (viewType == DIRE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dire_header, parent, false);
            viewHolder = new RadiantHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        switch (position) {
            case 0: {
                type = RADIANT_HEADER;
                break;
            }
            case 6: {
                type = DIRE_HEADER;
                break;
            }
            default:
                type = PLAYER;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            RadiantHolder viewHolder = (RadiantHolder) holder;
            if (match.isRadiant_win()) {
                viewHolder.winorlose.setText("WIN");
                viewHolder.winorlose.setTextColor(Color.WHITE);
            } else {
                viewHolder.winorlose.setText("LOSE");
                viewHolder.winorlose.setTextColor(Color.BLACK);
            }
            viewHolder.total_kill.setText(match.getRadiant_score() + "");
        } else if (position == 6) {
            RadiantHolder viewHolder = (RadiantHolder) holder;
            if (!match.isRadiant_win()) {
                viewHolder.winorlose.setText("WIN");
                viewHolder.winorlose.setTextColor(Color.WHITE);
            } else {
                viewHolder.winorlose.setText("LOSE");
                viewHolder.winorlose.setTextColor(Color.BLACK);
            }
            viewHolder.total_kill.setText(match.getRadiant_score() + "");
        } else {
            if (position <= 5) position -= 1;
            else position -= 2;
            final Match.PPlayer p = players.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.player_hero.setImageResource(context.getResources().getIdentifier("hero_" + p.getHero_id(), "drawable", context.getPackageName()));
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
            viewHolder.k.setText(k);
            viewHolder.gpm.setText("GPM:" + p.getGold_per_min());
            viewHolder.xpm.setText("XPM:" + p.getXp_per_min());
            viewHolder.damage.setText("Damage:" + p.getHero_damage());
            viewHolder.item_0.setImageResource(context.getResources().getIdentifier("item_" + p.getItem_0(), "drawable", context.getPackageName()));
            viewHolder.item_1.setImageResource(context.getResources().getIdentifier("item_" + p.getItem_1(), "drawable", context.getPackageName()));
            viewHolder.item_2.setImageResource(context.getResources().getIdentifier("item_" + p.getItem_2(), "drawable", context.getPackageName()));
            viewHolder.item_3.setImageResource(context.getResources().getIdentifier("item_" + p.getItem_3(), "drawable", context.getPackageName()));
            viewHolder.item_4.setImageResource(context.getResources().getIdentifier("item_" + p.getItem_4(), "drawable", context.getPackageName()));
            viewHolder.item_5.setImageResource(context.getResources().getIdentifier("item_" + p.getItem_5(), "drawable", context.getPackageName()));
        }

    }

    @Override
    public int getItemCount() {
        return players.size() + 2;
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

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView player_hero;
        TextView player;
        TextView k;
        TextView kk;
        TextView xpm;
        TextView gpm;
        TextView damage;
        SimpleDraweeView item_0;
        SimpleDraweeView item_1;
        SimpleDraweeView item_2;
        SimpleDraweeView item_3;
        SimpleDraweeView item_4;
        SimpleDraweeView item_5;

        public ViewHolder(View itemView) {
            super(itemView);
            player_hero = itemView.findViewById(R.id.player_hero);
            player = itemView.findViewById(R.id.player_name);
            k = itemView.findViewById(R.id.k);
            kk = itemView.findViewById(R.id.kk);
            xpm = itemView.findViewById(R.id.xpm);
            gpm = itemView.findViewById(R.id.gpm);
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
