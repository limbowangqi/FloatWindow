package com.limbo.floatwindow.draggable

import android.view.View
import com.limbo.floatwindow.FloatWindow
import com.limbo.floatwindow.IFloatWindow
import kotlin.math.abs


/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 6:13 PM
 */
abstract class BaseDraggable : View.OnTouchListener {

    private val floatBuilder by lazy { FloatWindow.init() }
    private var floatWindow: IFloatWindow? = null

    fun bindingFloatWindow(floatWindow: IFloatWindow) {
        this.floatWindow = floatWindow
        floatBuilder.contentView?.setOnTouchListener(this)
    }

    /**
     * 更新位置信息
     */
    protected fun updateLocation(x: Int?, y: Int?) {
        floatBuilder.setAbsoluteXY(
            x ?: floatBuilder.absoluteXY.first,
            y ?: floatBuilder.absoluteXY.second
        )
        floatWindow?.notifyDataChange()
    }

    protected fun isMovingXY(startX: Float, startY: Float, endX:Float, endY:Float):Boolean{
        if (abs(endX - startX) < 10 && abs(endY - startY) < 10){
            return false
        }
        return true
    }
}