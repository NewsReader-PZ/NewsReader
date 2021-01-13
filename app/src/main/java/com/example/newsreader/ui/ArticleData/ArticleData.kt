package com.example.newsreader.ui.ArticleData

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.ArrayList
@Entity(tableName = "article_data")
data class ArticleData(@ColumnInfo(name="title") val title: String,
//                       var publishingDate:Date = Date(),
//                       var updateDate:Date = Date(),
//                       var imagesDescription :ArrayList<String>,
//                       var imagesAuthors :ArrayList<String>,
                       @ColumnInfo(name="author", typeAffinity = TEXT)val author:String,
                       @ColumnInfo(name="category", typeAffinity = TEXT)val category:String,
                       @ColumnInfo(name="subheading", typeAffinity = TEXT)val subheading:String,
//                       var text:String = "texttexttext",
                       @PrimaryKey @ColumnInfo(name = "id", typeAffinity = TEXT) val id:String,
                       @ColumnInfo(name="first_img_ref")val onlyFirstImage:StorageReference
){
//    constructor(empa: String) : this(title,publishingDate,updateDate,imagesDescription, imagesAuthors, author, text, images) {
//
//    }


}