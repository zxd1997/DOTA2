package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Wards;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class WardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Wards> wards;

    public WardsAdapter(Context context, List<Wards> wards) {
        this.context = context;
        this.wards = wards;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ward, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Wards ward = wards.get(position);
        int time = Math.abs(ward.getWard().getTime());
        int h = time / 3600;
        int m = time % 3600 / 60;
        int s = time % 3600 % 60;
        StringBuilder t = new StringBuilder();
        if (ward.getWard().getTime() < 0) t.append("-");
        if (h > 0) {
            t.append((h < 10) ? "0" + h + ":" : h + ":");
        }
        t.append((m < 10) ? "0" + m + ":" : m + ":");
        t.append((s < 10) ? "0" + s : s);
        viewHolder.place.setText(t);
        if (ward.getWard_left() == null)
            viewHolder.left.setText("--:--");
        else {
            time = Math.abs(ward.getWard_left().getTime());
            h = time / 3600;
            m = time % 3600 / 60;
            s = time % 3600 % 60;
            t = new StringBuilder();
            if (ward.getWard_left().getTime() < 0) t.append("-");
            if (h > 0) {
                t.append((h < 10) ? "0" + h + ":" : h + ":");
            }
            t.append((m < 10) ? "0" + m + ":" : m + ":");
            t.append((s < 10) ? "0" + s : s);
            viewHolder.left.setText(t);
        }
        viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + ward.getWard().getPlayer_slot(), "color", context.getPackageName())));
        int OBSERVER = 0;
        if (ward.getType() == OBSERVER)
            viewHolder.type.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                    context.getResources().getIdentifier("item_42", "drawable", context.getPackageName())))
                    .build());
        else viewHolder.type.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                context.getResources().getIdentifier("item_43", "drawable", context.getPackageName())))
                .build());
        viewHolder.name.setText(ward.getPlayerName() == null ? "Anonymous" : ward.getPlayerName());
        viewHolder.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                context.getResources().getIdentifier("hero_" + ward.getPlayerHero(), "drawable", context.getPackageName())))
                .build());
    }

    @Override
    public int getItemCount() {
        return wards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView header;
        final View color;
        final TextView name;
        final TextView place;
        final TextView left;
        final SimpleDraweeView type;

        ViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.wheader);
            color = itemView.findViewById(R.id.color_ward);
            name = itemView.findViewById(R.id.ward_name);
            place = itemView.findViewById(R.id.ward_place);
            left = itemView.findViewById(R.id.ward_left);
            type = itemView.findViewById(R.id.ward);
        }
    }
}
