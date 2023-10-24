package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_next = findViewById<Button>(R.id.button_next);

        button_next.setBackgroundColor(Color.rgb(245,244,155))

        button_next.setOnClickListener{
            val intent = Intent(this,OperationPage::class.java);
            startActivity(intent);
        }
    }
}