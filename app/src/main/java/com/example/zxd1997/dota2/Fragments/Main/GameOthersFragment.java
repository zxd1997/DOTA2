package com.example.zxd1997.dota2.Fragments.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Adapters.GameInfoAdapter;
import com.example.zxd1997.dota2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOthersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOthersFragment extends Fragment {

    public GameOthersFragment() {
        // Required empty public constructor
    }

    public static GameOthersFragment newInstance() {
        return new GameOthersFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_others, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.others);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> items = new ArrayList<>();
        items.add("单位属性");
        items.add("属性");
        items.add("生命值");
        items.add("魔法值");
        items.add("普通攻击");
        items.add("攻击动作");
        items.add("攻击距离");
        items.add("护甲");
        items.add("技能增强");
        items.add("魔法抗性");
        items.add("状态抗性");
        items.add("移动速度");
        items.add("攻击速度");
        items.add("转身速率");
        items.add("视野");
        items.add("死亡");
        items.add("反补");
        items.add("买活");
        items.add("英雄");
        items.add("幻象");
        items.add("小兵");
        items.add("中立生物");
        items.add("召唤单位");
        items.add("普通召唤单位");
        items.add("英雄级单位");
        items.add("守卫");
        items.add("远古单位");
        items.add("Roshan");
        items.add("信使");
        items.add("建筑物");
        items.add("物品");
        items.add("背包系统");
        items.add("游戏模式");

        items.add("结算判定");
        items.add("伤害");
        items.add("伤害事件");
        items.add("普通攻击");
        items.add("攻击特效");
        items.add("致命一击");
        items.add("重击");
        items.add("吸血");
        items.add("独占攻击特效");
        items.add("伤害结算流程");
        items.add("伤害调整");
        items.add("伤害格挡");
        items.add("分裂与溅射");
        items.add("闪避");
        items.add("躲避");
        items.add("技能");
        items.add("充能技能");
        items.add("施法动作");
        items.add("技能抵挡");
        items.add("技能反弹");
        items.add("持续施法");
        items.add("持续性技能");
        items.add("单位持续性伤害技能");
        items.add("范围持续性伤害技能");
        items.add("持续伤害");
        items.add("事件结算流程");

        items.add("状态效果");
        items.add("光环");
        items.add("隐身");
        items.add("无敌");
        items.add("隐藏");
        items.add("龙卷风");
        items.add("技能免疫");
        items.add("眩晕");
        items.add("缠绕");
        items.add("睡眠");
        items.add("沉默");
        items.add("破坏");
        items.add("妖术");
        items.add("嘲讽");
        items.add("恐惧");
        items.add("虚无");
        items.add("缴械");
        items.add("致盲");
        items.add("驱散");
        items.add("躲避");


        items.add("世界元素");
        items.add("地图");
        items.add("小地图");
        items.add("金钱");
        items.add("经验");
        items.add("神符");
        items.add("概率分布机制");
        items.add("真随机分布");
        items.add("伪随机分布");
        items.add("树木");
        recyclerView.setAdapter(new GameInfoAdapter(items, getContext()));
        return view;
    }

}
