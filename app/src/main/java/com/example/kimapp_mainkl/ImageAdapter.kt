package com.example.kimapp_mainkl

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class ImageAdapter(private val context: Context,private val imageList: List<ImageItem>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>(){

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(item: ImageItem) {
            imageView.scaleType = ImageView.ScaleType.FIT_START // 设置scaleType
            imageView.setImageResource(item.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_image_recycler_adapter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = imageList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class VerticalSpaceItemDecoration(private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: android.view.View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.top = 0
            outRect.bottom = 0
        }
    }

}
