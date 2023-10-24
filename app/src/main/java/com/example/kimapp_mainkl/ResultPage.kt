package com.example.kimapp_mainkl

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ResultPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ringView = findViewById<View>(R.id.ring)
        val centerText = findViewById<TextView>(R.id.txt_Level)

        val Level = intent.getIntExtra("LEVEL", 0)
        changeColor(Level)

    }

    fun setRingColor(color: Int) {
        val ringDrawable =
            ContextCompat.getDrawable(this, R.drawable.green_circle) as GradientDrawable
        ringDrawable.setColor(color)
    }

    //接下來可以用這兩個參數來設定
    //setRingColor(Color.RED)
    //setTextColor(Color.BLUE)

    fun setTextColor(color: Int, centerText: TextView) {
        centerText.setTextColor(color)
    }

    fun changeColor(Level: Int) {
        val centerText = findViewById<TextView>(R.id.txt_Level)
        when (Level) {
            1 -> {
                setRingColor(Color.GREEN)
                setTextColor(Color.GREEN, centerText)
            }

            2 -> {
                setRingColor(Color.YELLOW)
                setTextColor(Color.YELLOW, centerText)
            }

            4 -> {
                setRingColor(Color.rgb(255, 179, 0))
                setTextColor(Color.rgb(255, 179, 0), centerText)
            }

            8 -> {
                setRingColor(Color.RED)
                setTextColor(Color.RED, centerText)
            }
        }
    }
}















