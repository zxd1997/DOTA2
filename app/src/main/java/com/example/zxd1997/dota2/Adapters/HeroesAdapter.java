package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.HeroActivity;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;

public class HeroesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SparseArray<String> heroes;
    private Context context;

    public HeroesAdapter(SparseArray<String> heroes, Context context) {
        this.heroes = heroes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.head.setImageResource(Tools.getResId("hero_" + heroes.keyAt(position), R.drawable.class));
        viewHolder.name.setText(heroes.valueAt(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setTransitionName(viewHolder.head, "hero_" + heroes.keyAt(position));
                Intent intent = new Intent(context, HeroActivity.class);
                intent.putExtra("id", heroes.keyAt(position));
                intent.putExtra("name", heroes.valueAt(position));
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, viewHolder.head, ViewCompat.getTransitionName(viewHolder.head)).toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView head;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.hero);
            name = itemView.findViewById(R.id.hero_n);
        }
    }
}
