package com.limbo.floatwindow

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import java.lang.ref.WeakReference

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 5:10 PM
 */
class FloatWindowImpl// 默认配置
    (private val builder: FloatBuilder) : IFloatWindow {
    // 属性
    private val windowParams by lazy { WindowManager.LayoutParams() }
    private val activityLifecycle by lazy { ActivityLifecycle(this) }

    private val handler = MyHandler(WeakReference(this))
    private var activity: Activity? = null
    private var windowManager: WindowManager? = null
    private var isShowing = false

    init {
        builder.run {
            windowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            windowParams.format = PixelFormat.TRANSLUCENT
            windowParams.windowAnimations = android.R.style.Animation_Toast
            windowParams.flags =
                (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_DIM_BEHIND )
        }
    }

    override fun show(activity: Activity) {
        try {// check
            if (builder.contentView == null){
                throw NullPointerException("contentView is must not be null")
            }

            hide()

            this.activity = activity

            handler.sendMessageDelayed(handler.obtainMessage(), 100)

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

                // 添加布局
                windowManager?.addView(contentView, windowParams)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun hide() {
        try {
            activity?.application?.unregisterActivityLifecycleCallbacks(activityLifecycle)

            if (!isShowing){
                return
            }
            isShowing = false

            windowManager?.removeView(builder.contentView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 更新视图
     */
    override fun notifyDataChange() {
        try {
            updateWindowParams()

            windowManager?.updateViewLayout(builder.contentView, windowParams)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 更新参数配置
     */
    private fun updateWindowParams(){
        builder.run {
            windowParams.gravity = gravity
            windowParams.dimAmount = dimAmount
            windowParams.x = absoluteXY.first
            windowParams.y = absoluteXY.second
            val flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            if (touchable) {
                windowParams.flags = windowParams.flags or flags
            } else {
                windowParams.flags = windowParams.flags and flags.inv()
            }
            // 绑定滑动
            draggable?.bindingFloatWindow(this@FloatWindowImpl)
        }
    }

    private class MyHandler(val weakReference: WeakReference<FloatWindowImpl>) :
        Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            weakReference.get()?.run {
                val token = activity?.window?.decorView?.windowToken
                if (token == null) {
                    handler.sendMessageDelayed(handler.obtainMessage(), 100)
                } else {
                    windowManager =
                        activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

                    windowParams.token = token
                    show()
                }
            }
        }
    }

}