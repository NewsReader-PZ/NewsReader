package com.example.newsreader

import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

object ArticleFullData: Observable() {
    var title: String = "Title"
    var publishingDate:com.google.firebase.Timestamp = com.google.firebase.Timestamp(
        Date()
    )
    var updateDate:com.google.firebase.Timestamp = com.google.firebase.Timestamp(
        Date()
    )
    lateinit var imagesDescription :ArrayList<String>
    lateinit var imagesAuthors :ArrayList<String>
    var author:String = "Author"
    var subheading:String = "Subheading"
    var text:String = "texttexttext"
    lateinit var id:String
    var images:ArrayList<String> = ArrayList(1)
}