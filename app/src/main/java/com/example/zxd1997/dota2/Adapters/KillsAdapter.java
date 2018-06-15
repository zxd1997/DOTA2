package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Content;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class KillsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Content> contents;

    public KillsAdapter(Context context, List<Content> contents) {
        this.context = context;
        this.contents = contents;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dk_header, parent, false));
            case 0:
                return new kdHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dk_number, parent, false));
            default:
                return new Empty(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int t = contents.get(position).isHeader() ? 1 : 0;
        if (contents.get(position).getHero_id() == -1)
            t = 2;
        return t;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                if (contents.get(position).getHero_id() != -2) {
                    HeaderHolder headerHolder = (HeaderHolder) holder;
                    Log.d("color", "onBindViewHolder: " + contents.get(position).getColor());
                    headerHolder.color.setBackgroundColor(contents.get(position).getColor());
                    headerHolder.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                            context.getResources().getIdentifier("hero_" + contents.get(position).getHero_id(), "drawable", context.getPackageName())
                    )).build());
                }
                break;
            case 2:
                Empty empty = (Empty) holder;
                empty.textView.setText(context.getResources().getString(contents.get(position).getColor()));
                empty.textView.setTextSize(8 * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
                break;
            default:
                kdHolder kdHolder = (kdHolder) holder;
                kdHolder.textView.setText(contents.get(position).getKd());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class Empty extends RecyclerView.ViewHolder {
        final TextView textView;

        Empty(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.header_text);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView header;
        final View color;

        HeaderHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color_kd);
            header = itemView.findViewById(R.id.kd_header);
        }
    }

    class kdHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        kdHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.kd_text);
        }
    }
}
