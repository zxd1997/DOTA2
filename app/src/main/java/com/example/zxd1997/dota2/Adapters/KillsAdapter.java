package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Content;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class KillsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Content> contents;

    public KillsAdapter(Context context, List<Content> contents) {
        this.context = context;
        this.contents = contents;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dk_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dk_number, parent, false);
            return new kdHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).isHeader() ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
        } else if (getItemViewType(position) == 1) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.header.setImageResource(context.getResources().getIdentifier("hero_" + contents.get(position).getHero_id(), "drawable", context.getPackageName()));
        } else {
            kdHolder kdHolder = (kdHolder) holder;
            kdHolder.textView.setText(contents.get(position).getKd());
        }
    }

    @Override
    public int getItemCount() {
        return 36;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.kd_header);
        }
    }

    class kdHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public kdHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.kd_text);
        }
    }
}
