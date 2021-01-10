package com.example.newsreader.comments.CommentData

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newsreader.ui.ArticleData.ArticleData
import com.google.type.Date
@Entity(tableName = "comment", foreignKeys = [ForeignKey(entity=ArticleData::class,
                                                        parentColumns = ["id"],
                                                        childColumns = ["article_id"],
                                                        onDelete = ForeignKey.CASCADE)])
data class Comment (
        @PrimaryKey val id:String,
        @ColumnInfo (name = "user_name", typeAffinity = TEXT)val userName:String,
        @ColumnInfo (name = "text", typeAffinity = TEXT)val text:String,
        @ColumnInfo (name = "date")val date:java.util.Date,
        @ColumnInfo (name = "user_uid", typeAffinity = TEXT)val userUid:String,
        @ColumnInfo (name = "article_id", typeAffinity = TEXT)val articleId:String)