package com.example.kimapp_mainkl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

data class ImageItem(val imageUrl: Int)

class OperationPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_page)

        val imageButton_backfirst=findViewById<ImageButton>(R.id.imageButton_backfirst)
        val button_nextSelect=findViewById<Button>(R.id.button_nextSelect)
        val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        button_nextSelect.setBackgroundColor(Color.rgb(245,244,155))

        recycler_view.layoutManager = layoutManager

        val itemDecoration = cls_ImageAdapter.VerticalSpaceItemDecoration(0)
        recycler_view.addItemDecoration(itemDecoration)

        val imageList = listOf(
            ImageItem(R.drawable.done)
        )

        val adapter = cls_ImageAdapter(this,imageList)
        recycler_view.adapter = adapter

        imageButton_backfirst.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java);
            startActivity(intent);
        }

        button_nextSelect.setOnClickListener{
            val intent = Intent(this,SelectPage::class.java);
            startActivity(intent);
        }
    }
}

