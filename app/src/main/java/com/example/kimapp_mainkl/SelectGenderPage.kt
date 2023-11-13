package com.example.kimapp_mainkl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SelectGenderPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_gender_page)

        val maleImageButton = findViewById<ImageButton>(R.id.maleImageButton);
        val femaleImageButton = findViewById<ImageButton>(R.id.femaleImageButton);
        val imageButton_selectGender = findViewById<ImageButton>(R.id.imageButton_selectGender)

        imageButton_selectGender.setOnClickListener{
            val intent = Intent(this,SelectPage::class.java)
            startActivity(intent);
        }

        //選擇性別savedata為儲存值
        
        femaleImageButton.setOnClickListener {
            DataProvider.saveData("Gender_Of_value", 0)
            val intent = Intent(this,SelectPage::class.java)
            startActivity(intent);
        }

        maleImageButton.setOnClickListener {
            DataProvider.saveData("Gender_Of_value", 1)
            val intent = Intent(this,SelectPage::class.java)
            startActivity(intent);
        }

    }
}