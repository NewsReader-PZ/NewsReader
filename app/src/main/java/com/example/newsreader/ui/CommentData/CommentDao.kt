package com.example.newsreader.comments.CommentData

import androidx.room.*

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(vararg  comments: Comment)
    @Delete
    fun deleteComments()
    @Query("SELECT * FROM comment WHERE article_id = :articleId")
    fun loadCommentsForArticle(articleId:String): ArrayList<Comment>
}