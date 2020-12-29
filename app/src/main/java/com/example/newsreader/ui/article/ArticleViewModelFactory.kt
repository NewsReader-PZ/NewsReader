package com.example.newsreader.ui.article

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleViewModelFactory (private val articleId:String, private val application: Application):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(articleId, application = application) as T
    }
}