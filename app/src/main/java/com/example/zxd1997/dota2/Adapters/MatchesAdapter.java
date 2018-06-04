package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

public class MatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<RecentMatch> recentMatches;
    Context context;

    public MatchesAdapter(Context context, List<RecentMatch> recentMatches) {
        this.recentMatches = recentMatches;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_list, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final RecentMatch recentMatch = recentMatches.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MatchActivity.class);
                intent.putExtra("id", recentMatch.getMatch_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
        SpannableStringBuilder k = new SpannableStringBuilder();
        SpannableString t1 = new SpannableString(recentMatch.getKills() + "");
        t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        k.append(t1).append("/");
        t1 = new SpannableString(recentMatch.getDeaths() + "");
        t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        k.append(t1).append("/");
        t1 = new SpannableString(recentMatch.getAssists() + "");
        t1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        k.append(t1);
        viewHolder.kda.setText(k);
        viewHolder.gpm.setText("GPM:" + recentMatch.getGold_per_min());
        viewHolder.xpm.setText("XPM:" + recentMatch.getXp_per_min());
        viewHolder.hero_damage.setText("Damage:" + recentMatch.getHero_damage());
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.skills);
        viewHolder.skills.setText(typedArray.getText(recentMatch.getSkill()));
        typedArray = context.getResources().obtainTypedArray(R.array.skills_color);
        viewHolder.skills.setTextColor(context.getResources().getColor(typedArray.getResourceId(recentMatch.getSkill(), 0)));
        boolean win;
        if (recentMatch.getPlayer_slot() < 128 && recentMatch.isRadiant_win()) {
            win = true;
        } else if (recentMatch.getPlayer_slot() > 127 && !recentMatch.isRadiant_win()) {
            win = true;
        } else win = false;
        if (win) {
            viewHolder.winornot.setTextColor(context.getResources().getColor(R.color.win));
            viewHolder.winornot.setText("Win");
        } else {
            viewHolder.winornot.setTextColor(context.getResources().getColor(R.color.lose));
            viewHolder.winornot.setText("Lose");
        }
        Hero h = null;
        for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
            if (entry.getValue().getId() == recentMatch.getHero_id()) {
                h = entry.getValue();
                break;
            }
        }
        viewHolder.hero_name.setText(h.getLocalized_name());
        viewHolder.hero_header.setImageResource(context.getResources().getIdentifier("hero_" + h.getId(), "drawable", context.getPackageName()));
        String tmp;
        long now = System.currentTimeMillis() / 1000;
        long year = (now - recentMatch.getStart_time()) / (3600 * 24 * 30 * 12);
        long month = (now - recentMatch.getStart_time()) / (3600 * 24 * 30);
        long day = (now - recentMatch.getStart_time()) / (3600 * 24);
        long hour = (now - recentMatch.getStart_time()) / 3600;
        long minute = (now - recentMatch.getStart_time()) / 60;
        if (year > 0) {
            tmp = year + " Years ago";
        } else if (month > 0) {
            tmp = month + " Months ago";
        } else if (day > 0) {
            tmp = day + " Days ago";
        } else if (hour > 0) {
            tmp = hour + " Hours ago";
        } else if (minute > 3) {
            tmp = minute + "Minutes ago";
        } else tmp = "Just Now";
        typedArray = context.getResources().obtainTypedArray(R.array.game_mode);
        viewHolder.game_mode.setText(typedArray.getText(recentMatch.getGame_mode()));
        viewHolder.time.setText(tmp);
    }

    @Override
    public int getItemCount() {
        return recentMatches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView hero_header;
        TextView hero_name;
        TextView winornot;
        TextView time;
        TextView game_mode;
        TextView lobby_type;
        TextView skills;
        TextView kda;
        TextView hero_damage;
        TextView gpm;
        TextView xpm;

        public ViewHolder(View itemView) {
            super(itemView);
            hero_header = itemView.findViewById(R.id.hero_header);
            hero_name = itemView.findViewById(R.id.hero_name);
            winornot = itemView.findViewById(R.id.winornot);
            time = itemView.findViewById(R.id.time);
            game_mode = itemView.findViewById(R.id.game_mode);
            lobby_type = itemView.findViewById(R.id.lobby_type);
            skills = itemView.findViewById(R.id.skills);
            kda = itemView.findViewById(R.id.kda);
            hero_damage = itemView.findViewById(R.id.hero_damage);
            gpm = itemView.findViewById(R.id.gpm);
            xpm = itemView.findViewById(R.id.xpm);
        }
    }
}
