package com.zerogerc.application;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class PointDrawer extends View {

    boolean needToDraw = false;

    int lastX = -1;

    int lastY = -1;

    int dx = 0;

    private final Paint paint = new Paint();

    private int touchSlop;

    public PointDrawer(Context context) {
        super(context);
        init();
    }

    public PointDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PointDrawer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                needToDraw = false;
                lastX = ((int) event.getX());
                lastY = ((int) event.getY());
                dx = 0;
                return true;
            case MotionEvent.ACTION_UP:
                if (Math.abs(dx) <= touchSlop) {
                    needToDraw = true;
                    lastX = ((int) event.getX());
                    lastY = ((int) event.getY());
                    invalidate();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                dx += event.getX() - lastX;
                break;
            default:
                break;
        }

        lastX = ((int) event.getX());
        lastY = ((int) event.getY());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 20; i++) {
            canvas.drawCircle(200 * i, 200, 50, paint);
        }

        if (needToDraw) {
            canvas.drawCircle(lastX, lastY, 50, paint);
        }
    }

    private void init() {
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        touchSlop = ViewConfiguration.get(getContext()).getScaledDoubleTapSlop();
    }
}
