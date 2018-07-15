package com.example.zxd1997.dota2.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Beans.Search;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Search> searches;

    public SearchResultAdapter(Context context, List<Search> searches) {
        this.context = context;
        this.searches = searches;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (searches.size() == 0) {
            viewHolder.name.setVisibility(View.GONE);
            viewHolder.header.setVisibility(View.GONE);
            viewHolder.id.setVisibility(View.GONE);
            viewHolder.no.setVisibility(View.VISIBLE);
        } else {
            viewHolder.name.setVisibility(View.VISIBLE);
            viewHolder.header.setVisibility(View.VISIBLE);
            viewHolder.id.setVisibility(View.VISIBLE);
            viewHolder.no.setVisibility(View.GONE);
            Search search = searches.get(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra("id", search.getAccount_id());
                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, viewHolder.header, ViewCompat.getTransitionName(viewHolder.header)).toBundle());
                }
            });
            viewHolder.name.setText(search.getPersonaname());
            viewHolder.id.setText(context.getResources().getString(R.string.id, search.getAccount_id()));
            Tools.showImage(Uri.parse(search.getAvatarfull()), viewHolder.header);
        }
    }

    @Override
    public int getItemCount() {
        return searches.size() == 0 ? 1 : searches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;
        TextView id;
        TextView no;

        public ViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.search_header);
            name = itemView.findViewById(R.id.result_name);
            id = itemView.findViewById(R.id.result_id);
            no = itemView.findViewById(R.id.noresult);
        }
    }
}
