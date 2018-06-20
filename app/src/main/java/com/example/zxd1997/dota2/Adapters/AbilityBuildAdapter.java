package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Abilities;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class AbilityBuildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ABILITY = 1;
    private final int TALENT = 2;
    private final int HEADER = -1;
    private final int NULL_ABILITY = 3;
    private final int PLAYER = -2;
    private final int LEVEL = -3;
    private final List<Abilities> abilities;
    private final Context context;

    public AbilityBuildAdapter(Context context, List<Abilities> abilities) {
        this.context = context;
        this.abilities=abilities;
    }

    @Override
    public int getItemViewType(int position) {
        return abilities.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ABILITY:
            case TALENT:
            case NULL_ABILITY: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_build, parent, false);
                return new ViewHolder(view);
            }
            case HEADER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_header, parent, false);
                return new HeaderHolder(view);
            }
            case PLAYER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_player, parent, false);
                return new PlayerHolder(view);
            }
            default: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ablity_level, parent, false);
                return new LevelHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER: {
                Abilities a = abilities.get(position);
                HeaderHolder viewHolder = (HeaderHolder) holder;
                viewHolder.header.setImageURI(new Uri.Builder().scheme("res").path(a.getId() + "").build());
                viewHolder.name.setText(a.getWhat());
                viewHolder.color.setBackgroundColor(context.getResources().getColor(a.getColor()));
                break;
            }
            case PLAYER:
                break;
            case LEVEL: {
                LevelHolder viewHolder = (LevelHolder) holder;
                viewHolder.level.setText(abilities.get(position).getWhat());
                break;
            }
            case NULL_ABILITY: {
                break;
            }
            case ABILITY: {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.talent.setText("");
                viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(abilities.get(position).getColor() + "").build());
                break;
            }
            case TALENT: {
                ViewHolder viewHolder = (ViewHolder) holder;
                Abilities a = abilities.get(position);
                viewHolder.talent.setText(a.getWhat());
                viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(abilities.get(position).getColor() + "").build());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return abilities.size();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView header;
        final TextView name;
        final View color;

        HeaderHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color_skill);
            header = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.playername);
        }
    }

    class PlayerHolder extends RecyclerView.ViewHolder {
        PlayerHolder(View itemView) {
            super(itemView);
        }
    }

    class LevelHolder extends RecyclerView.ViewHolder {
        final TextView level;

        LevelHolder(View itemView) {
            super(itemView);
            level = itemView.findViewById(R.id.level);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView icon;
        final TextView talent;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.ability);
            talent = itemView.findViewById(R.id.talent);
        }
    }
}
