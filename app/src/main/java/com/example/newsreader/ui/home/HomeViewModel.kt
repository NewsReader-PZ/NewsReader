package com.example.newsreader.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.newsreader.ArticleData
import com.example.newsreader.Repository
import com.example.newsreader.ui.article.Article

class HomeViewModel : ViewModel() {
    var data:ArrayList<ArticleData> = ArrayList()
        init{}
    //var data:ArrayList<ArticleData>  = Repository.getArticlesForHomeView()
//    var articlesList:MutableLiveData<Array<ArticleData>> = MutableLiveData()
//    val _articlesList :LiveData<Array<ArticleData>>
//        get(){
//            return articlesList.value
//        }
//    init {
//        articlesList.value = Repository.getArticlesForHomeView()
//    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}