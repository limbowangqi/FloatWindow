package com.limbo.floatwindow

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 4:57 PM
 */
object FloatWindow {

    private val builder by lazy { FloatBuilder() }

    private val floatWindow by lazy { FloatWindowImpl(builder) }

    fun init(): FloatBuilder {
        return builder
    }

    fun getInstance(): IFloatWindow {
        return floatWindow
    }
}