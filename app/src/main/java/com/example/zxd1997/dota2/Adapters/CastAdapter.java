package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zxd1997.dota2.Beans.Cast;
import com.example.zxd1997.dota2.Beans.CastHeader;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.Beans.TeamFightCast;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.example.zxd1997.dota2.Views.MapView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Objects;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HEADER = -1;
    private final int ARROW = 4;
    private final int ENTER = 6;
    private final int BUFF = 8;
    private final int PLAYER_HEADER = 9;
    private final int PLAYER_HEADER_DAMAGE = 10;
    private final int RADIANT_HEADER = 11;
    private final int TEAMFIGHT_HEADER = 12;
    private final int ABILITY = 0;
    private final int ITEM = 1;
    private final int HERO = 3;
    private final int SPACE = 5;
    private final int HERO1 = 7;
    private final int KDHEADER = 13;
    private final int KD = 14;
    private final int MAP = 15;
    private final int TEAMFIGHT = 16;
    private final List<Cast> contents;
    private final Context context;

    public CastAdapter(Context context, List<Cast> casts) {
        this.context = context;
        this.contents = casts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PLAYER_HEADER_DAMAGE:
            case PLAYER_HEADER:
                return new PHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.p_header, parent, false));
            case HEADER:
            case RADIANT_HEADER:
                return new Header(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
            case TEAMFIGHT_HEADER:
                return new TeamFight(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_fight_header, parent, false));
            case ARROW:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.arrow, parent, false));
            case ENTER:
            case SPACE:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.space, parent, false));
            case BUFF:
                return new Buff(LayoutInflater.from(parent.getContext()).inflate(R.layout.buff, parent, false));
            case KD:
                return new kdHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dk_number, parent, false));
            case KDHEADER:
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dk_header, parent, false));
            case TEAMFIGHT:
                return new TeamFightHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.teamfight, parent, false));
            case MAP:
                return new Map(LayoutInflater.from(parent.getContext()).inflate(R.layout.teamfight_map, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_taken_list, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case MAP: {
                break;
            }
            case TEAMFIGHT: {
                break;
            }
            case KD: {
                kdHolder kdHolder = (CastAdapter.kdHolder) holder;
                kdHolder.textView.setText(contents.get(position).getT());
                break;
            }
            case KDHEADER: {
                HeaderHolder headerHolder = (HeaderHolder) holder;
                if (contents.get(position).getTime() != 0) {
                    headerHolder.color.setBackgroundColor(contents.get(position).getTime());
                    Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + contents.get(position).getId(), R.drawable.class))).build(), headerHolder.header);
                }
                break;
            }
            case PLAYER_HEADER_DAMAGE: {
                PHeaderHolder castHeader = (PHeaderHolder) holder;
                CastHeader p = (CastHeader) contents.get(position);
                castHeader.color.setBackgroundColor(p.getTime());
//                castHeader.pheader.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + p.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + p.getHero_id(), R.drawable.class))).build(), castHeader.pheader);
                castHeader.out.setText(context.getResources().getString(R.string.d_out, p.getDamage_out()));
                castHeader.taken.setText(context.getResources().getString(R.string.d_taken, p.getDamage_in()));
                castHeader.name.setText(p.getId());
                break;
            }
            case PLAYER_HEADER: {
                PHeaderHolder castHeader = (PHeaderHolder) holder;
                CastHeader p = (CastHeader) contents.get(position);
                castHeader.color.setBackgroundColor(p.getTime());
//                castHeader.pheader.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + p.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId("hero_" + p.getHero_id(), R.drawable.class))).build(), castHeader.pheader);
                castHeader.out.setText(context.getResources().getString(R.string.total_gold, p.getDamage_out()));
                castHeader.taken.setText(context.getResources().getString(R.string.gold_spent, p.getDamage_in()));
                castHeader.name.setText(p.getId());
                break;
            }
            case HEADER: {
                Header header = (Header) holder;
                header.header_text.setTextColor(contents.get(position).getTime());
                header.header_text.setText(contents.get(position).getId());
                break;
            }
            case RADIANT_HEADER: {
                Header header = (Header) holder;
                header.header_text.setTextColor(Color.WHITE);
                header.header_text.setText(contents.get(position).getId());
                ViewGroup.LayoutParams layoutParams = header.itemView.getLayoutParams();
                layoutParams.height = (int) (18 * context.getResources().getDisplayMetrics().density);
                header.background.setBackground(context.getDrawable(contents.get(position).getTime()));
                break;
            }
            case TEAMFIGHT_HEADER: {
                TeamFight viewHolder = (TeamFight) holder;
                Match.TeamFight.TeamFightPlayer p = ((TeamFightCast) contents.get(position)).getP();
                viewHolder.name.setText(p.getPersonaname());
                viewHolder.color.setBackgroundColor(context.getResources().getColor(Tools.getResId("slot_" + p.getPlayer_slot(), R.color.class)));
                if (p.getDeaths() > 0) viewHolder.death.setVisibility(View.VISIBLE);
//                viewHolder.header.setImageURI(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(p.getHero_id(), R.drawable.class))).build());
                Tools.showImage(new Uri.Builder().scheme(context.getString(R.string.res)).path(String.valueOf(Tools.getResId(p.getHero_id(), R.drawable.class))).build(), viewHolder.header);
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
            case SPACE:
                break;
            case BUFF: {
                Buff buff = (Buff) holder;
//                buff.buff.setImageURI(new Uri.Builder().scheme("res").path(contents.get(position).getId()).build());
                Tools.showImage(new Uri.Builder().scheme("res").path(contents.get(position).getId()).build(), buff.buff);
                buff.count.setText(String.valueOf(contents.get(position).getTime()));
                break;
            }
            default: {
                ViewHolder viewHolder = (ViewHolder) holder;
                Cast c = contents.get(position);
                int PURCHASE = 2;
                if (getItemViewType(position) == PURCHASE) {
//                    viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + c.getId(), R.drawable.class))).build());
                    Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + c.getId(), R.drawable.class))).build(), viewHolder.icon);
                    viewHolder.damage_taken.setText(Tools.getTime(c.getTime()));
                } else {
                    switch (getItemViewType(position)) {
                        case ABILITY:
//                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("ability_" + c.getId(), R.drawable.class))).build());
                            Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("ability_" + c.getId(), R.drawable.class))).build(), viewHolder.icon);
                            break;
                        case ITEM:
