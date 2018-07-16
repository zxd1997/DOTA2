package com.example.zxd1997.dota2.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.HeroActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HeroDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int type;
    private Context context;
    private List<Hero> heroes;

    public HeroDataAdapter(int type, Context context, List<Hero> heroes) {
        this.type = type;
        this.context = context;
        this.heroes = heroes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_data_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Hero hero = heroes.get(position);
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.heroes);
        viewHolder.name.setText(typedArray.getText(hero.getId()));
        typedArray.recycle();
        viewHolder.itemView.setOnClickListener(v -> {
            ViewCompat.setTransitionName(viewHolder.header, "hero_" + hero.getId());
            Intent intent = new Intent(context, HeroActivity.class);
            intent.putExtra("id", hero.getId());
            intent.putExtra("name", viewHolder.name.getText());
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, viewHolder.header, ViewCompat.getTransitionName(viewHolder.header)).toBundle());
        });
        Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + hero.getId(), R.drawable.class))).build(), viewHolder.header);
        switch (type) {
            case 1: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getTotal_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getTotal_picks() / 1000) : String.valueOf(hero.getTotal_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getTotal_wins() / hero.getTotal_picks() * 100));
                break;
            }
            case 2: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getHerald_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getHerald_picks() / 1000) : String.valueOf(hero.getHerald_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getHerald_wins() / hero.getHerald_picks() * 100));
                break;
            }
            case 3: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getGuardian_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getGuardian_picks() / 1000) : String.valueOf(hero.getGuardian_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getGuardian_wins() / hero.getGuardian_picks() * 100));
                break;
            }
            case 4: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getCrusader_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getCrusader_picks() / 1000) : String.valueOf(hero.getCrusader_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getCrusader_wins() / hero.getCrusader_picks() * 100));
                break;
            }
            case 5: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getArchon_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getArchon_picks() / 1000) : String.valueOf(hero.getArchon_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getArchon_wins() / hero.getArchon_picks() * 100));
                break;
            }
            case 6: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getLegend_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getLegend_picks() / 1000) : String.valueOf(hero.getLegend_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getLegend_wins() / hero.getLegend_picks() * 100));
                break;
            }
            case 7: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getAncient_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getAncient_picks() / 1000) : String.valueOf(hero.getAncient_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getAncient_wins() / hero.getAncient_picks() * 100));
                break;
            }
            case 8: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getDivine_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getDivine_picks() / 1000) : String.valueOf(hero.getDivine_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getDivine_wins() / hero.getDivine_picks() * 100));
                break;
            }
            case 9: {
                viewHolder.ban.setVisibility(View.GONE);
                viewHolder.t3.setVisibility(View.GONE);
                viewHolder.pick.setText(hero.getImmortal_picks() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getImmortal_picks() / 1000) : String.valueOf(hero.getImmortal_picks()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getImmortal_wins() / hero.getImmortal_picks() * 100));
                break;
            }
            case 10: {
                viewHolder.pick.setText(hero.getPro_pick() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) hero.getPro_pick() / 1000) : String.valueOf(hero.getPro_pick()));
                viewHolder.winrate.setText(String.format("%.2f%%", (float) hero.getPro_win() / hero.getPro_pick() * 100));
                viewHolder.ban.setVisibility(View.VISIBLE);
                viewHolder.t3.setVisibility(View.VISIBLE);
                viewHolder.ban.setText(String.valueOf(hero.getPro_ban()));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;
        TextView pick;
        TextView winrate;
        TextView ban;
        TextView t3;

        public ViewHolder(View itemView) {
            super(itemView);
            t3 = itemView.findViewById(R.id.text3);
            header = itemView.findViewById(R.id.hero_data_header);
            name = itemView.findViewById(R.id.hero_data_name);
            pick = itemView.findViewById(R.id.hero_pick);
            winrate = itemView.findViewById(R.id.hero_winrate);
            ban = itemView.findViewById(R.id.hero_ban);
        }
    }
}
