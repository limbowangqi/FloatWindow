package com.limbo.window.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.limbo.window.app.databinding.ActivityMainBinding
import com.limbo.floatwindow.FloatWindow;
import com.limbo.floatwindow.draggable.MovingDraggable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        val view = LayoutInflater.from(this).inflate(R.layout.view_float_window, null)
//        view.setOnClickListener{
//            Toast.makeText(this, "111", Toast.LENGTH_SHORT).show()
//        }
        FloatWindow.init()
            .setContentView(view)
            .setDraggable(MovingDraggable())
            .setAbsoluteXY(100,100)

        // show
        binding.btnShow.setOnClickListener {
            FloatWindow.getInstance().show(this)
        }
        // hide
        binding.btnClose.setOnClickListener { FloatWindow.getInstance().hide() }

        // jump
        binding.btnJump.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SecondActivity::class.java
                )
            )
        }

    }
}