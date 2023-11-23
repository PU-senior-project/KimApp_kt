package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import org.w3c.dom.Text

class SelectPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_page)

        val imageButton_backOperation = findViewById<ImageButton>(R.id.imageButton_backOperation)
        val button_nextshooting = findViewById<Button>(R.id.button_nextshooting)
        val button_selectgender = findViewById<Button>(R.id.button_selectgender)
        val button_selectmetarial = findViewById<Button>(R.id.button_selectmetarial)
        val button_nextEnding = findViewById<Button>(R.id.button_nextEnding)

        val number_camera = findViewById<TextView>(R.id.number_camera)
        val number_gender = findViewById<TextView>(R.id.number_gender)
        val number_button = findViewById<TextView>(R.id.number_button)


        button_nextEnding.setBackgroundColor(Color.rgb(245,244,155))


        if(DataProvider.getData("checkcamera_done")==1) number_camera.text="(1/1)"
        else number_camera.text="(0/1)"
        if(DataProvider.getData("checkgender_done")==1) number_gender.text="(1/1)"
        else number_gender.text="(0/1)"
        if(DataProvider.getData("checkbutton_done")==1)number_button.text="(1/1)"
        else number_button.text="(0/1)"


        imageButton_backOperation.setOnClickListener{  //回到上一頁
            val intent = Intent(this,OperationPage::class.java)
            startActivity(intent);
        }

        button_nextshooting.setOnClickListener{  //跳入攝影頁面
            val intent = Intent(this,VideoPage::class.java)
            startActivity(intent);
        }

        button_selectgender.setOnClickListener{  //跳入選擇性別
            val intent = Intent(this,SelectGenderPage::class.java)
            startActivity(intent);
        }

        button_selectmetarial.setOnClickListener{
            if (DataProvider.getData("checkgender_done" )!= 1){ //如無點選性別確認跳出dialog(請先選擇性別)
                    showIncompleteActionDialog_gendertobutton()
            }else{
                if(DataProvider.getData("Gender_Of_value")==0){ //判斷是女性
                    val intent = Intent(this,SelectMaterialPage_carry_female::class.java)
                    startActivity(intent);
                }else if (DataProvider.getData("Gender_Of_value")==1){  //判斷是男性
                    val intent = Intent(this,SelectMaterialPage_carry_male::class.java)
                    startActivity(intent);
                }
            }
        }

        button_nextEnding.setOnClickListener{
            if (DataProvider.getData("checkcamera_done") != 1 ||
                DataProvider.getData("checkgender_done") != 1 ||
                DataProvider.getData("checkbutton_done") != 1) {

                showIncompleteActionDialog()

            } else {
                val intent = Intent(this, ResultPage::class.java)
                startActivity(intent)
            }
        }
    }
    private fun showIncompleteActionDialog() {
        AlertDialog.Builder(this)
            .setTitle("提醒")
            .setMessage("請完成全部按鈕動作")
            .setPositiveButton("確定", null)
            .show()
    }

    private fun showIncompleteActionDialog_gendertobutton() {
        AlertDialog.Builder(this)
            .setTitle("提醒")
            .setMessage("請完成選擇性別動作")
            .setPositiveButton("確定", null)
            .show()
    }

}