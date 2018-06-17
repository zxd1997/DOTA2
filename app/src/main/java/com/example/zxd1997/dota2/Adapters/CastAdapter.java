package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ABILITY = 0;
    private final int ITEM = 1;
    private final int PURCHASE = 2;
    private final int HEADER = -1;
    private final int HERO = 3;
    private final int HERO1 = 7;
    private final int ARROW = 4;
    private final int ENTER = 6;
    private final int BUFF = 8;
    private final int PLAYER_HEADER = 9;
    private final int PLAYER_HEADER_DAMAGE = 10;
    private final int RADIANT_HEADER = 11;
    private final int TEAMFIGHT_HEADER = 12;
    private final List<Cast> contents = new ArrayList<>();
    private final Context context;

    public CastAdapter(Context context, List<Match.PPlayer> players, String s) {
        this.context = context;
        for (Match.PPlayer p : players) {
            contents.add(new CastHeader(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color",
                    context.getPackageName())), p.getPersonaname(), PLAYER_HEADER, p.getHero_id(), p.getTotal_gold(), p.getGold_spent()));
            contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.item_purchase), HEADER));
            for (Match.Objective purchase : p.getPurchase_log()) {
                if (purchase.getKey().equals("tpscroll") || purchase.getKey().equals("ward_observer") || purchase.getKey().equals("ward_sentry"))
                    continue;
                Item item = MainActivity.items.get(purchase.getKey());
                contents.add(new Cast(purchase.getTime(), item.getId() + "", PURCHASE));
            }
            contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
            for (Map.Entry<String, Integer> entry : p.getAbility_uses().entrySet()) {
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        break;
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : p.getItem_uses().entrySet()) {
                Item item = MainActivity.items.get(entry.getKey());
                contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
            }
        }
    }

    CastAdapter(Context context, List<Match.TeamFight.TeamFightPlayer> teamFightPlayers, char c) {
        this.context = context;
        int j = 0;
        contents.add(new Cast(R.drawable.radiant_header, context.getResources().getString(R.string.radiant), RADIANT_HEADER));
        for (Match.TeamFight.TeamFightPlayer p : teamFightPlayers) {
            if (j == 5) {
                contents.add(new Cast(R.drawable.dire_header, context.getResources().getString(R.string.dire), RADIANT_HEADER));
            }
            j++;
            if (p.getDeaths() > 0 || p.getBuybacks() > 0 || p.getDamage() > 0 || p.getHealing() > 0) {
                contents.add(new TeamFightCast(0, "", TEAMFIGHT_HEADER, p));
                if (p.getKilled().size() > 0) {
                    contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.kills), HEADER));
                    for (Map.Entry<String, Integer> entry : p.getKilled().entrySet()) {
                        Hero hero = MainActivity.heroes.get(entry.getKey());
                        for (int i = 0; i < entry.getValue(); i++)
                            contents.add(new Cast(entry.getValue(), "hero_" + hero.getId() + "_icon", HERO));
                    }
                }
                if (p.getAbility_uses().size() + p.getItem_uses().size() > 0) {
                    contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
                    for (Map.Entry<String, Integer> entry : p.getAbility_uses().entrySet()) {
                        for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                            if (entry1.getValue().equals(entry.getKey())) {
                                contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                                break;
                            }
                        }
                    }
                    for (Map.Entry<String, Integer> entry : p.getItem_uses().entrySet()) {
                        Item item = MainActivity.items.get(entry.getKey());
                        contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                    }
                }
            }
        }
    }

    public CastAdapter(Context context, List<Match.PPlayer> players, int t) {
        this.context = context;
        for (Match.PPlayer p : players) {
            CastHeader castHeader = new CastHeader(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color",
                    context.getPackageName())), p.getPersonaname(), PLAYER_HEADER_DAMAGE, p.getHero_id(), p.getHero_damage(), 0);
            contents.add(castHeader);
            contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.damage_dealt), HEADER));
            for (Map.Entry<String, Integer> entry : p.getDamage_inflictor().entrySet()) {
                if (entry.getKey().equals("null")) {
                    contents.add(new Cast(entry.getValue(), "default_attack", ABILITY));
                    contents.add(new Cast(0, "", ARROW));
                    int i = 0;
                    for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                        if (i == 8) contents.add(new Cast(0, "", ENTER));
                        contents.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                        i++;
                    }
                    contents.add(new Cast(0, "", ENTER));
                } else {
                    boolean f = false;
                    for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                        if (entry1.getValue().equals(entry.getKey())) {
                            contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                            contents.add(new Cast(0, "", ARROW));
                            int i = 0;
                            for (Map.Entry<String, Integer> entry2 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                                if (i == 8) contents.add(new Cast(0, "", ENTER));
                                contents.add(new Cast(entry2.getValue(), "hero_" + MainActivity.heroes.get(entry2.getKey()).getId() + "_icon", HERO1));
                                i++;
                            }
                            contents.add(new Cast(0, "", ENTER));
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        Item item = MainActivity.items.get(entry.getKey());
                        contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                        contents.add(new Cast(0, "", ARROW));
                        int i = 0;
                        for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                            if (i == 8) contents.add(new Cast(0, "", ENTER));
                            contents.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                            i++;
                        }
                        contents.add(new Cast(0, "", ENTER));
                    }
                }
            }
            int d_t = 0;
            contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.damage_taken), HEADER));
            for (Map.Entry<String, Integer> entry : p.getDamage_inflictor_received().entrySet()) {
                d_t += entry.getValue();
                if (entry.getKey().equals("null")) {
                    contents.add(new Cast(entry.getValue(), "default_attack", ABILITY));
                } else {
                    boolean f = false;
                    for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                        if (entry1.getValue().equals(entry.getKey())) {
                            contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        Item item = MainActivity.items.get(entry.getKey());
                        contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                    }
                }
            }
            castHeader.damage_in = d_t;
        }
    }

    CastAdapter(Context context, List<Match.PPlayer.Buff> buffs) {
        this.context = context;
        @SuppressLint("Recycle") TypedArray typedArray = Objects.requireNonNull(context).getResources().obtainTypedArray(R.array.buff);
        int i = 0;
        for (Match.PPlayer.Buff buff : buffs) {
            if (buff.getPermanent_buff() < 6) {
                if (i == 0)
                    contents.add(new Cast(Color.BLACK, context.getString(R.string.buffs), HEADER));
                contents.add(new Cast(buff.getStack_count(), String.valueOf(typedArray.getResourceId(buff.getPermanent_buff(), 0)), BUFF));
                i++;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PLAYER_HEADER_DAMAGE:
            case PLAYER_HEADER: {
                return new PHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.p_header, parent, false));
            }
            case HEADER:
            case RADIANT_HEADER: {
                return new Header(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
            }
            case TEAMFIGHT_HEADER: {
                return new TeamFight(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_fight_header, parent, false));
            }
            case ARROW: {
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.arrow, parent, false));
            }
            case ENTER: {
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.space, parent, false));
            }
            case BUFF: {
                return new Buff(LayoutInflater.from(parent.getContext()).inflate(R.layout.buff, parent, false));
            }
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_taken_list, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PLAYER_HEADER_DAMAGE: {
                PHeaderHolder castHeader = (PHeaderHolder) holder;
                CastHeader p = (CastHeader) contents.get(position);
                castHeader.color.setBackgroundColor(p.time);
                castHeader.pheader.setImageURI(new Uri.Builder().scheme("res").path(
                        String.valueOf(context.getResources().getIdentifier("hero_" + p.hero_id, "drawable", context.getPackageName()))).build());
                castHeader.out.setText(context.getResources().getString(R.string.d_out, p.damage_out));
                castHeader.taken.setText(context.getResources().getString(R.string.d_taken, p.damage_in));
                castHeader.name.setText(p.id);
                break;
            }
            case PLAYER_HEADER: {
                PHeaderHolder castHeader = (PHeaderHolder) holder;
                CastHeader p = (CastHeader) contents.get(position);
                castHeader.color.setBackgroundColor(p.time);
                castHeader.pheader.setImageURI(new Uri.Builder().scheme("res").path(
                        String.valueOf(context.getResources().getIdentifier("hero_" + p.hero_id, "drawable", context.getPackageName()))).build());
                castHeader.out.setText(context.getResources().getString(R.string.total_gold, p.damage_out));
                castHeader.taken.setText(context.getResources().getString(R.string.gold_spent, p.damage_in));
                castHeader.name.setText(p.id);
                break;
            }
            case HEADER: {
                Header header = (Header) holder;
                header.header_text.setTextColor(contents.get(position).time);
                header.header_text.setText(contents.get(position).id);
                break;
            }
            case RADIANT_HEADER: {
                Header header = (Header) holder;
                header.header_text.setTextColor(Color.WHITE);
                header.header_text.setText(contents.get(position).id);
                header.itemView.setBackground(context.getDrawable(contents.get(position).time));
                break;
            }
            case TEAMFIGHT_HEADER: {
                TeamFight viewHolder = (TeamFight) holder;
                Match.TeamFight.TeamFightPlayer p = ((TeamFightCast) contents.get(position)).p;
                viewHolder.name.setText(p.getPersonaname() == null ? context.getString(R.string.anonymous) : p.getPersonaname());
                viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color", context.getPackageName())));
                if (p.getDeaths() > 0) viewHolder.death.setVisibility(View.VISIBLE);
                viewHolder.header.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(
                        context.getResources().getIdentifier(p.getHero_id(), "drawable", context.getPackageName()))).build());
                SpannableString gold = new SpannableString(" ");
                Drawable drawable = context.getDrawable(R.drawable.gold);
                Objects.requireNonNull(drawable).setBounds(0, 0, 50, 34);
                gold.setSpan(new ImageSpan(drawable), 0, gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString up = new SpannableString(" ");
                drawable = context.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
                up.setSpan(new ImageSpan(drawable), 0, up.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString down = new SpannableString(" ");
                drawable = context.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                Objects.requireNonNull(drawable).setBounds(0, 0, 48, 48);
                down.setSpan(new ImageSpan(drawable), 0, down.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString gold_delta = new SpannableString(p.getGold_delta() + "");
                if (p.getGold_delta() > 0) {
                    gold_delta.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, gold_delta.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.gold_delta.setText(new SpannableStringBuilder().append(up).append(gold_delta).append(" ").append(gold));
                } else {
                    gold_delta.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, gold_delta.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.gold_delta.setText(new SpannableStringBuilder().append(down).append(gold_delta).append(" ").append(gold));
                }
                viewHolder.damage.setText(context.getString(R.string.damage_, p.getDamage()));
                viewHolder.healing.setText(context.getString(R.string.healing, p.getHealing()));
                viewHolder.xp_delta.setText(context.getString(R.string.xp_earn, p.getXp_delta()));
                break;
            }
            case ARROW:
            case ENTER:
                break;
            case BUFF: {
                Buff buff = (Buff) holder;
                buff.buff.setImageURI(new Uri.Builder().scheme("res").path(contents.get(position).id).build());
                buff.count.setText(String.valueOf(contents.get(position).time));
                break;
            }
            default: {
                ViewHolder viewHolder = (ViewHolder) holder;
                if (getItemViewType(position) == PURCHASE) {
                    viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                            context.getResources().getIdentifier("item_" + contents.get(position).id, "drawable", context.getPackageName())))
                            .build());
                    viewHolder.damage_taken.setText(Tools.getTime(contents.get(position).time));
                } else {
                    switch (getItemViewType(position)) {
                        case ABILITY:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier("ability_" + contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            break;
                        case ITEM:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier("item_" + contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            break;
                        case HERO:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier(contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            viewHolder.damage_taken.setVisibility(View.GONE);
                            break;
                        case HERO1:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier(contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            break;
                    }
                    viewHolder.damage_taken.setText(String.valueOf(contents.get(position).time));
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class Cast {
        final int time;
        final String id;
        final int type;

        Cast(int time, String id, int type) {
            this.type = type;
            this.id = id;
            this.time = time;
        }
    }

    class TeamFightCast extends Cast {
        Match.TeamFight.TeamFightPlayer p;

        TeamFightCast(int time, String id, int type, Match.TeamFight.TeamFightPlayer p) {
            super(time, id, type);
            this.p = p;
        }
    }

    class CastHeader extends Cast {
        final int damage_out;
        final int hero_id;
        int damage_in;

        CastHeader(int time, String id, int type, int hero_id, int damage_out, int damage_in) {
            super(time, id, type);
            this.hero_id = hero_id;
            this.damage_out = damage_out;
            this.damage_in = damage_in;
        }
    }

    class PHeaderHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView pheader;
        final View color;
        final TextView name;
        final TextView out;
        final TextView taken;

        PHeaderHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color_detail);
            pheader = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.hname);
            out = itemView.findViewById(R.id.damage_out);
            taken = itemView.findViewById(R.id.damage_taken);
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        Holder(View itemView) {
            super(itemView);
        }
    }

    class Buff extends RecyclerView.ViewHolder {
        final SimpleDraweeView buff;
        final TextView count;

        Buff(View itemView) {
            super(itemView);
            buff = itemView.findViewById(R.id.buff);
            count = itemView.findViewById(R.id.buff_count);
        }
    }

    class Header extends RecyclerView.ViewHolder {
        final TextView header_text;

        Header(View itemView) {
            super(itemView);
            header_text = itemView.findViewById(R.id.header_text);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView icon;
        final TextView damage_taken;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.d_skill);
            damage_taken = itemView.findViewById(R.id.d_damage_taken);
        }
    }

    class TeamFight extends RecyclerView.ViewHolder {
        final SimpleDraweeView header;
        final View color;
        final TextView name;
        final ImageView death;
        final TextView gold_delta;
        final TextView xp_delta;
        final TextView damage;
        final TextView healing;

        TeamFight(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.team_fight_header);
            color = itemView.findViewById(R.id.team_fight_color);
            death = itemView.findViewById(R.id.death);
            name = itemView.findViewById(R.id.team_fight_player);
            gold_delta = itemView.findViewById(R.id.gold_delta);
            xp_delta = itemView.findViewById(R.id.xp_delta);
            damage = itemView.findViewById(R.id.team_fight_damage);
            healing = itemView.findViewById(R.id.team_fight_healing);
        }
    }
}
