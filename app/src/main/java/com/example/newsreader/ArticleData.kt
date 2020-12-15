package com.example.newsreader

import android.icu.text.CaseMap
import java.util.*
import kotlin.collections.ArrayList

data class ArticleData(var title: String = "Title",
                       var publishingDate:Date = Date(),
                       var updateDate:Date = Date(),
                       var imagesDescription :ArrayList<String>,
                       var imagesAuthors :ArrayList<String>,
                       var author:String = "Author",
                       var subheading:String,
                       var text:String = "texttexttext",
                       var images:ArrayList<String>){
//    constructor(empa: String) : this(title,publishingDate,updateDate,imagesDescription, imagesAuthors, author, text, images) {
//
//    }


}