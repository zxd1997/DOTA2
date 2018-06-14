package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Objects;

public class TeamFightPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Match.TeamFight.TeamFightPlayer> teamFightPlayers;

    public TeamFightPlayerAdapter(Context context, List<Match.TeamFight.TeamFightPlayer> teamFightPlayers) {
        this.context = context;
        this.teamFightPlayers = teamFightPlayers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_fight_player, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Match.TeamFight.TeamFightPlayer p = teamFightPlayers.get(position);
        viewHolder.name.setText(p.getPersonaname() == null ? context.getString(R.string.anonymous) : p.getPersonaname());
        viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color", context.getPackageName())));
        if (p.getDeaths() > 0) viewHolder.death.setVisibility(View.VISIBLE);
        viewHolder.header.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                context.getResources().getIdentifier(p.getHero_id(), "drawable", context.getPackageName()))).build());
        SpannableString gold = new SpannableString(" ");
        Drawable drawable = context.getDrawable(R.drawable.gold);
        Objects.requireNonNull(drawable).setBounds(0, 0, 50, 34);
        gold.setSpan(new ImageSpan(drawable), 0, gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString up = new SpannableString(" ");
        drawable = context.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
        Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
        up.setSpan(new ImageSpan(drawable), 0, up.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString down = new SpannableString(" ");
        drawable = context.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
        Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
        down.setSpan(new ImageSpan(drawable), 0, down.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString gold_delta = new SpannableString(p.getGold_delta() + "");
        if (p.getGold_delta() > 0) {
            gold_delta.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, gold_delta.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.gold_delta.setText(new SpannableStringBuilder().append(up).append(gold_delta).append(" ").append(gold));
        } else {
            gold_delta.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, gold_delta.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.gold_delta.setText(new SpannableStringBuilder().append(down).append(gold_delta).append(" ").append(gold));
        }
        viewHolder.damage.setText(context.getString(R.string.damage_, p.getDamage()));
        viewHolder.healing.setText(context.getString(R.string.healing, p.getHealing()));
        viewHolder.xp_delta.setText(context.getString(R.string.xp_earn, p.getXp_delta()));
    }

    @Override
    public int getItemCount() {
        return teamFightPlayers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        View color;
        TextView name;
        ImageView death;
        TextView gold_delta;
        TextView xp_delta;
        TextView damage;
        TextView healing;
        RecyclerView team_fight_cast;

        public ViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.team_fight_header);
            color = itemView.findViewById(R.id.team_fight_color);
            death = itemView.findViewById(R.id.death);
            name = itemView.findViewById(R.id.team_fight_player);
            gold_delta = itemView.findViewById(R.id.gold_delta);
            xp_delta = itemView.findViewById(R.id.xp_delta);
            damage = itemView.findViewById(R.id.team_fight_damage);
            healing = itemView.findViewById(R.id.team_fight_healing);
            team_fight_cast = itemView.findViewById(R.id.team_fight_cast);
        }
    }
}
