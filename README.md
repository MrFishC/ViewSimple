# 前言

+ 总结View相关的知识
  + 基础知识-坐标系
  + 滑动
  + 弹性滑动
  + 动画
  + **事件分发**
  + **View的工作流程**
  + 自定义控件

# 一.基础部分
+ View的体系结构
  + 继承关系
+ 认识MotionEvent
  + 当用户触摸屏幕时将创建一个MotionEvent对象，其包含了触摸位置和时间等细节信息；

+ Android的坐标系
  + 坐标原点：屏幕左上角
    + 原点向右，X轴正方向
    + 原点向下，Y轴正方向
+ View坐标系
  + 函数（相对于父控件而言）
    + getTop()：子View左上角距父View顶部的距离
    + getBottom()：子View右下角距父View左侧的距离 
    + getLeft()：子View左上角距父View左侧的距离
    + getRight()：子View右下角距父View顶部的距离
  + 可以根据上方的4个函数获取view的宽高

+ MotionEvent提供的函数
  + getX()：触摸点到View的左边界的距离
  + getY()：触摸点到View的顶部的距离
  + getRowX()：触摸点到屏幕左边界的距离
  + getRowY()：触摸点到屏幕上边界的距离
+ MeasureSpec
  + 每一个view都持有;
  + 作用:在view的onMeasure方法中,根据MeasureSpec来确定view的宽高;
  + 由自己的layoutparams和父容器的MeasureSpec共同来决定;
  + view的内部类,封装了view的规格信息,32位的int值,高2位表示测量模式,低30位表示测量大小;
  + 测量模式
    + UNSPECIFIED:(一般用于系统内部的测量,一般不用关注此模式)父view对子view不做限制;
    + AT_MOST:子view的大小不超过(根据MeasureSpec获取的)SpecSize值;
    + EXACTLY:子view的大小等于(根据MeasureSpec获取的)SpecSize值;

# 二.View的滑动
## 2.1.layout实现
## 2.2.offsetLeftAndRight和offsetTopAndBottom实现
## 2.3.setLayoutParams实现
## 2.4.动画实现(view动画或属性动画)
## 2.5.scrollTo与scrollBy实现
  + scrollTo(x,y):移动到
  + scrollBy(x,y):移动的增量,底层调用了scrollTo
  + scrollTo和scrollBy移动的是view的内容,若在viewGroup中使用,移动的是子view
## 2.6.scroller实现
  + 需要view的computeScroll方法配合
  + scroller原理
    + startScroll方法 --调用--> invalidate方法 --导致--> view的重绘 --调用--> view的draw方法 --调用--> view的computeScroll方法,
    通过重写computeScroll方法,在该方法中通过scroller来获取当前的ScrollX和ScrollY，然后调用scrollTo（）方法进行View的滑动. --->
    如此不断地移动小的距离并连贯起来从而实现平滑移动;
    + **原理总结**:scroller并不能直接让view进行滑动,需要配合view的computeScroll方法.在computeScroll方法中不断地让view进行重绘,每次重绘都会计算
    view滑动的时间,根据时间计算滑动的位置,根据滑动的位置调用scrollTo方法进行滑动,不断地重复该过程实现弹性滑动;

# 三.动画
+ view动画
  + 分类
    + AlphaAnimation:渐变
    + RotateAnimation:旋转
    + TranslateAnimation:平移
    + ScaleAnimation:缩放
    + AnimationSet:动画集(混合使用多种动画)
  + 弊端:不改变view的位置
+ 帧动画
+ 属性动画(Android3.0之后推出,基本上能实现所有的动画效果)
  + ObjectAnimator(常用)
    + 创建:通过静态工厂类来创建(参数,1个对象和对象属性**该属性必须要有get和set方法,不然ObjectAnimator就无法生效.属性的get和set方法可以直接或间接地自定义增加**的名称)
  + 动画的组合:多个ObjectAnimator组合到AnimatorSet形成一个动画
    + 其它:动画的监听,在xml中使用,PropertyValuesHolder的使用;

# 四.Activity的组成
## 4.1.Activity的组成
+ Activity
  --- PhoneWindow
    --- DecorView
      --- Title
      --- Content
