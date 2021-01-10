package com.example.newsreader.ui.ArticleData

import androidx.room.*

    @Dao
    interface ArticleDataDao {
    @Query("SELECT * FROM article_data")
    fun getAll():ArrayList<ArticleData>
    @Query("SELECT * FROM article_data WHERE category=:category")
    fun loadArticlesForGivenCategory(category:String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles:ArrayList<ArticleData>)
    @Delete
    fun deleteArticles()
}