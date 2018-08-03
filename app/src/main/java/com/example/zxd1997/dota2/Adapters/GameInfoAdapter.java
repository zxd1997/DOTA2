package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.GameInfoActivity;
import com.example.zxd1997.dota2.R;

import java.util.List;

public class GameInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> items;
    Context context;

    public GameInfoAdapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        String t = items.get(position);
        return t.equals("单位属性") || t.equals("结算判定") || t.equals("状态效果") || t.equals("物品机制")
                || t.equals("世界元素") || t.equals("玩法定位") || t.equals("游戏客户端") ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType == 0 ? new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.game_info_list, parent, false)) :
                new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).header_text.setText(items.get(position));
        if (getItemViewType(position) == 0)
            ((ViewHolder) holder).itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GameInfoActivity.class);
                intent.putExtra("name", items.get(position));
                context.startActivity(intent);
            });
        else {
            ((ViewHolder) holder).divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView header_text;
        final View divider;

        ViewHolder(View itemView) {
            super(itemView);
            header_text = itemView.findViewById(R.id.header_text);
            divider = itemView.findViewById(R.id.divider1);
        }
    }
}
