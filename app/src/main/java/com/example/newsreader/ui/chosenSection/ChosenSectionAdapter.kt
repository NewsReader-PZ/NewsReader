package com.example.newsreader.ui.chosenSection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.ArticleData
import com.example.newsreader.GlideApp
import com.example.newsreader.Repository
import com.example.newsreader.databinding.BiggerChosenCategoryNewsItemViewBinding
import com.example.newsreader.databinding.BiggerNewsItemViewBinding

class ChosenSectionAdapter(private val clickListener: ChosenSectionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), LifecycleObserver {
        private val TAG = "ChosenSectionAdapter"
        var data: ArrayList<ArticleData> = ArrayList()
            set(value){
                field = value
                this.notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(BiggerChosenCategoryNewsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item  = data[position]
            val holder:ViewHolder = holder as ViewHolder
            holder.binding.article = item
            holder.binding.biggerChosenCategoryNewsItemAuthor.text = item.author
            holder.binding.biggerChosenCategoryNewsItemSubheading.text = item.subheading
            holder.binding.biggerChosenCategoryNewsItemTitle.text = item.title
            holder.binding.clickListener = clickListener
            //holder0.articleImage.setImageResource(R.drawable.title_much_smaller)
            GlideApp.with(holder.itemView.context)
                    .load(item.onlyFirstImage)
                    //.override(500, 250)
                    .into(holder.binding.biggerChosenCategoryNewsItemImageView)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        class ViewHolder(val binding: BiggerChosenCategoryNewsItemViewBinding): RecyclerView.ViewHolder(binding.root){

        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
        }
        class ChosenSectionListener(val clickListener: (articleId: String)->Unit){
            fun onClick(articleData: ArticleData?) = articleData?.id?.let { clickListener(it) }
        }

    }