package com.example.zxd1997.dota2.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.zxd1997.dota2.Adapters.LogAdapter;

import java.util.ArrayList;
import java.util.List;


public class MapView extends View {
    Paint paint = new Paint();

    private List<LogAdapter.Point> points = new ArrayList<>();

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<LogAdapter.Point> getPoints() {
        return points;
    }

    public void setPoints(List<LogAdapter.Point> points) {
        this.points = points;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        for (LogAdapter.Point point : points) {
            paint.setColor(point.getColor());
            canvas.drawCircle((point.getX() * 2 - 134) / 2 * (float) 4 / 510 * getWidth(), getHeight() - (point.getY() * 2 - 124) / 2 * (float) 4 / 505 * getHeight(), 25, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
