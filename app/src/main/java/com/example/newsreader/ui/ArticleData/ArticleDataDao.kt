package com.example.newsreader

import androidx.room.Dao
import androidx.room.Query

@Dao

interface ArticleDataDao {
@Query("SELECT * FROM article_data")
fun getAll():ArrayList<ArticleData>
}