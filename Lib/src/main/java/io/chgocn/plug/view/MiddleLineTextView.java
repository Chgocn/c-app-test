package io.chgocn.plug.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * offer text show at middle line when change line
 * ref:{http://blog.csdn.net/lcq5211314123/article/details/46722737}
 * Created by chgocn(chgocn@gmail.com).
 */
public class MiddleLineTextView extends TextView{
    public MiddleLineTextView(Context context) {
        super(context);
    }

    public MiddleLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MiddleLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private StaticLayout mStaticLayout;
    private TextPaint mTextPaint;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
    }

    private void initView() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getCurrentTextColor());
        mStaticLayout = new StaticLayout(getText(), mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStaticLayout.draw(canvas);
    }
}
