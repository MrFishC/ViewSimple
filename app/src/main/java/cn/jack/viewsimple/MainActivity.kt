package cn.jack.viewsimple

import android.os.Bundle
import android.os.Handler
import android.text.method.Touch.scrollTo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import cn.jack.viewsimple.slide.SlideView4
import cn.jack.viewsimple.slide.SlideView6


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val targetView = findViewById<SlideView6>(R.id.slideView4)

        //view动画
//        targetView.animation = AnimationUtils.loadAnimation(this,R.anim.translate)
        //属性动画
//        ObjectAnimator.ofFloat(targetView,"translationX",0f,300f).setDuration(1000).start()

        targetView.smoothTo(-400,0)
    }
}