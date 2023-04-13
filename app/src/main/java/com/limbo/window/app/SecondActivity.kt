package com.limbo.window.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.limbo.window.app.databinding.ActivitySecondBinding
import com.limbo.floatwindow.FloatWindow;

/**
 *
 * @Description: 类注释
 * @Author: slong
 * @CreateDate: 2023/4/12 3:27 PM
 */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {
            FloatWindow.getInstance().show(this)
        }
        binding.btnClose.setOnClickListener { FloatWindow.getInstance().hide() }
    }
}