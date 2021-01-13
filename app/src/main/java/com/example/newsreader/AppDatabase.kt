package com.example.newsreader

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsreader.ui.CommentData.Comment
import com.example.newsreader.ui.ArticleData.ArticleData

@Database(entities = [Comment::class, ArticleData::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleData
}