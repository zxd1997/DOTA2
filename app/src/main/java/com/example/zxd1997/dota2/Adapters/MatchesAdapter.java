package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

public class MatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<RecentMatch> recentMatches;
    private final Context context;

    public MatchesAdapter(Context context, List<RecentMatch> recentMatches) {
        this.recentMatches = recentMatches;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("Recycle")
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
        viewHolder.gpm.setText(context.getString(R.string.gpm_) + recentMatch.getGold_per_min());
        viewHolder.xpm.setText(context.getString(R.string.xpm_) + recentMatch.getXp_per_min());
        viewHolder.hero_damage.setText(context.getString(R.string.damage_) + recentMatch.getHero_damage());
        @SuppressLint("Recycle") TypedArray typedArray = context.getResources().obtainTypedArray(R.array.skills);
        viewHolder.skills.setText(typedArray.getText(recentMatch.getSkill()));
        typedArray = context.getResources().obtainTypedArray(R.array.skills_color);
        viewHolder.skills.setTextColor(context.getResources().getColor(typedArray.getResourceId(recentMatch.getSkill(), 0)));
        boolean win;
        win = recentMatch.getPlayer_slot() < 128 && recentMatch.isRadiant_win() || recentMatch.getPlayer_slot() > 127 && !recentMatch.isRadiant_win();
        if (win) {
            viewHolder.winornot.setTextColor(context.getResources().getColor(R.color.win));
            viewHolder.winornot.setText(context.getString(R.string.win));
        } else {
            viewHolder.winornot.setTextColor(context.getResources().getColor(R.color.lose));
            viewHolder.winornot.setText(context.getString(R.string.lose));
        }
        Hero h = null;
        for (Map.Entry<String, Hero> entry : MainActivity.heroes.entrySet()) {
            if (entry.getValue().getId() == recentMatch.getHero_id()) {
                h = entry.getValue();
                break;
            }
        }
        assert h != null;
        viewHolder.hero_name.setText(h.getLocalized_name());
        viewHolder.hero_header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                context.getResources().getIdentifier("hero_" + h.getId(), "drawable", context.getPackageName())
        )).build());
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
        final SimpleDraweeView hero_header;
        final TextView hero_name;
        final TextView winornot;
        final TextView time;
        final TextView game_mode;
        final TextView lobby_type;
        final TextView skills;
        final TextView kda;
        final TextView hero_damage;
        final TextView gpm;
        final TextView xpm;

        ViewHolder(View itemView) {
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
