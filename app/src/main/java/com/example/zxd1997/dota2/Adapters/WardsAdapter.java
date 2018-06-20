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
import com.example.zxd1997.dota2.Utils.Tools;
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
        viewHolder.place.setText(Tools.getTime(ward.getWard().getTime()));
        if (ward.getWard_left() == null)
            viewHolder.left.setText("--:--");
        else {
            viewHolder.left.setText(Tools.getTime(ward.getWard_left().getTime()));
        }
        viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + ward.getWard().getPlayer_slot(), R.color.class)));
        int OBSERVER = 0;
        if (ward.getType() == OBSERVER)
            viewHolder.type.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.item_42)).build());
        else
            viewHolder.type.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.item_43)).build());
        viewHolder.name.setText(ward.getPlayerName() == null ? context.getString(R.string.anonymous) : ward.getPlayerName());
        viewHolder.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + ward.getPlayerHero(), R.drawable.class))).build());
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
