package com.limbo.floatwindow

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 12:03 PM
 */
class ActivityLifecycle(private val floatWindow: IFloatWindow) : Application.ActivityLifecycleCallbacks {


    override fun onActivityResumed(activity: Activity) {
        floatWindow.show(activity)
    }

    override fun onActivityPaused(activity: Activity) {
//        floatWindow.hide()
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
    }


    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}