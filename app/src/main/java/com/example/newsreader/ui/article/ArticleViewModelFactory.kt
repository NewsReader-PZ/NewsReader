package com.example.newsreader.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleViewModelFactory (private val articleId:String):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(articleId) as T
    }
}