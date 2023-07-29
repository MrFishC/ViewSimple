package cn.jack.viewsimple.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * @创建者 Jack
 * @创建时间 2023/7/27
 * @描述
 */
public class SlideView5 extends View {
    private int mLastX;
    private int mLastY;

    public SlideView5(Context context) {
        super(context);
    }

    public SlideView5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideView5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取触摸点的坐标信息
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offsetx = x - mLastX;
                int offsety = y - mLastY;

                ((View) getParent()).scrollBy(-offsetx, -offsety);//偏移量需要设置为负值,因为参考对象不同
        }
        return true;
    }
}
