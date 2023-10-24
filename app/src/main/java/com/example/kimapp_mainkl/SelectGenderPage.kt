package com.example.kimapp_mainkl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.kimapp_mainkl.DataProvider

class SelectGenderPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_gender_page)

        val maleImageButton = findViewById<ImageButton>(R.id.maleImageButton);
        val femaleImageButton = findViewById<ImageButton>(R.id.femaleImageButton);
        val imageButton_selectGender = findViewById<ImageButton>(R.id.imageButton_selectGender)

        val txt_data: TextView = findViewById(R.id.txt_data);

        imageButton_selectGender.setOnClickListener{
            val intent = Intent(this,SelectPage::class.java)
            startActivity(intent);
        }

        maleImageButton.setOnClickListener {
            DataProvider.saveData("Gender_Of_male", 15)
            txt_data.text = DataProvider.getData("Gender_Of_male").toString()
        }
        femaleImageButton.setOnClickListener {
            DataProvider.saveData("Gender_Of_female", 20)
            txt_data.text = DataProvider.getData("Gender_Of_female").toString()
        }

    }
}