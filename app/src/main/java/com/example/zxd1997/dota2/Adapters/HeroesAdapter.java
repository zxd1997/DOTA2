package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.HeroActivity;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

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
        ViewHolder viewHolder = (ViewHolder) holder;
//        Log.d("hero", "onBindViewHolder: "+"hero_"+heroes.keyAt(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeroActivity.class);
                intent.putExtra("id", heroes.keyAt(position));
                intent.putExtra("name", heroes.valueAt(position));
                context.startActivity(intent);
            }
        });
        viewHolder.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + heroes.keyAt(position), R.drawable.class))).build());
        viewHolder.name.setText(heroes.valueAt(position));
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.hero);
            name = itemView.findViewById(R.id.hero_n);
        }
    }
}
