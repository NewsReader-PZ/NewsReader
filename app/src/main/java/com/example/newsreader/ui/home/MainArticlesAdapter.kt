package com.example.newsreader.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.ArticleData
import com.example.newsreader.R

class MainArticlesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = listOf<ArticleData>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            0 -> ViewHolder0(LayoutInflater.from(parent.context)
                    .inflate(R.layout.standard_news_item_view, parent, false))
            1 -> ViewHolder1(LayoutInflater.from(parent.context)
                    .inflate(R.layout.bigger_news_item_view, parent, false))
            else -> ViewHolder0(LayoutInflater.from(parent.context)
                    .inflate(R.layout.standard_news_item_view, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int):Unit {
        val item = data[position]
        val res = holder.itemView.context.resources
        when(position){
            0->{
                val holder0:ViewHolder0 = holder as ViewHolder0
                holder0.title.text = item.text
                holder0.author.text = item.author
                holder0.articleImage.setImageResource(R.drawable.icon_main)
            }
            1->{
                val holder1:ViewHolder1 = holder as ViewHolder1
                holder1.title.text = item.text
                holder1.author.text = item.author
                holder1.articleImage.setImageResource(R.drawable.icon_main)
                holder1.subheading.text = item.subheading
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position==0) 1 else 0
        //return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder0(itemView: View):RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.standard_news_text_view_title)
        val author : TextView = itemView.findViewById(R.id.standard_news_text_view_author)
        val articleImage : ImageView = itemView.findViewById(R.id.standard_news_image_icon)
    }
    class ViewHolder1(itemView: View):RecyclerView.ViewHolder(itemView){
        val title : TextView  = itemView.findViewById<TextView>(R.id.bigger_news_item_title)
        val author : TextView = itemView.findViewById(R.id.bigger_news_item_author)
        val subheading : TextView = itemView.findViewById(R.id.bigger_news_item_subheading)
        val articleImage: ImageView = itemView.findViewById(R.id.bigger_news_item_image_view)

    }
}