package com.limbo.floatwindow

import android.view.Gravity
import android.view.View
import androidx.annotation.FloatRange
import com.limbo.floatwindow.draggable.BaseDraggable

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 4:59 PM
 */
class FloatBuilder {

    // view
    internal var contentView: View? = null

    // 相对位置
//    internal var gravity: Int = Gravity.TOP or Gravity.START

    // 绝对位置xy坐标
    internal var absoluteXY: Pair<Int, Int> = Pair(0, 0)

//    // 外部阴影0-1
//    internal var dimAmount: Float = 0f
//
//    // 是否外部可以点击
//    internal var touchable: Boolean = true

    //是否可以拖拽
    internal var draggable: BaseDraggable? = null

    fun setContentView(view: View): FloatBuilder {
        this.contentView = view
        return this
    }


//    fun setGravity(gravity: Int): FloatBuilder {
//        this.gravity = gravity
//        return this
//    }


    fun setAbsoluteXY(x: Int, y: Int): FloatBuilder {
        absoluteXY = Pair(x, y)
        return this
    }


//    fun setBackgroundDimAmount(@FloatRange(from = 0.0, to = 1.0) amount: Float): FloatBuilder {
//        this.dimAmount = amount
//        return this
//    }


//    fun setOutsideTouchable(touchable: Boolean): FloatBuilder {
//        this.touchable = touchable
//        return this
//    }


    fun setDraggable(draggable: BaseDraggable): FloatBuilder {
        this.draggable = draggable
        return this
    }
}