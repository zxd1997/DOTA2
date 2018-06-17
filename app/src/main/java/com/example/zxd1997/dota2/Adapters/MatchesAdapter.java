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
import com.example.zxd1997.dota2.Utils.Tools;
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
        viewHolder.lobby_type.setText("");
        @SuppressLint("Recycle") TypedArray typedArray = context.getResources().obtainTypedArray(R.array.skills);
        viewHolder.skills.setText(typedArray.getText(recentMatch.getSkill()));
        typedArray = context.getResources().obtainTypedArray(R.array.skills_color);
        viewHolder.skills.setTextColor(context.getResources().getColor(typedArray.getResourceId(recentMatch.getSkill(), 0)));
        boolean win;
        win = recentMatch.getPlayer_slot() < 128 && recentMatch.isRadiant_win() || recentMatch.getPlayer_slot() > 127 && !recentMatch.isRadiant_win();
        if (win) {
            viewHolder.win_or_not.setTextColor(context.getResources().getColor(R.color.win));
            viewHolder.win_or_not.setText(context.getString(R.string.win));
        } else {
            viewHolder.win_or_not.setTextColor(context.getResources().getColor(R.color.lose));
            viewHolder.win_or_not.setText(context.getString(R.string.lose));
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
        typedArray = context.getResources().obtainTypedArray(R.array.game_mode);
        viewHolder.game_mode.setText(typedArray.getText(recentMatch.getGame_mode()));
        viewHolder.time.setText(Tools.getBefore(recentMatch.getStart_time()));
    }

    @Override
    public int getItemCount() {
        return recentMatches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView hero_header;
        final TextView hero_name;
        final TextView win_or_not;
        final TextView time;
        final TextView game_mode;
        final TextView lobby_type;
        final TextView skills;
        final TextView kda;

        ViewHolder(View itemView) {
            super(itemView);
            hero_header = itemView.findViewById(R.id.hero_header);
            hero_name = itemView.findViewById(R.id.hero_name);
            win_or_not = itemView.findViewById(R.id.winornot);
            time = itemView.findViewById(R.id.time);
            game_mode = itemView.findViewById(R.id.game_mode);
            lobby_type = itemView.findViewById(R.id.lobby_type);
            skills = itemView.findViewById(R.id.skills);
            kda = itemView.findViewById(R.id.kda);
        }
    }
}
