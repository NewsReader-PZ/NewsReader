package com.example.newsreader.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.newsreader.ui.articleData.ArticleSmaller
import com.example.newsreader.Repository

class HomeViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    val articlesArray:LiveData<ArrayList<ArticleSmaller>>
    get() {
        return Repository.articlesArray
    }
    //var data:ArrayList<ArticleSmaller>  = Repository.getArticlesForHomeView()
//    var articlesList:MutableLiveData<Array<ArticleSmaller>> = MutableLiveData()
//    val _articlesList :LiveData<Array<ArticleSmaller>>
//        get(){
//            return articlesList.value
//        }


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}