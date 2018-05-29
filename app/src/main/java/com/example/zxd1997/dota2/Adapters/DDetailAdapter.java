package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class DDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Match.PPlayer> p;
    Context context;

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
        viewHolder.header.setImageResource(context.getResources().getIdentifier("hero_" + p.get(position).getHero_id(), "drawable", context.getPackageName()));
        viewHolder.name.setText(p.get(position).getPersonaname() == null ? "Anonymous" : p.get(position).getPersonaname());
        viewHolder.d_taken.setLayoutManager(new GridLayoutManager(context, 9));
        viewHolder.d_taken.setAdapter(new DTakenAdapter(context, p.get(position).getDamage_inflictor_received()));
        viewHolder.d_output.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.d_output.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        viewHolder.d_output.setAdapter(new DOutputAdapter(context, p.get(position)));
    }

    @Override
    public int getItemCount() {
        return p.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;
        RecyclerView d_output;
        RecyclerView d_taken;

        public ViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.hname);
            d_output = itemView.findViewById(R.id.d_output);
            d_output.setNestedScrollingEnabled(false);
            d_taken = itemView.findViewById(R.id.d_taken);
            d_taken.setNestedScrollingEnabled(false);
        }
    }
}
