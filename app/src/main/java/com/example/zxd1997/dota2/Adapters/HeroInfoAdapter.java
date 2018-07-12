package com.example.zxd1997.dota2.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.ImageGetter;

import java.util.List;

public class HeroInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> parts;

    public HeroInfoAdapter(List<String> parts) {
        this.parts = parts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_info_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            viewHolder.text.setText(Html.fromHtml(parts.get(position), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(viewHolder.text), null));
        else
            viewHolder.text.setText(Html.fromHtml(parts.get(position), new ImageGetter(viewHolder.text), null));
//        Log.d("text", "onBindViewHolder: "+(int)viewHolder.text.getText().charAt(0));
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.hero_text);
        }
    }
}
