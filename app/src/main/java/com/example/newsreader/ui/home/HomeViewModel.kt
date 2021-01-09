package com.example.newsreader.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.newsreader.ArticleData
import com.example.newsreader.Repository

class HomeViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    val articlesArray:LiveData<ArrayList<ArticleData>>
    get() {
        return Repository.articlesArray
    }
    //var data:ArrayList<ArticleData>  = Repository.getArticlesForHomeView()
//    var articlesList:MutableLiveData<Array<ArticleData>> = MutableLiveData()
//    val _articlesList :LiveData<Array<ArticleData>>
//        get(){
//            return articlesList.value
//        }


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}