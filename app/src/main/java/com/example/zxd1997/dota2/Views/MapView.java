package com.example.zxd1997.dota2.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.zxd1997.dota2.Adapters.LogAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.graphics.BitmapFactory.decodeResource;


public class MapView extends View {
    private final Paint paint = new Paint();

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
        for (LogAdapter.Point point : points) {
            canvas.drawBitmap(decodeResource(Objects.requireNonNull(getContext()).getResources(), point.getColor()), point.getX() * getWidth() - 32, getHeight() - point.getY() * getHeight() - 32, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
