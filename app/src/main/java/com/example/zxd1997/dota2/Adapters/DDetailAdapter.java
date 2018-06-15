package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

public class DDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Match.PPlayer> p;
    private final Context context;

    public DDetailAdapter(Context context, List<Match.PPlayer> p) {
        this.p = p;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ddtail_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.get(position).getPlayer_slot(), "color", context.getPackageName())));
        viewHolder.header.setImageURI(new Uri.Builder().scheme("res").path(
                String.valueOf(context.getResources().getIdentifier("hero_" + p.get(position).getHero_id(), "drawable", context.getPackageName()))).build());
        viewHolder.name.setText(p.get(position).getPersonaname() == null ? context.getString(R.string.anonymous) : p.get(position).getPersonaname());
        viewHolder.d_out.setText(context.getResources().getString(R.string.d_out, p.get(position).getHero_damage()));
        int d_t = 0;
        for (Map.Entry<String, Integer> entry : p.get(position).getDamage_inflictor_received().entrySet()) {
            d_t += entry.getValue();
        }
        viewHolder.d_taken.setText(context.getResources().getString(R.string.d_taken, p.get(position).getHero_damage()));
        final CastAdapter castAdapter = new CastAdapter(context, p.get(position));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 20);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (castAdapter.getItemViewType(position)) {
                    case -1:
                        return 20;
                    case 4:
                        return 1;
                    case 5:
                        return 3;
                    case 6:
                        return 20;
                    default:
                        return 2;
                }
            }
        });
        viewHolder.d_output.setLayoutManager(gridLayoutManager);
        viewHolder.d_output.setAdapter(castAdapter);
    }

    @Override
    public int getItemCount() {
        return p.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView header;
        final TextView name;
        final RecyclerView d_output;
        final View color;
        final TextView d_out;
        final TextView d_taken;
        ViewHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color_detail);
            header = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.hname);
            d_out = itemView.findViewById(R.id.damage_out);
            d_taken = itemView.findViewById(R.id.damage_taken);
            d_output = itemView.findViewById(R.id.d_output);
            d_output.setNestedScrollingEnabled(false);
        }
    }
}
