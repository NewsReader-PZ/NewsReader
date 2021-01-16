package com.example.newsreader.ui.chosenSection

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreader.ui.articleData.ArticleSmaller
import com.example.newsreader.Repository

class ChosenSectionViewModel : ViewModel() {
    //private var _selectedArticleArray:MutableLiveData<ArticleSmaller> = MutableLiveData()
    val selectedArticleArray:LiveData<ArrayList<ArticleSmaller>>
    get() {
        return Repository.chosenArticlesArray
    }
    class myLinearLayoutManager(context: Context): LinearLayoutManager(context){
        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }
    }

    override fun onCleared() {
        super.onCleared()
        selectedArticleArray.value?.clear()
    }
}