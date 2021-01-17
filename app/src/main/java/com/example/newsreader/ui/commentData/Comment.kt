package com.example.newsreader.ui.commentData

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "comment", foreignKeys = [ForeignKey(entity=ArticleSmaller::class,
//                                                        parentColumns = ["id"],
//                                                        childColumns = ["article_id"],
//                                                        onDelete = ForeignKey.CASCADE)])
@Entity(tableName = "comment")
data class Comment (
        @PrimaryKey val id:String,
        @ColumnInfo (name = "user_name", typeAffinity = TEXT)val userName:String,
        @ColumnInfo (name = "text", typeAffinity = TEXT)val text:String,
        @ColumnInfo (name = "date", typeAffinity = TEXT )val date:java.util.Date,
        @ColumnInfo (name = "user_uid", typeAffinity = TEXT)val userUid:String,
        @ColumnInfo (name = "article_id", typeAffinity = TEXT)val articleId:String)