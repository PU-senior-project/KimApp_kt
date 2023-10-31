package com.example.kimapp_mainkl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class SelectPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_page)
        val imageButton_backOperation = findViewById<ImageButton>(R.id.imageButton_backOperation)
        val button_nextshooting = findViewById<Button>(R.id.button_nextshooting)
        val button_selectgender = findViewById<Button>(R.id.button_selectgender)
        val button_selectmetarial = findViewById<Button>(R.id.button_selectmetarial)

        imageButton_backOperation.setOnClickListener{
            val intent = Intent(this,OperationPage::class.java)
            startActivity(intent);
        }

        button_nextshooting.setOnClickListener{
            val intent = Intent(this,OperationPage::class.java)
            startActivity(intent);
        }

        button_selectgender.setOnClickListener{
            val intent = Intent(this,SelectGenderPage::class.java)
            startActivity(intent);
        }

        if(DataProvider.getData("Gender_Of_gender")==1){
            button_selectmetarial.setOnClickListener{
                val intent = Intent(this,SelectMaterialPage_carry_male::class.java)
                startActivity(intent);
            }
        }else if(DataProvider.getData("Gender_Of_gender")==0){
            button_selectmetarial.setOnClickListener{
                val intent = Intent(this,SelectMaterialPage_carry_female::class.java)
                startActivity(intent);
            }
        }

    }
}