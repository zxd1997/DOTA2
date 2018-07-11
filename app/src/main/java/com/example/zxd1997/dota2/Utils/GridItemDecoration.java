package com.example.zxd1997.dota2.Utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zxd1997.dota2.Adapters.CastAdapter;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        if (viewHolder instanceof CastAdapter.ViewHolder || viewHolder instanceof CastAdapter.BanPickHolder) {
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            int dp8 = (int) (MyApplication.getContext().getResources().getDisplayMetrics().density * 8);
            outRect.set(0, 0, 0, 0);
            outRect.left = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * dp8);
            outRect.right = (int) (((float) dp8 * (spanCount + 1) / spanCount) - outRect.left);
        }
    }
}
