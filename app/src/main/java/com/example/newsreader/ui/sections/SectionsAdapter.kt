package com.example.newsreader.ui.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver

import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.GlideApp
import com.example.newsreader.Repository
import com.example.newsreader.databinding.SectionsStandardItemBinding


class SectionsAdapter(val clickListener: SectionsListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), LifecycleObserver {
    private val TAG = "SectionsAdapter"
    var data: ArrayList<SectionData> = ArrayList()
        set(value){
            field = value
            this.notifyDataSetChanged()
        }
//    init {
//        Repository.setArticlesForHomeView()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(SectionsStandardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item  = data[position]
        val res = holder.itemView.context.resources
                val holder:ViewHolder = holder as ViewHolder
                holder.binding.section = item
                holder.binding.categoriesStandardItemTextView.text = item.sectionName
                holder.binding.clickListener = clickListener
                //holder0.articleImage.setImageResource(R.drawable.title_much_smaller)
                GlideApp.with(holder.itemView.context)
                        .load(item.resourcesId)
                        //.override(500, 250)
                        .into(holder.binding.categoriesStandardItemImageView)
        }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val binding: SectionsStandardItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }
    class SectionsListener(val clickListener: (category: String)->Unit){
        fun onClick(sectionData: SectionData?) = sectionData?.sectionName?.let { clickListener(it) }
    }

    }

