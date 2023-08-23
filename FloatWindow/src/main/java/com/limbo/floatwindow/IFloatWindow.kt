package com.limbo.floatwindow

import android.app.Activity

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 5:09 PM
 */
interface IFloatWindow {

    fun show(activity:Activity)

    fun hide()

    fun isShowing():Boolean

    fun notifyDataChange()
}