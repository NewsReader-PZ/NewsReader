package com.example.newsreader

import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

data class ArticleFullData(var title: String = "Title",
                           var publishingDate:Date = Timestamp(0),
                           var updateDate:Date = Timestamp(0),
                           var imagesDescription :ArrayList<String>,
                           var imagesAuthors :ArrayList<String>,
                           var author:String = "Author",
                           var subheading:String = "Subheading",
                           var text:String = "texttexttext",
                           var id:String,
                           var images:ArrayList<String> = ArrayList(1)) {
}