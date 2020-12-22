package com.example.newsreader.ui.article

import androidx.lifecycle.ViewModel
import com.example.newsreader.ArticleData
import com.example.newsreader.ArticleFullData
import com.example.newsreader.Repository

class ArticleViewModel(val articleId: String) : ViewModel() {
        var article: ArticleFullData?
        init {
            Repository.setCurrentArticle(articleId)
            article = Repository.currentArticle.value
        }
}