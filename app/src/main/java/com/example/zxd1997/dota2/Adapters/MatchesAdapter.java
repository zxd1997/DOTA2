package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Activities.MyHeroActivity;
import com.example.zxd1997.dota2.Activities.PeerActivity;
import com.example.zxd1997.dota2.Beans.MatchHero;
import com.example.zxd1997.dota2.Beans.Peer;
import com.example.zxd1997.dota2.Beans.PlayedHero;
import com.example.zxd1997.dota2.Beans.Ranking;
import com.example.zxd1997.dota2.Beans.RecentMatch;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<MatchHero> contents;
    private final Context context;
    private boolean hasfoot = true;
    private final int MATCH = 1;
    private final int HERO = 2;
    private final int FOOT = 0;
    private final int RECORD = 3;
    private final int RANKING = 4;
    private final int HEADER = 5;
    private final int PEERS = 6;
    private final int HERO_CARD = 7;

    public boolean isHasfoot() {
        return hasfoot;
    }

    public void setHasfoot(boolean hasfoot) {
        this.hasfoot = hasfoot;
    }

    public MatchesAdapter(Context context, List<MatchHero> contents) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasfoot)
            return position == getItemCount() - 1 ? 0 : contents.get(position).getType();
        else return contents.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MATCH: {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.match_list, parent, false));
            }
            case FOOT: {
                return new Footer(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false));
            }
            case HERO: {
                return new HeroHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_played, parent, false));
            }
            case RECORD:
                return new RecordHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.record, parent, false));
            case RANKING:
                return new RankHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list, parent, false));
            case HEADER:
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
            case PEERS:
                return new PeerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.peer, parent, false));
            default:
                return new HeroCard(LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_card, parent, false));
        }
    }

    @SuppressLint("Recycle")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HERO_CARD) {
            HeroCard heroCard = (HeroCard) holder;
            TypedArray typedArray = context.getResources().obtainTypedArray(R.array.heroes);
            heroCard.name.setText(typedArray.getText(contents.get(position).getHero_id()));
            heroCard.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + contents.get(position).getHero_id(), R.drawable.class))).build());
        } else if (getItemViewType(position) == HEADER) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.text.setText(contents.get(position).getTitle());
            headerHolder.text.setTextSize(8 * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
        } else if (getItemViewType(position) == FOOT) {
            Footer footer = (Footer) holder;
            AnimationDrawable animationDrawable = (AnimationDrawable) footer.logo.getDrawable();
            animationDrawable.start();
        } else {
            if (getItemViewType(position) == MATCH) {
                final ViewHolder viewHolder = (ViewHolder) holder;
                final RecentMatch recentMatch = (RecentMatch) contents.get(position);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MatchActivity.class);
                        intent.putExtra("id", recentMatch.getMatch_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    }
                });
                final SpannableStringBuilder k = new SpannableStringBuilder();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpannableString t1 = new SpannableString(recentMatch.getKills() + "");
                        t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.win)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        k.append(t1).append("/");
                        t1 = new SpannableString(recentMatch.getDeaths() + "");
                        t1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lose)), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        k.append(t1).append("/");
                        t1 = new SpannableString(recentMatch.getAssists() + "");
                        t1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        k.append(t1);
                        final boolean win = recentMatch.getPlayer_slot() < 128 && recentMatch.isRadiant_win() || recentMatch.getPlayer_slot() > 127 && !recentMatch.isRadiant_win();
                        viewHolder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                if (win) {
                                    viewHolder.win_or_not.setTextColor(context.getResources().getColor(R.color.win));
                                    viewHolder.win_or_not.setText(context.getString(R.string.win));
                                } else {
                                    viewHolder.win_or_not.setTextColor(context.getResources().getColor(R.color.lose));
                                    viewHolder.win_or_not.setText(context.getString(R.string.lose));
                                }
                                viewHolder.kda.setText(k);
                                TypedArray typedArray = context.getResources().obtainTypedArray(R.array.heroes);
                                viewHolder.hero_name.setText(typedArray.getText(recentMatch.getHero_id()));
                                viewHolder.hero_header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + recentMatch.getHero_id(), R.drawable.class))).build());
                                typedArray = context.getResources().obtainTypedArray(R.array.skills);
                                viewHolder.skills.setText(typedArray.getText(recentMatch.getSkill()));
                                typedArray = context.getResources().obtainTypedArray(R.array.lobby_type);
                                if (recentMatch.getLobby_type() == 2 || recentMatch.getLobby_type() == 5 || recentMatch.getLobby_type() == 6 || recentMatch.getLobby_type() == 7 || recentMatch.getLobby_type() == 9)
                                    viewHolder.lobby_type.setTextColor(context.getResources().getColor(R.color.very_high));
                                else
                                    viewHolder.lobby_type.setTextColor(context.getResources().getColor(R.color.normal));
                                viewHolder.lobby_type.setText(typedArray.getText(recentMatch.getLobby_type()));
                                typedArray = context.getResources().obtainTypedArray(R.array.skills_color);
                                viewHolder.skills.setTextColor(context.getResources().getColor(typedArray.getResourceId(recentMatch.getSkill(), 0)));
                                typedArray = context.getResources().obtainTypedArray(R.array.game_mode);
                                viewHolder.game_mode.setText(typedArray.getText(recentMatch.getGame_mode()));
                                typedArray.recycle();
                                viewHolder.time.setText(Tools.getBefore(recentMatch.getStart_time()));
                            }
                        });
                    }
                }).start();
            } else if (getItemViewType(position) == HERO) {
                final PlayedHero hero = (PlayedHero) contents.get(position);
                final HeroHolder heroHolder = (HeroHolder) holder;
                heroHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MyHeroActivity.class);
                        intent.putExtra("id", hero.getHero_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final float winrate = (float) hero.getWin() / hero.getGames();
                        heroHolder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                TypedArray typedArray = context.getResources().obtainTypedArray(R.array.heroes);
                                heroHolder.hero_name.setText(typedArray.getText(hero.getHero_id()));
                                heroHolder.hero_header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + hero.getHero_id(), R.drawable.class))).build());
                                heroHolder.winrate.setText(new SpannableStringBuilder(context.getResources().getString(R.string.winrate_)).append(Tools.getS(winrate > 0 ? winrate : 0)));
                                heroHolder.matches.setText(context.getResources().getString(R.string.matches_, hero.getGames()));
                                heroHolder.last_played.setText(Tools.getBefore(hero.getLast_played()));
                            }
                        });
                    }
                }).start();
            } else if (getItemViewType(position) == RECORD) {
                final RecordHolder viewHolder = (RecordHolder) holder;
                final RecentMatch recentMatch = (RecentMatch) contents.get(position);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MatchActivity.class);
                        intent.putExtra("id", recentMatch.getMatch_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    }
                });
                final boolean win = recentMatch.getPlayer_slot() < 128 && recentMatch.isRadiant_win() || recentMatch.getPlayer_slot() > 127 && !recentMatch.isRadiant_win();
                viewHolder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (win) {
                            viewHolder.win_or_not.setTextColor(context.getResources().getColor(R.color.win));
                            viewHolder.win_or_not.setText(context.getString(R.string.win));
                        } else {
                            viewHolder.win_or_not.setTextColor(context.getResources().getColor(R.color.lose));
                            viewHolder.win_or_not.setText(context.getString(R.string.lose));
                        }
                        viewHolder.record_title.setText(recentMatch.getTitle());
                        viewHolder.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + recentMatch.getHero_id(), R.drawable.class))).build());
                        viewHolder.time.setText(Tools.getBefore(recentMatch.getStart_time()));
                    }
                });
            } else if (getItemViewType(position) == RANKING) {
                final Ranking ranking = (Ranking) contents.get(position);
                final RankHolder rankHolder = (RankHolder) holder;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rankHolder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                TypedArray typedArray = context.getResources().obtainTypedArray(R.array.heroes);
                                rankHolder.hero_name.setText(typedArray.getText(ranking.getHero_id()));
                                rankHolder.header.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("hero_" + ranking.getHero_id(), R.drawable.class))).build());
                                final DecimalFormat df = new DecimalFormat("0.00");
                                rankHolder.score.setText(df.format(ranking.getScore()));
                                rankHolder.percentage.setText(Tools.getS(ranking.getPercent_rank()));
                                rankHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, MyHeroActivity.class);
                                        intent.putExtra("id", ranking.getHero_id());
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        context.startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                }).start();
            } else if (getItemViewType(position) == PEERS) {
                final Peer peer = (Peer) contents.get(position);
                PeerHolder peerHolder = (PeerHolder) holder;
                peerHolder.header.setImageURI(peer.getAvatar_full());
                peerHolder.name.setText(peer.getPersonaname());
                peerHolder.last.setText(Tools.getBefore(peer.getLast_played()));
                peerHolder.matches.setText(String.valueOf(peer.getWith_games()));
                peerHolder.winrate.setText(Tools.getS((double) peer.getWith_win() / peer.getWith_games()));
                peerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PeerActivity.class);
                        intent.putExtra("id", peer.getAccount_id());
                        intent.putExtra("name", peer.getPersonaname());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        if (hasfoot)
            return contents.size() + 1;
        return contents.size();
    }

    class RankHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView hero_name;
        TextView score;
        TextView percentage;

        RankHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.ranking_header);
            hero_name = itemView.findViewById(R.id.ranking_name);
            score = itemView.findViewById(R.id.score);
            percentage = itemView.findViewById(R.id.percentage);
        }
    }

    class PeerHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;
        TextView last;
        TextView matches;
        TextView winrate;

        PeerHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.peer_header);
            name = itemView.findViewById(R.id.peer_name);
            last = itemView.findViewById(R.id.last_together);
            matches = itemView.findViewById(R.id.peer_matches);
            winrate = itemView.findViewById(R.id.together_winrate);
        }
    }

    class RecordHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView record_title;
        TextView time;
        TextView win_or_not;

        RecordHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.record_hero);
            record_title = itemView.findViewById(R.id.record_title);
            time = itemView.findViewById(R.id.record_time);
            win_or_not = itemView.findViewById(R.id.record_win);
        }
    }

    class Footer extends RecyclerView.ViewHolder {
        ImageView logo;

        Footer(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.loading_logo);
        }
    }

    class HeroHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView hero_header;
        TextView hero_name;
        TextView matches;
        TextView winrate;
        TextView last_played;

        HeroHolder(View itemView) {
            super(itemView);
            hero_header = itemView.findViewById(R.id.played_header);
            hero_name = itemView.findViewById(R.id.played_name);
            matches = itemView.findViewById(R.id.matches_played);
            winrate = itemView.findViewById(R.id.hero_win_rate);
            last_played = itemView.findViewById(R.id.last_played);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView text;

        HeaderHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.header_text);
        }
    }

    public class HeroCard extends RecyclerView.ViewHolder {
        public TextView winrate;
        public TextView total;
        public TextView kill;
        public TextView death;
        public TextView assists;
        public TextView kda;
        public TextView gpm;
        public TextView xpm;
        public TextView lh;
        public TextView dn;
        public TextView damage;
        public TextView td;
        public TextView hh;
        public TextView name;
        public SimpleDraweeView header;

        HeroCard(View itemView) {
            super(itemView);
            total = itemView.findViewById(R.id.hero_total);
            winrate = itemView.findViewById(R.id.hero_win_rate);
            kill = itemView.findViewById(R.id.hero_kill);
            death = itemView.findViewById(R.id.hero_death);
            assists = itemView.findViewById(R.id.hero_assists);
            kda = itemView.findViewById(R.id.hero_kda);
            gpm = itemView.findViewById(R.id.hero_gpm);
            xpm = itemView.findViewById(R.id.hero_xpm);
            lh = itemView.findViewById(R.id.hero_lh);
            dn = itemView.findViewById(R.id.hero_dn);
            damage = itemView.findViewById(R.id.hero_damage);
            hh = itemView.findViewById(R.id.hero_hh);
            td = itemView.findViewById(R.id.hero_td);
            name = itemView.findViewById(R.id.hero_name_header);
            header = itemView.findViewById(R.id.hero_big_header);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView hero_header;
        final TextView hero_name;
        final TextView win_or_not;
        final TextView time;
        final TextView game_mode;
        final TextView lobby_type;
        final TextView skills;
        final TextView kda;

        ViewHolder(View itemView) {
            super(itemView);
            hero_header = itemView.findViewById(R.id.hero_header);
            hero_name = itemView.findViewById(R.id.hero_name);
            win_or_not = itemView.findViewById(R.id.winornot);
            time = itemView.findViewById(R.id.time);
            game_mode = itemView.findViewById(R.id.game_mode);
            lobby_type = itemView.findViewById(R.id.lobby_type);
            skills = itemView.findViewById(R.id.skills);
            kda = itemView.findViewById(R.id.kda);
        }
    }
}
