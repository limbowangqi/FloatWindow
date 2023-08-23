package com.limbo.floatwindow

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 5:10 PM
 */
class FloatWindowImpl// 默认配置
    (private val builder: FloatBuilder) : IFloatWindow {
    // 属性
    private val layoutParams by lazy {
        FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }
    private val activityLifecycle by lazy { ActivityLifecycle(this) }

    private var activity: Activity? = null

    private var isShowing = false

    override fun show(activity: Activity) {
        try {// check
            if (builder.contentView == null) {
                throw NullPointerException("contentView is must not be null")
            }

            hide()

            this.activity = activity

            show()

            // 设置监听
            activity.application?.registerActivityLifecycleCallbacks(activityLifecycle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun show() {
        try {
            if (isShowing) {
                return
            }
            isShowing = true

            // 开始展示
            builder.run {
                // 自定义配置
                updateWindowParams()

                if (contentView?.parent != null) {
                    (contentView?.parent as ViewGroup?)?.removeView(contentView)
                }
                getViewGroup(activity)?.addView(contentView, layoutParams)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun hide() {
        try {
            if (!isShowing) {
                return
            }
            isShowing = false

            activity?.application?.unregisterActivityLifecycleCallbacks(activityLifecycle)

            getViewGroup(activity)?.removeView(builder.contentView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun isShowing(): Boolean {
        return isShowing
    }

    /**
     * 更新视图
     */
    override fun notifyDataChange() {
        try {
            updateWindowParams()

            getViewGroup(activity)?.updateViewLayout(builder.contentView, layoutParams)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 更新参数配置
     */
    private fun updateWindowParams() {
        builder.run {
            contentView?.measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            var left = absoluteXY.first
            if (absoluteXY.first < 0){
                left = 0
            }else if (absoluteXY.first > (getScreenWidth(activity!!) - (contentView?.measuredWidth?:0))){
                left = (getScreenWidth(activity!!) - (contentView?.measuredWidth?:0))
            }

            var top = absoluteXY.second
            if (absoluteXY.second < 0){
                top = 0
            }else if (absoluteXY.second > (getScreenHeight(activity!!) - (contentView?.measuredHeight?:0))){
                top = (getScreenHeight(activity!!) - (contentView?.measuredHeight?:0))
            }

            builder.setAbsoluteXY(left, top)

            layoutParams.setMargins(
                left,
                top,
                0,
                0
            )

            // 绑定滑动
            draggable?.bindingFloatWindow(this@FloatWindowImpl)
        }
    }

    private fun getViewGroup(activity: Activity?): ViewGroup? {
        return activity?.findViewById<ViewGroup>(android.R.id.content)
    }

    private fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    private fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

//    private class MyHandler(val weakReference: WeakReference<FloatWindowImpl>) :
//        Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            weakReference.get()?.run {
//                val token = activity?.window?.decorView?.windowToken
//                if (token == null) {
//                    handler.sendMessageDelayed(handler.obtainMessage(), 100)
//                } else {
//                    windowManager =
//                        activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
//
//                    windowParams.token = token
//                    show()
//                }
//            }
//        }
//    }

}