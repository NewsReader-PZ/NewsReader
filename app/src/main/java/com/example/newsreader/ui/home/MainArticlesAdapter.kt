package com.example.newsreader.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.ui.articleData.ArticleSmaller
import com.example.newsreader.GlideApp
import com.example.newsreader.Repository
import com.example.newsreader.databinding.BiggerNewsItemViewBinding
import com.example.newsreader.databinding.HeaderItemViewBinding
import com.example.newsreader.databinding.StandardNewsItemViewBinding


private const val ITEM_VIEW_TYPE_HEADER = 2
private const val ITEM_VIEW_TYPE_BIGGER_NEWS = 0
private const val ITEM_VIEW_TYPE_STANDARD_NEWS = 1


class MainArticlesAdapter(homeViewModel: HomeViewModel, private val clickListener: MainArticlesListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), LifecycleObserver {
    private val TAG = "MainArticlesAdapter"
    var headersArrayList:ArrayList<String> = ArrayList()
    var data: ArrayList<ArticleSmaller> = ArrayList()
        set(value){
           val result:DiffUtil.DiffResult = DiffUtil.calculateDiff(MyDiffCallback(this.data,value))
            field = value
            notifyDataSetChanged()
            result.dispatchUpdatesTo(this)
        }
    init {
        Repository.setArticlesForHomeView()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
//            0 -> ViewHolder(LayoutInflater.from(parent.context)
//                    .inflate(R.layout.bigger_news_item_view, parent, false),
//                 onItemListener
//            )
            ITEM_VIEW_TYPE_BIGGER_NEWS->ViewHolder0(BiggerNewsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEM_VIEW_TYPE_HEADER->ViewHolderHeader(HeaderItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> ViewHolderElse(StandardNewsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item:ArticleSmaller?  = when {
            position < 3 -> data[position]
            position in 4..5 -> data[position-1]
            else -> data[position-2]
        }

        val res = holder.itemView.context.resources
        when(position){
            0->{
                val holder0:ViewHolder0 = holder as ViewHolder0
                holder0.binding.article = item
                holder0.binding.biggerNewsItemTitle.text = item?.title
                holder0.binding.biggerNewsItemAuthor.text = item?.author
                holder0.binding.clickListener = clickListener
                //holder0.articleImage.setImageResource(R.drawable.title_much_smaller)
                GlideApp.with(holder0.itemView.context)
                    .load(item?.onlyFirstImage)
                    //.override(500, 250)
                    .into(holder0.binding.biggerNewsItemImageView)
                holder0.binding.biggerNewsItemSubheading.text = item?.subheading
            }
            5-> {
                val holderHeader:ViewHolderHeader = holder as ViewHolderHeader
                holderHeader.binding.headerText.text = headersArrayList[0]
            }
            8-> {
            val holderHeader:ViewHolderHeader = holder as ViewHolderHeader
            holderHeader.binding.headerText.text = headersArrayList[1]
        }

            else->{
                val holderE:ViewHolderElse = holder as ViewHolderElse
                holderE.binding.article = item
                holderE.binding.standardNewsTextViewTitle.text = item?.title
                holderE.binding.standardNewsTextViewAuthor.text = item?.author
                holderE.binding.clickListener = clickListener
                //holderE.articleImage.setImageResource(R.drawable.title_much_smaller)
                GlideApp.with(holder.itemView.context)
                    .load(item?.onlyFirstImage)
                   // .override(150, 100)
                    .into(holderE.binding.standardNewsImageIcon)
                //holderE.subheading.text = item.subheading
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return  when(position){
             0 -> ITEM_VIEW_TYPE_BIGGER_NEWS
            5,8 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_STANDARD_NEWS
        }
        //return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        Log.i(TAG, "Data size: ${data.size}")
        return if(data.size < 5) data.size
        else if(data.size in 6..7) data.size +1
        else data.size+2
    }

    class ViewHolder0(val binding:BiggerNewsItemViewBinding):RecyclerView.ViewHolder(binding.root){

    }
    class ViewHolderHeader(val binding:HeaderItemViewBinding):RecyclerView.ViewHolder(binding.root){
        var headerText = ""
    }
    class ViewHolderElse(val binding: StandardNewsItemViewBinding):RecyclerView.ViewHolder(binding.root){


    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }
    class MainArticlesListener(val clickListener: (articleId: String)->Unit){
        fun onClick(articleSmaller: ArticleSmaller?) = articleSmaller?.id?.let { clickListener(it) }

    }

    class MyDiffCallback(private val oldArticleList:ArrayList<ArticleSmaller>, private val newArticleList:ArrayList<ArticleSmaller>): DiffUtil.Callback(){

        override fun getOldListSize(): Int {
           return oldArticleList.size
        }

        override fun getNewListSize(): Int {
            return newArticleList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArticleList[oldItemPosition].id == newArticleList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArticleList[oldItemPosition] == newArticleList[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}