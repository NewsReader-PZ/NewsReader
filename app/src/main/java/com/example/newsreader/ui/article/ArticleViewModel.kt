package com.example.newsreader.ui.article

import androidx.lifecycle.ViewModel
import com.example.newsreader.Repository

class ArticleViewModel(val articleId: String) : ViewModel() {
        init {
            Repository.setCurrentArticle(articleId)
        }
}