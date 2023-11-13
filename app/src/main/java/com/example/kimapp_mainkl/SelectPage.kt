package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
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
        val button_nextEnding = findViewById<Button>(R.id.button_nextEnding)

        button_nextEnding.setBackgroundColor(Color.rgb(245,244,155))

        imageButton_backOperation.setOnClickListener{
            val intent = Intent(this,OperationPage::class.java)
            startActivity(intent);
        }

        button_nextshooting.setOnClickListener{
            val intent = Intent(this,VideoPage::class.java)
            startActivity(intent);
        }

        button_selectgender.setOnClickListener{
            val intent = Intent(this,SelectGenderPage::class.java)
            startActivity(intent);
        }

        button_selectmetarial.setOnClickListener{
            if(DataProvider.getData("Gender_Of_value")==0){
                val intent = Intent(this,SelectMaterialPage_carry_female::class.java)
                startActivity(intent);
            }else if (DataProvider.getData("Gender_Of_value")==1){
                val intent = Intent(this,SelectMaterialPage_carry_male::class.java)
                startActivity(intent);
            }
        }

        button_nextEnding.setOnClickListener{
            val intent = Intent(this,ResultPage::class.java)
            startActivity(intent);
        }

    }
}