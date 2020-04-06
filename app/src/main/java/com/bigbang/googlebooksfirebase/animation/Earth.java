package com.bigbang.googlebooksfirebase.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigbang.googlebooksfirebase.R;

public class Earth extends View {

    private Bitmap earth;
    private float xPosition = 0f;
    private float yPosition = 0f;

    private float speed = 10.25f;

    private float xSpeed = speed;
    private float ySpeed = speed;
    private Paint paint = new Paint();

    public Earth(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        earth = BitmapFactory.decodeResource(getResources(), R.drawable.earth);
        earth = Bitmap.createScaledBitmap(earth, (earth.getWidth() / 2), (earth.getHeight() / 2), false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0x00000000);

        if (xPosition > (getWidth() - earth.getWidth()) || xPosition < 0)
            xSpeed *= -1;
        if (yPosition > (getHeight() - earth.getHeight()) || yPosition < 0)
            ySpeed *= -1;

        xPosition += xSpeed;
        yPosition += ySpeed;

        canvas.drawBitmap(earth, xPosition, yPosition, paint);
        invalidate();
    }
}
