package cn.jack.viewsimple.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @创建者 Jack
 * @创建时间 2023/7/27
 * @描述
 */
public class SlideView1 extends View {
    private int mLastX;
    private int mLastY;

    public SlideView1(Context context) {
        super(context);
    }

    public SlideView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取触摸点的坐标信息
        int x = (int) event.getX();
        int y = (int) event.getY();

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offsetx = x - mLastX;
                int offsety = y - mLastY;

                layoutParams.leftMargin += offsetx;
                layoutParams.topMargin += offsety;
                //使用setLayoutParams改变布局参数
                setLayoutParams(layoutParams);
        }
        return true;
    }
}
