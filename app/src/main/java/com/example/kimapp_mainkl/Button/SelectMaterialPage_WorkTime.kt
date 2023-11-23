package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

class SelectMaterialPage_WorkTime : AppCompatActivity() {
    private var hasSelectedworktime = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_material_page_work_time)

        val imageButton_selectMaterial_worktime_back = findViewById<ImageButton>(R.id.imageButton_selectMaterial_worktime_back)
        val select_material_page_worktime_next = findViewById<Button>(R.id.select_material_page_worktime_next)
        val radio_three_1 = findViewById<RadioButton>(R.id.radio_three_1)
        val radio_three_2 = findViewById<RadioButton>(R.id.radio_three_2)
        val radio_three_3 = findViewById<RadioButton>(R.id.radio_three_3)
        val radiogroup_worktime_less5s = findViewById<RadioGroup>(R.id.radiogroup_worktime_less5s)
        val radiogroup_worktime_more5s = findViewById<RadioGroup>(R.id.radiogroup_worktime_more5s)
        val radiogroup_worktime_more5min = findViewById<RadioGroup>(R.id.radiogroup_worktime_more5min)

        select_material_page_worktime_next.setBackgroundColor(Color.rgb(245,244,155))

        imageButton_selectMaterial_worktime_back.setOnClickListener{
            val intent = Intent(this,SelectMaterialPage_State::class.java);
            startActivity(intent);
        }

        select_material_page_worktime_next.setOnClickListener{
            if (!hasSelectedworktime) {
                showIncompleteActionDialog()
            }else{
                val intent = Intent(this,SelectPage::class.java)
                startActivity(intent)
                DataProvider.saveData("checkbutton_done",1)
            }
        }

        radio_three_1.setOnClickListener { v ->
            radiogroup_worktime_less5s.visibility = View.VISIBLE//<5s
            radiogroup_worktime_more5s.visibility = View.GONE//>5s
            radiogroup_worktime_more5min.visibility = View.GONE//>5分鐘
            hasSelectedworktime = false
        }

        radio_three_2.setOnClickListener { v ->
            radiogroup_worktime_less5s.visibility = View.GONE//<5s
            radiogroup_worktime_more5s.visibility = View.VISIBLE//>5s
            radiogroup_worktime_more5min.visibility = View.GONE//>5分鐘
            hasSelectedworktime = false
        }

        radio_three_3.setOnClickListener { v ->
            radiogroup_worktime_less5s.visibility = View.GONE//<5s
            radiogroup_worktime_more5s.visibility = View.GONE//>5s
            radiogroup_worktime_more5min.visibility = View.VISIBLE//>5分鐘
            hasSelectedworktime = false
        }

        radiogroup_worktime_less5s.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedValue_WorkTime = selectedRadioButton.tag.toString()
            val selectedValue_WorkTime_int = selectedValue_WorkTime.toInt()
            DataProvider.saveData("selectedValue_WorkTime", selectedValue_WorkTime_int)
            hasSelectedworktime = true
        }

        radiogroup_worktime_more5s.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedValue_WorkTime = selectedRadioButton.tag.toString()
            val selectedValue_WorkTime_int = selectedValue_WorkTime.toInt()
            DataProvider.saveData("selectedValue_WorkTime", selectedValue_WorkTime_int)
            hasSelectedworktime = true
        }

        radiogroup_worktime_more5min.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedValue_WorkTime = selectedRadioButton.tag.toString()
            val selectedValue_WorkTime_int = selectedValue_WorkTime.toInt()
            DataProvider.saveData("selectedValue_WorkTime", selectedValue_WorkTime_int)
            hasSelectedworktime = true
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