## 4.2.DecorView如何被加载到window中
+ 前置知识:需要了解Activity的加载流程
+ 简要描述:调用WindowManager的addView方法 ---> WindowManagerImpl的addView方法 ---> WindowManagerGlobal的addView方法 
  ---> **创建ViewRootImpl,调用其setView方法将DecorView添加到Window中**;

# 五.事件分发
+ 事件分发:点击屏幕产生的事件被封装成MotionEvent类,MotionEvent在view层级的传递(即MotionEvent的分发过程);
  + 是解决滑动冲突的基础
+ 三个重要方法
  + dispatchTouchEvent:分发,返回结果受当前的onTouchEvent和下一级dispatchTouchEvent的影响;
  + onInterceptTouchEvent:拦截,如果当前view拦截了某个事件,那么在同一个事件序列中,此方法不会被再次被调用;
  + onTouchEvent:处理,表示是否消费当前事件,若不消费,那么在同一个事件序列中,当前view无法再次接收到事件;
+ onTouchListener,onTouchEvent,onClickListener的优先级,依次由高到低;
+ 事件首先会传递给Activity ---> 调用Activity的分发方法(具体的处理工作是由PhoneWindow来完成 ---> 交给DecorView ---> 交给根ViewGroup)
  + 即:Activity ---> Window ---> View
+ 事件分发的传递规则
  + 事件由上而下的传递:Activity ---> PhoneWindow ---> DecorView ---> 顶层的ViewGroup,对于顶层的ViewGroup,首先事件会传递给其dispatchTouchEvent方法,若其
    onInterceptTouchEvent方法返回true,表示需要拦截事件,事件就会交由其onTouchEvent方法.若其onInterceptTouchEvent方法返回false,事件就会交由其子view的
    dispatchTouchEvent方法.如此反复,假设传给底层的view,底层的view没有子view,事件会交给view的dispatchTouchEvent方法,一般情况下,最终会调用其onTouchEvent方法;
  + 事件由下而上的传递:若底层的onTouchEvent方法方法true,则事件被消费掉,若返回false事件会回传给父view的onTouchEvent方法,若父view的onTouchEvent返回false,则继续
    将事件传给父view的父view,如此反复;

# 六.View的工作流程
+ 实际上就是view的测量,布局和绘制,对应的三个方法,measure,layout和draw;
+ View工作流程的入口:ViewRootlmpl的PerformTraveals方法 ---> 使得ViewTree开始View的工作流程
  + 主要方法
    + performMeasure:对应view的measure
    + performLayout:对应view的layout
    + performDraw:对应view的draw
+ view的measure(测量自己的宽高)和viewGroup的measure(测量自己和测量子view的宽高)
  + 前者(细节):specMode在AT_MOST和EXACTLY模式下,都返回SepSize这个值;
    + 自定义控件时,重写onMeasure方法,当specMode为wrap_content时,设置默认值;
  + view的measure过程跟Activity的生命周期不是同步执行的,对应有四种处理方案;
    + 在Activity/View#onWindowFocusChanged方法中获取view的宽高
    + 调用view.post(runnable),在runnable中获取宽高
    + 使用ViewTreeObserver
    + 手动对View进行measure来获取宽高
+ view的layout
  + 确定元素的位置
+ view的draw
  + 将view绘制到屏幕上

# 七.自定义view
## 7.1.分类
+ 自定义view
+ 自定义viewGroup
+ 组合

## 7.2.处理滑动冲突
  + 外部拦截法:重写onInterceptTouchEvent方法,定义变量作为结果值返回,在move事件中根据条件判断是否需要进行拦截,对变量设置true或false(down事件,变量必须设置为false);
    + 注意:若拦截了move事件,即便在up事件中将变量返回false,也不会传递到给子view中.
  + 内部拦截法:父容器不拦截,所有的事件都传递给子元素,若子元素需要此事件就消费掉,需要配合requestDisallowInterceptTouchEvent方法实现;
  + 外部拦截结合内部拦截;

## 7.3.注意事项
+ 让view支持wrap_content
+ padding不生效的处理方式
  + 重写onDraw方法进行处理

+ 参考书籍
  + 《Android进阶之光》
  + 《Android开发艺术探索》

