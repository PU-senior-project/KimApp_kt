package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup

class SelectMaterialPage_carry_female : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_material_page_carry_female)//用來顯示頁面Page.kt

        val imageButton_selectMaterial_back = findViewById<ImageButton>(R.id.imageButton_selectMaterial_back)
        val select_material_page_transport_next = findViewById<Button>(R.id.select_material_page_transport_next)
        val radiogroup_transport = findViewById<RadioGroup>(R.id.radiogroup_transport)

        select_material_page_transport_next.setBackgroundColor(Color.rgb(245,244,155))

        imageButton_selectMaterial_back.setOnClickListener{
            val intent = Intent(this,SelectPage::class.java);
            startActivity(intent);
        }

        select_material_page_transport_next.setOnClickListener{
            val intent = Intent(this,SelectMaterialPage_State::class.java);
            startActivity(intent);
        }

        radiogroup_transport.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedValue_carry = selectedRadioButton.tag.toString()
            val selectedValue_carry_int = selectedValue_carry.toInt()
            DataProvider.saveData("selectedValue_carry", selectedValue_carry_int)
        }

    }
}