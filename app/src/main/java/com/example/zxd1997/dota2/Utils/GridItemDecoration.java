package com.example.zxd1997.dota2.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zxd1997.dota2.Adapters.CastAdapter;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        if (viewHolder instanceof CastAdapter.ViewHolder) {
            CastAdapter.ViewHolder viewHolder1 = (CastAdapter.ViewHolder) parent.getChildViewHolder(view);
            int dp4 = (int) (MyApplication.getContext().getResources().getDisplayMetrics().density * 4);
            int dp8 = (int) (MyApplication.getContext().getResources().getDisplayMetrics().density * 8);
            outRect.set(0, 0, 0, 0);
            outRect.set(dp4, dp4, dp4, dp4);
            switch (viewHolder1.getFirst()) {
                case 1: {
                    outRect.left = dp8;
                    break;
                }
                case 2: {
                    outRect.right = dp8;
                    break;
                }
                default:
                    break;
            }
        }
    }
}
