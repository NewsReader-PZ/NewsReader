package com.example.newsreader.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.ArticleData
import com.example.newsreader.R

class MainArticlesAdapter : RecyclerView.Adapter<MainArticlesAdapter.ViewHolder>() {
    var data = listOf<ArticleData>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.standard_news_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.title.text = "Tytuł RecyclerView"
        holder.author.text = "Autor artykułu RecyclerView"
        holder.articleImage.setImageResource(R.drawable.icon_main)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.standard_news_text_view_title)
        val author : TextView = itemView.findViewById(R.id.standard_news_text_view_author)
        val articleImage : ImageView = itemView.findViewById(R.id.standard_news_image_icon)

    }
}