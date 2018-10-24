package cn.ushang.plank.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class InclineTextView extends View {

    float labelLength=100;
    float contentTextSize=30;
    float marginStart=30;

    public InclineTextView(Context context) {
        super(context);
    }

    public InclineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawShape(canvas);
        drawText(canvas);
        canvas.restore();

    }

    private void drawShape(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(0xffCA435B);
        Path path=new Path();
        path.moveTo(marginStart,0);
        path.lineTo(labelLength,0);
        path.lineTo(0,labelLength);
        path.lineTo(0,marginStart);
        path.close();
        canvas.drawPath(path,paint);
    }

    private void drawText(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(contentTextSize);
        canvas.rotate(-45,labelLength/2,labelLength/2);
        canvas.translate(0,labelLength/2-contentTextSize);
        canvas.drawText("教练",labelLength/2-contentTextSize,contentTextSize/2,paint);
    }

}
