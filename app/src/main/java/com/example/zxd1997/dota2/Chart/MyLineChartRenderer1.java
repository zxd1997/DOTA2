package com.example.zxd1997.dota2.Chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.renderer.AbstractChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public class MyLineChartRenderer1 extends AbstractChartRenderer {
    private static final float LINE_SMOOTHNESS = 0.16f;
    private static final int DEFAULT_LINE_STROKE_WIDTH_DP = 3;
    private static final int DEFAULT_TOUCH_TOLERANCE_MARGIN_DP = 4;

    private static final int MODE_DRAW = 0;
    private static final int MODE_HIGHLIGHT = 1;
    private int dir;
    private final LineChartDataProvider dataProvider;

    private final int checkPrecision;

    private float baseValue;

    private final int touchToleranceMargin;
    private final Path path = new Path();
    private final Paint linePaint = new Paint();
    private final Paint pointPaint = new Paint();

    private Bitmap softwareBitmap;
    private final Canvas softwareCanvas = new Canvas();
    private final Viewport tempMaximumViewport = new Viewport();
    //    private List<Float> preTop;
//    private List<Float> preBot;
    private float preBot;
    private float preTop;

    public MyLineChartRenderer1(Context context, Chart chart, LineChartDataProvider dataProvider) {
        super(context, chart);
        this.dataProvider = dataProvider;
        int lineCount = this.dataProvider.getLineChartData().getLines().size();
        touchToleranceMargin = ChartUtils.dp2px(density, DEFAULT_TOUCH_TOLERANCE_MARGIN_DP);

        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(ChartUtils.dp2px(density, DEFAULT_LINE_STROKE_WIDTH_DP));

        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);

        checkPrecision = ChartUtils.dp2px(density, 2);

    }

    public void onChartSizeChanged() {
        final int internalMargin = calculateContentRectInternalMargin();
        computator.insetContentRectByInternalMargins(internalMargin, internalMargin,
                internalMargin, internalMargin);
        if (computator.getChartWidth() > 0 && computator.getChartHeight() > 0) {
            softwareBitmap = Bitmap.createBitmap(computator.getChartWidth(), computator.getChartHeight(),
                    Bitmap.Config.ARGB_8888);
            softwareCanvas.setBitmap(softwareBitmap);
        }
    }

    @Override
    public void onChartDataChanged() {
        super.onChartDataChanged();
        final int internalMargin = calculateContentRectInternalMargin();
        computator.insetContentRectByInternalMargins(internalMargin, internalMargin,
                internalMargin, internalMargin);
        baseValue = dataProvider.getLineChartData().getBaseValue();
        onChartViewportChanged();
    }

    @Override
    public void onChartViewportChanged() {
        if (isViewportCalculationEnabled) {
            calculateMaxViewport();
            computator.setMaxViewport(tempMaximumViewport);
            computator.setCurrentViewport(computator.getMaximumViewport());
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final LineChartData data = dataProvider.getLineChartData();

        final Canvas drawCanvas;

        // softwareBitmap can be null if chart is rendered in layout editor. In that case use default canvas and not
        // softwareCanvas.
        if (null != softwareBitmap) {
            drawCanvas = softwareCanvas;
            drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        } else {
            drawCanvas = canvas;
        }

        for (Line line : data.getLines()) {
            if (line.hasLines()) {
                if (line.isCubic()) {
                    drawSmoothPath(drawCanvas, line);
                } else if (line.isSquare()) {
                    drawSquarePath(drawCanvas, line);
                } else {
                    drawPath(drawCanvas, line);
                }
            }
        }

        if (null != softwareBitmap) {
            canvas.drawBitmap(softwareBitmap, 0, 0, null);
        }
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
        final LineChartData data = dataProvider.getLineChartData();
        int lineIndex = 0;
        for (Line line : data.getLines()) {
            if (checkIfShouldDrawPoints(line)) {
                drawPoints(canvas, line, lineIndex, MODE_DRAW);
            }
            ++lineIndex;
        }
        if (isTouched()) {
            // Redraw touched point to bring it to the front
            highlightPoints(canvas);
        }
    }

    private boolean checkIfShouldDrawPoints(Line line) {
        return line.hasPoints() || line.getValues().size() == 1;
    }

    @Override
    public boolean checkTouch(float touchX, float touchY) {
        selectedValue.clear();
        final LineChartData data = dataProvider.getLineChartData();
        int lineIndex = 0;
        for (Line line : data.getLines()) {
            if (checkIfShouldDrawPoints(line)) {
                int pointRadius = ChartUtils.dp2px(density, line.getPointRadius());
                int valueIndex = 0;
                for (PointValue pointValue : line.getValues()) {
                    final float rawValueX = computator.computeRawX(pointValue.getX());
                    final float rawValueY = computator.computeRawY(pointValue.getY());
                    if (isInArea(rawValueX, rawValueY, touchX, touchY, pointRadius + touchToleranceMargin)) {
                        selectedValue.set(lineIndex, valueIndex, SelectedValue.SelectedValueType.LINE);
                    }
                    ++valueIndex;
                }
            }
            ++lineIndex;
        }
        return isTouched();
    }

    private void calculateMaxViewport() {
        tempMaximumViewport.set(Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MAX_VALUE);
        LineChartData data = dataProvider.getLineChartData();

        for (Line line : data.getLines()) {
            // Calculate max and min for viewport.
            for (PointValue pointValue : line.getValues()) {
                if (pointValue.getX() < tempMaximumViewport.left) {
                    tempMaximumViewport.left = pointValue.getX();
                }
                if (pointValue.getX() > tempMaximumViewport.right) {
                    tempMaximumViewport.right = pointValue.getX();
                }
                if (pointValue.getY() < tempMaximumViewport.bottom) {
                    tempMaximumViewport.bottom = pointValue.getY();
                }
                if (pointValue.getY() > tempMaximumViewport.top) {
                    tempMaximumViewport.top = pointValue.getY();
                }

            }
        }
    }

    private int calculateContentRectInternalMargin() {
        int contentAreaMargin = 0;
        final LineChartData data = dataProvider.getLineChartData();
        for (Line line : data.getLines()) {
            if (checkIfShouldDrawPoints(line)) {
                int margin = line.getPointRadius() + DEFAULT_TOUCH_TOLERANCE_MARGIN_DP;
                if (margin > contentAreaMargin) {
                    contentAreaMargin = margin;
                }
            }
        }
        return ChartUtils.dp2px(density, contentAreaMargin);
    }

    /**
     * Draws lines, uses path for drawing filled area on software canvas. Line is drawn with canvas.drawLines() method.
     */
    private void drawPath(Canvas canvas, final Line line) {
        prepareLinePaint(line);

        int valueIndex = 0;
        for (PointValue pointValue : line.getValues()) {

            final float rawX = computator.computeRawX(pointValue.getX());
            final float rawY = computator.computeRawY(pointValue.getY());

            if (valueIndex == 0) {
                path.moveTo(rawX, rawY);
            } else {
                path.lineTo(rawX, rawY);
            }

            ++valueIndex;

        }

        canvas.drawPath(path, linePaint);

        if (line.isFilled()) {
            drawArea(canvas, line);
        }

        path.reset();
    }

    private void drawSquarePath(Canvas canvas, final Line line) {
        prepareLinePaint(line);

        int valueIndex = 0;
        float previousRawY = 0;
        for (PointValue pointValue : line.getValues()) {

            final float rawX = computator.computeRawX(pointValue.getX());
            final float rawY = computator.computeRawY(pointValue.getY());

            if (valueIndex == 0) {
                path.moveTo(rawX, rawY);
            } else {
                path.lineTo(rawX, previousRawY);
                path.lineTo(rawX, rawY);
            }

            previousRawY = rawY;

            ++valueIndex;

        }

        canvas.drawPath(path, linePaint);

        if (line.isFilled()) {
            drawArea(canvas, line);
        }

        path.reset();
    }

    private void drawSmoothPath(Canvas canvas, final Line line) {
        prepareLinePaint(line);

        final int lineSize = line.getValues().size();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;

        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                PointValue linePoint = line.getValues().get(valueIndex);
                currentPointX = computator.computeRawX(linePoint.getX());
                currentPointY = computator.computeRawY(linePoint.getY());
            }
            if (Float.isNaN(previousPointX)) {
                if (valueIndex > 0) {
                    PointValue linePoint = line.getValues().get(valueIndex - 1);
                    previousPointX = computator.computeRawX(linePoint.getX());
                    previousPointY = computator.computeRawY(linePoint.getY());
                } else {
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                if (valueIndex > 1) {
                    PointValue linePoint = line.getValues().get(valueIndex - 2);
                    prePreviousPointX = computator.computeRawX(linePoint.getX());
                    prePreviousPointY = computator.computeRawY(linePoint.getY());
                } else {
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // nextPoint is always new one or it is equal currentPoint.
            if (valueIndex < lineSize - 1) {
                PointValue linePoint = line.getValues().get(valueIndex + 1);
                nextPointX = computator.computeRawX(linePoint.getX());
                nextPointY = computator.computeRawY(linePoint.getY());
            } else {
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // Move to start point.
                path.moveTo(currentPointX, currentPointY);
            } else {
                // Calculate control points.
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (LINE_SMOOTHNESS * firstDiffX);
                final float firstControlPointY = previousPointY + (LINE_SMOOTHNESS * firstDiffY);
                final float secondControlPointX = currentPointX - (LINE_SMOOTHNESS * secondDiffX);
                final float secondControlPointY = currentPointY - (LINE_SMOOTHNESS * secondDiffY);
                path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
            }

            // Shift values by one back to prevent recalculation of values that have
            // been already calculated.
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }

        canvas.drawPath(path, linePaint);
        if (line.isFilled()) {
            drawArea(canvas, line);
        }
        path.reset();
    }

    private void prepareLinePaint(final Line line) {
        linePaint.setStrokeWidth(ChartUtils.dp2px(density, line.getStrokeWidth()));
        linePaint.setColor(line.getColor());
        linePaint.setPathEffect(line.getPathEffect());
    }

    private void drawPoints(Canvas canvas, Line line, int lineIndex, int mode) {
        pointPaint.setColor(line.getPointColor());
        int valueIndex = 0;
        for (PointValue pointValue : line.getValues()) {
            int pointRadius = ChartUtils.dp2px(density, line.getPointRadius());
            final float rawX = computator.computeRawX(pointValue.getX());
            final float rawY = computator.computeRawY(pointValue.getY());
            if (computator.isWithinContentRect(rawX, rawY, checkPrecision)) {
                // Draw points only if they are within contentRectMinusAllMargins, using contentRectMinusAllMargins
                // instead of viewport to avoid some
                // float rounding problems.
                switch (mode) {
                    case MODE_DRAW:
                        drawPoint(canvas, line, pointValue, rawX, rawY, pointRadius);
                        if (line.hasLabels()) {
                            drawLabel(canvas, line, pointValue, rawX, rawY, pointRadius + labelOffset);
                        }
                        break;
                    case MODE_HIGHLIGHT:
                        highlightPoint(canvas, line, pointValue, rawX, rawY, lineIndex, valueIndex);
                        break;
                    default:
                        throw new IllegalStateException("Cannot process points in mode: " + mode);
                }
            }
            ++valueIndex;
        }
    }

    private void drawPoint(Canvas canvas, Line line, PointValue pointValue, float rawX, float rawY,
                           float pointRadius) {
        if (ValueShape.SQUARE.equals(line.getShape())) {
            canvas.drawRect(rawX - pointRadius, rawY - pointRadius, rawX + pointRadius, rawY + pointRadius,
                    pointPaint);
        } else if (ValueShape.CIRCLE.equals(line.getShape())) {
            canvas.drawCircle(rawX, rawY, pointRadius, pointPaint);
        } else if (ValueShape.DIAMOND.equals(line.getShape())) {
            canvas.save();
            canvas.rotate(45, rawX, rawY);
            canvas.drawRect(rawX - pointRadius, rawY - pointRadius, rawX + pointRadius, rawY + pointRadius,
                    pointPaint);
            canvas.restore();
        } else {
            throw new IllegalArgumentException("Invalid point shape: " + line.getShape());
        }
    }

    private void highlightPoints(Canvas canvas) {
        final int lineIndex = selectedValue.getFirstIndex();
        final int valueIndex = selectedValue.getSecondIndex();
        List<Line> lines = dataProvider.getLineChartData().getLines();
        preBot = 0;
        preTop = 0;

//        Log.d("label", "highlightPoints: " + chart.getMaximumViewport().centerY());
        Collections.sort(lines, (o1, o2) -> (int) ((o2.getValues().get(valueIndex).getY() - o1.getValues().get(valueIndex).getY()) * 1000));
        for (Line line : lines) {
            drawPoints(canvas, line, lineIndex, MODE_HIGHLIGHT);
        }
    }

    private void highlightPoint(Canvas canvas, Line line, PointValue pointValue, float rawX, float rawY, int lineIndex,
                                int valueIndex) {
        if (selectedValue.getFirstIndex() == lineIndex && selectedValue.getSecondIndex() == valueIndex) {
            int pointRadius = ChartUtils.dp2px(density, line.getPointRadius());
            pointPaint.setColor(line.getDarkenColor());
            drawPoint(canvas, line, pointValue, rawX, rawY, pointRadius + touchToleranceMargin);
            if (line.hasLabels() || line.hasLabelsOnlyForSelected()) {
                drawLabel(canvas, line, pointValue, rawX, -10, pointRadius + labelOffset);
            }
        }
    }

    private void drawLabel(Canvas canvas, Line line, PointValue pointValue, float rawX, float rawY, float offset) {
        final Rect contentRect = computator.getContentRectMinusAllMargins();
        final int numChars = line.getFormatter().formatChartValue(labelBuffer, pointValue);
        if (numChars == 0) {
            // No need to draw empty label
            return;
        }

        final float labelWidth = labelPaint.measureText(labelBuffer, labelBuffer.length - numChars, numChars);
        final int labelHeight = Math.abs(fontMetrics.ascent);
        float left = rawX + offset;
        float right = rawX + labelWidth + labelMargin * 2 + offset;
        float top = rawY + offset;
        float bottom = rawY + offset + labelHeight + labelMargin * (float) 1.3;
        if (left < contentRect.left) {
            left = rawX + offset;
            right = rawX + offset + labelWidth + labelMargin * 2;
        }
        if (right > contentRect.right) {
            left = rawX - offset - labelWidth - labelMargin * 2;
            right = rawX - offset;
        }
        if (preTop == 0 && preBot == 0) {
            preTop = top;
            preBot = bottom;
        } else {
//            Log.d("label", "drawLabel: " + dir);
            top = preBot + labelMargin;
            bottom = top + labelHeight + labelMargin * (float) 1.3;
            preTop = top;
            preBot = bottom;
        }
        labelBackgroundRect.set(left, top, right, bottom);
        drawLabelTextAndBackground(canvas, labelBuffer, labelBuffer.length - numChars, numChars,
                line.getDarkenColor());
    }

    private void drawArea(Canvas canvas, Line line) {
        final int lineSize = line.getValues().size();
        if (lineSize < 2) {
            //No point to draw area for one point or empty line.
            return;
        }

        final Rect contentRect = computator.getContentRectMinusAllMargins();
        final float baseRawValue = Math.min(contentRect.bottom, Math.max(computator.computeRawY(baseValue),
                contentRect.top));
        //That checks works only if the last point is the right most one.
        final float left = Math.max(computator.computeRawX(line.getValues().get(0).getX()), contentRect.left);
        final float right = Math.min(computator.computeRawX(line.getValues().get(lineSize - 1).getX()),
                contentRect.right);

        path.lineTo(right, baseRawValue);
        path.lineTo(left, baseRawValue);
        path.close();

        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setAlpha(line.getAreaTransparency());
        canvas.drawPath(path, linePaint);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    private boolean isInArea(float x, float y, float touchX, float touchY, float radius) {
        float diffX = touchX - x;
        float diffY = touchY - y;
        return Math.pow(diffX, 2) + Math.pow(diffY, 2) <= 2 * Math.pow(radius, 2);
    }

}
