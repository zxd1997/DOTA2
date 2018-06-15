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

public class PurchaseAndCastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Match.PPlayer> p;
    private final Context context;

    public PurchaseAndCastAdapter(Context context, List<Match.PPlayer> p) {
        this.p = p;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ddtail_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.get(position).getPlayer_slot(), "color", context.getPackageName())));
        viewHolder.header.setImageURI(
                new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("hero_" + p.get(position).getHero_id(), "drawable", context.getPackageName())))
                        .build());
        viewHolder.name.setText(p.get(position).getPersonaname() == null ? context.getString(R.string.anonymous) : p.get(position).getPersonaname());
        final CastAdapter castAdapter = new CastAdapter(context, p.get(position).getPurchase_log(), p.get(position).getAbility_uses(), p.get(position).getItem_uses());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 10);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return castAdapter.getItemViewType(position) == -1 ? 10 : 1;
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

        ViewHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color_detail);
            header = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.hname);
            d_output = itemView.findViewById(R.id.d_output);
            d_output.setNestedScrollingEnabled(false);
        }
    }
}
