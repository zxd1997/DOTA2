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
    private List<Object> parts;

    public HeroInfoAdapter(List<Object> parts) {
        this.parts = parts;
    }

    @Override
    public int getItemViewType(int position) {
        return parts.get(position) instanceof String ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_info_list, parent, false));
        else
            return new TalentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.talent, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                viewHolder.text.setText(Html.fromHtml((String) parts.get(position), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(viewHolder.text), null));
            else
                viewHolder.text.setText(Html.fromHtml((String) parts.get(position), new ImageGetter(viewHolder.text), null));
//        Log.d("text", "onBindViewHolder: "+(int)viewHolder.text.getText().charAt(0));
        } else {
            List<String> talent = (List<String>) parts.get(position);
            TalentHolder talentHolder = (TalentHolder) holder;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                talentHolder.talent_10_1.setText((Html.fromHtml(talent.get(1), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_10_1), null)));
                talentHolder.talent_10_2.setText((Html.fromHtml(talent.get(2), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_10_2), null)));
                talentHolder.talent_15_1.setText((Html.fromHtml(talent.get(3), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_15_1), null)));
                talentHolder.talent_15_2.setText((Html.fromHtml(talent.get(4), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_15_2), null)));
                talentHolder.talent_20_1.setText((Html.fromHtml(talent.get(5), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_20_1), null)));
                talentHolder.talent_20_2.setText((Html.fromHtml(talent.get(6), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_20_2), null)));
                talentHolder.talent_25_1.setText((Html.fromHtml(talent.get(7), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_25_1), null)));
                talentHolder.talent_25_2.setText((Html.fromHtml(talent.get(8), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(talentHolder.talent_25_2), null)));
            } else {
                talentHolder.talent_10_1.setText((Html.fromHtml(talent.get(1), new ImageGetter(talentHolder.talent_10_1), null)));
                talentHolder.talent_10_2.setText((Html.fromHtml(talent.get(2), new ImageGetter(talentHolder.talent_10_2), null)));
                talentHolder.talent_15_1.setText((Html.fromHtml(talent.get(3), new ImageGetter(talentHolder.talent_15_1), null)));
                talentHolder.talent_15_2.setText((Html.fromHtml(talent.get(4), new ImageGetter(talentHolder.talent_15_2), null)));
                talentHolder.talent_20_1.setText((Html.fromHtml(talent.get(5), new ImageGetter(talentHolder.talent_20_1), null)));
                talentHolder.talent_20_2.setText((Html.fromHtml(talent.get(6), new ImageGetter(talentHolder.talent_20_2), null)));
                talentHolder.talent_25_1.setText((Html.fromHtml(talent.get(7), new ImageGetter(talentHolder.talent_25_1), null)));
                talentHolder.talent_25_2.setText((Html.fromHtml(talent.get(8), new ImageGetter(talentHolder.talent_25_2), null)));
            }
        }
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

    class TalentHolder extends RecyclerView.ViewHolder {
        TextView talent_10_1;
        TextView talent_10_2;
        TextView talent_15_1;
        TextView talent_15_2;
        TextView talent_20_1;
        TextView talent_20_2;
        TextView talent_25_1;
        TextView talent_25_2;

        TalentHolder(View itemView) {
            super(itemView);
            talent_10_1 = itemView.findViewById(R.id.talent_10_1);
            talent_10_2 = itemView.findViewById(R.id.talent_10_2);
            talent_15_1 = itemView.findViewById(R.id.talent_15_1);
            talent_15_2 = itemView.findViewById(R.id.talent_15_2);
            talent_20_1 = itemView.findViewById(R.id.talent_20_1);
            talent_20_2 = itemView.findViewById(R.id.talent_20_2);
            talent_25_1 = itemView.findViewById(R.id.talent_25_1);
            talent_25_2 = itemView.findViewById(R.id.talent_25_2);
        }
    }
}
