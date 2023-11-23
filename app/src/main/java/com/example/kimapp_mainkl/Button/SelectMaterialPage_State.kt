package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

class SelectMaterialPage_State : AppCompatActivity() {
    private var hasSelectedstate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_material_page_state)

        val imageButton_selectMaterial_state_back = findViewById<ImageButton>(R.id.imageButton_selectMaterial_state_back)
        val select_material_page_state_next = findViewById<Button>(R.id.select_material_page_state_next)
        val radiogroup_state = findViewById<RadioGroup>(R.id.radiogroup_state)

        select_material_page_state_next.setBackgroundColor(Color.rgb(245,244,155))

        imageButton_selectMaterial_state_back.setOnClickListener{
            val intent = Intent(this,SelectMaterialPage_carry_male::class.java);
            startActivity(intent);
        }

        select_material_page_state_next.setOnClickListener{
            if (!hasSelectedstate) {
                showIncompleteActionDialog()
            }else{
                val intent = Intent(this,SelectMaterialPage_WorkTime::class.java)
                startActivity(intent)
            }
        }

        radiogroup_state.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedValue_state = selectedRadioButton.tag.toString()
            val selectedValue_state_int = selectedValue_state.toInt()
            DataProvider.saveData("selectedValue_state", selectedValue_state_int)
            hasSelectedstate = true
        }

    }
    private fun showIncompleteActionDialog() {
        AlertDialog.Builder(this)
            .setTitle("提醒")
            .setMessage("請完成全部按鈕動作")
            .setPositiveButton("確定", null)
            .show()
    }
}