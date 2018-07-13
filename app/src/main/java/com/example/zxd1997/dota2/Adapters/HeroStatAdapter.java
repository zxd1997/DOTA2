package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.HeroStat;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HeroStatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<HeroStat> heroStats;

    public HeroStatAdapter(Context context, List<HeroStat> heroStats) {
        this.context = context;
        this.heroStats = heroStats;
    }

    @Override
    public int getItemViewType(int position) {
        return heroStats.get(position).getId() > 0 ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_stats_list, parent, false));
        else
            return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            ViewHolder viewHolder = (ViewHolder) holder;
            HeroStat heroStat = heroStats.get(position);
            if (heroStat.getRank() == 9) {
                viewHolder.ban.setVisibility(View.VISIBLE);
                viewHolder.t3.setVisibility(View.VISIBLE);
                viewHolder.ban.setText(String.valueOf(heroStat.getBan()));
            } else {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
            }
            Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("rank" + heroStat.getRank(), R.drawable.class))).build(), viewHolder.rank);
            TypedArray typedArray = context.getResources().obtainTypedArray(R.array.tier_names);
            viewHolder.rank_name.setText(typedArray.getText(heroStat.getRank()));
            typedArray.recycle();
            viewHolder.pick.setText(heroStat.getPick() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) heroStat.getPick() / 1000) : String.valueOf(heroStat.getPick()));
            viewHolder.winrate.setText(String.format("%.2f%%", heroStat.getWinrate()));
        } else {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.text.setText(heroStats.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return heroStats.size();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        final TextView text;

        HeaderHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.header_text);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView rank;
        TextView rank_name;
        TextView pick;
        TextView winrate;
        TextView ban;
        TextView t3;

        public ViewHolder(View itemView) {
            super(itemView);
            t3 = itemView.findViewById(R.id.text3);
            rank = itemView.findViewById(R.id.rank_icon);
            rank_name = itemView.findViewById(R.id.rank_name);
            pick = itemView.findViewById(R.id.pick);
            winrate = itemView.findViewById(R.id.winrate);
            ban = itemView.findViewById(R.id.ban);
        }
    }
}
