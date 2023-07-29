package cn.jack.viewsimple.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * @创建者 Jack
 * @创建时间 2023/7/27
 * @描述
 */
public class SlideView6 extends View {
    private Scroller mScroller;

    public SlideView6(Context context) {
        this(context,null);
    }

    public SlideView6(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideView6(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    public void smoothTo(int destX,int destY){
        int scrollX = getScrollX();
        int dest = destX - scrollX;
        mScroller.startScroll(scrollX,0,dest,0,2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

}