//                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + c.getId(), R.drawable.class))).build());
                            Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + c.getId(), R.drawable.class))).build(), viewHolder.icon);
                            break;
                        case HERO:
//                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId(c.getId(), R.drawable.class))).build());
                            Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId(c.getId(), R.drawable.class))).build(), viewHolder.icon);
                            viewHolder.damage_taken.setVisibility(View.GONE);
                            break;
                        case HERO1:
//                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId(c.getId(), R.drawable.class))).build());
                            Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId(c.getId(), R.drawable.class))).build(), viewHolder.icon);
                            break;
                    }
                    viewHolder.damage_taken.setText(c.getTime() / 1000 >= 1 ? String.format(context.getString(R.string.k), (float) c.getTime() / 1000) : String.valueOf(c.getTime()));
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
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
        final FrameLayout background;

        Header(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
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

    class TeamFightHeader extends RecyclerView.ViewHolder {
        final CardView tf;
        final TextView radiant_gold_delta;
        final TextView radiant_death;
        final TextView start_end;
        final TextView dire_death;
        final TextView dire_gold_delta;
        final View itemView;
        final TextView teamfight_win;

        TeamFightHeader(final View itemView) {
            super(itemView);
            tf = itemView.findViewById(R.id.tf);
            this.itemView = itemView;
            teamfight_win = itemView.findViewById(R.id.teamfight_win);
            radiant_gold_delta = itemView.findViewById(R.id.radiant_gold_delta);
            radiant_death = itemView.findViewById(R.id.radiant_death);
            start_end = itemView.findViewById(R.id.start_end);
            dire_death = itemView.findViewById(R.id.dire_death);
            dire_gold_delta = itemView.findViewById(R.id.dire_gold_delta);
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

    class Map extends RecyclerView.ViewHolder {
        MapView mapView;

        public Map(View itemView) {
            super(itemView);
            mapView = itemView.findViewById(R.id.imageView6);
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
