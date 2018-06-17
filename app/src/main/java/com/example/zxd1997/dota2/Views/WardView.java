package com.example.zxd1997.dota2.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zxd1997.dota2.Beans.Wards;
import com.example.zxd1997.dota2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WardView extends View {
    Bitmap radiant_obs = BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(), R.drawable.goodguys_observer);
    float hh = radiant_obs.getHeight();
    float ww = radiant_obs.getWidth();
    Paint paint = new Paint();
    Rect rect = new Rect(0, 0, (int) ww, (int) hh);
    Rect drect = new Rect();
    Bitmap dire_obs = BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(), R.drawable.badguys_observer);
    Bitmap radiant_sen = BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(), R.drawable.goodguys_sentry);
    Bitmap dire_sen = BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(), R.drawable.badguys_sentry);
    private List<Wards> wards = new ArrayList<>();

    public WardView(Context context) {
        super(context);
    }

    public WardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<Wards> getWards() {
        return wards;
    }

    public void setWards(List<Wards> wards) {
        this.wards = wards;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        float radius;
        Bitmap tmp;
        for (Wards ward : wards) {
            int OBSERVER = 0;
            if (ward.getType() == OBSERVER) {
                if (ward.getWard().getPlayer_slot() < 5) {
                    tmp = radiant_obs;
                    paint.setColor(Color.parseColor("#00ff00"));
                    radius = (float) 1600 / 19000 * getWidth();
                } else {
                    tmp = dire_obs;
                    paint.setColor(Color.parseColor("#ff0000"));
                    radius = (float) 1600 / 19000 * getWidth();
                }
            } else {
                if (ward.getWard().getPlayer_slot() < 5) {
                    tmp = radiant_sen;
                    paint.setColor(Color.parseColor("#00ff00"));
                    radius = (float) 850 / 19000 * getWidth();
                } else {
                    tmp = dire_sen;
                    paint.setColor(Color.parseColor("#ff0000"));
                    radius = (float) 850 / 19000 * getWidth();
                }
            }
            paint.setAlpha(60);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float) (ward.getWard().getX() * 2 - 128) / 2 * (float) 4 / 502 * getWidth(), getHeight() - (float) (ward.getWard().getY() * 2 - 130) / 2 * (float) 4 / 503 * getHeight(), radius, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            paint.setAlpha(150);
            drect.set((int) ((float) (ward.getWard().getX() * 2 - 128) / 2 * (float) 4 / 502 * getWidth() - ww / 4), (int) (getHeight() - (float) (ward.getWard().getY() * 2 - 130) / 2 * (float) 4 / 503 * getHeight() - hh / 4),
                    (int) ((float) (ward.getWard().getX() * 2 - 128) / 2 * (float) 4 / 502 * getWidth() + ww / 4), (int) (getHeight() - (float) (ward.getWard().getY() * 2 - 130) / 2 * (float) 4 / 503 * getHeight() + hh / 4));
            canvas.drawCircle((float) (ward.getWard().getX() * 2 - 128) / 2 * (float) 4 / 502 * getWidth(), getHeight() - (float) (ward.getWard().getY() * 2 - 130) / 2 * (float) 4 / 503 * getHeight(), radius, paint);
            paint.setAlpha(255);
            canvas.drawBitmap(tmp, rect, drect, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
