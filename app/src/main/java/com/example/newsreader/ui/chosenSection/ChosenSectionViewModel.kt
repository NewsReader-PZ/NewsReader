package com.example.newsreader.ui.chosenSection

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreader.ArticleData
import com.example.newsreader.Repository

class ChosenSectionViewModel : ViewModel() {
    //private var _selectedArticleArray:MutableLiveData<ArticleData> = MutableLiveData()
    val selectedArticleArray:LiveData<ArrayList<ArticleData>>
    get() {
        return Repository.chosenArticlesArray
    }
    class myLinearLayoutManager(context: Context): LinearLayoutManager(context){
        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }
    }
}