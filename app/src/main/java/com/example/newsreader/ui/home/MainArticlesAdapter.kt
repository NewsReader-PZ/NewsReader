package com.example.newsreader.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import com.example.newsreader.ArticleData
import com.example.newsreader.R
import com.example.newsreader.Repository
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream

class MainArticlesAdapter(homeViewModel: HomeViewModel,val onItemListener: OnItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "MainArticlesAdapter"
    var data: ArrayList<ArticleData> = ArrayList()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    init {
        Repository.setArticlesForHomeView()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            0 -> ViewHolder0(LayoutInflater.from(parent.context)
                    .inflate(R.layout.bigger_news_item_view, parent, false),
                 onItemListener)
            else -> ViewHolderElse(LayoutInflater.from(parent.context)
                    .inflate(R.layout.standard_news_item_view, parent, false),
                    onItemListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int):Unit {
        val item = data[position]
        val res = holder.itemView.context.resources
        when(position){
            0->{
                val holder0:ViewHolder0 = holder as ViewHolder0
                holder0.title.text = item.title
                holder0.author.text = item.author
                //holder0.articleImage.setImageResource(R.drawable.title_much_smaller)
                Glide.with(holder0.itemView.context)
                    .load(item.onlyFirstImage)
                    //.override(500, 250)
                    .into(holder0.articleImage);
                holder0.subheading.text = item.subheading
            }
            else->{
                val holderE:ViewHolderElse = holder as ViewHolderElse
                holderE.title.text = item.title
                holderE.author.text = item.author
                //holderE.articleImage.setImageResource(R.drawable.title_much_smaller)
                Glide.with(holder.itemView.context)
                    .load(item.onlyFirstImage)
                   // .override(150, 100)
                    .into(holder.articleImage);
                //holderE.subheading.text = item.subheading
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position==0) 0 else 1
        //return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        Log.i(TAG, "Data size: ${data.size}")
        return data.size
    }

    class ViewHolder0(itemView: View, val onItemListener: OnItemListener):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener (this)
        }
        val title : TextView = itemView.findViewById(R.id.bigger_news_item_title)
        val author : TextView = itemView.findViewById(R.id.bigger_news_item_author)
        val articleImage : ImageView = itemView.findViewById(R.id.bigger_news_item_image_view)
        val subheading : TextView = itemView.findViewById(R.id.bigger_news_item_subheading)
        override fun onClick(v: View?) {
            onItemListener.onItemCLick(adapterPosition)
        }
    }
    class ViewHolderElse(itemView: View, val onItemListener: OnItemListener):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener (this)
        }
        val title : TextView  = itemView.findViewById(R.id.standard_news_text_view_title)
        val author : TextView = itemView.findViewById(R.id.standard_news_text_view_author)
        val articleImage: ImageView = itemView.findViewById(R.id.standard_news_image_icon)
        override fun onClick(v: View?) {
            onItemListener.onItemCLick(adapterPosition)
        }

    }
    public interface OnItemListener{
        fun onItemCLick(position: Int)
    }
}