package com.example.newsreader.ui.articleData

import com.google.firebase.storage.StorageReference

//@Entity(tableName = "article_data")
data class ArticleSmaller(val title: String,
//                       var publishingDate:Date = Date(),
//                       var updateDate:Date = Date(),
//                       var imagesDescription :ArrayList<String>,
//                       var imagesAuthors :ArrayList<String>,
                          val author:String,
                          val category:String,
                          val subheading:String,
//                       var text:String = "texttexttext",
                          val id:String,
                          val onlyFirstImage:StorageReference) {

}
//@Entity(tableName = "article_data")
//open data class ArticleSmaller(@ColumnInfo(name="title") val title: String,
////                       var publishingDate:Date = Date(),
////                       var updateDate:Date = Date(),
////                       var imagesDescription :ArrayList<String>,
////                       var imagesAuthors :ArrayList<String>,
//                            @ColumnInfo(name="author", typeAffinity = TEXT)val author:String,
//                            @ColumnInfo(name="category", typeAffinity = TEXT)val category:String,
//                            @ColumnInfo(name="subheading", typeAffinity = TEXT)val subheading:String,
////                       var text:String = "texttexttext",
//                            @PrimaryKey @ColumnInfo(name = "id", typeAffinity = TEXT) val id:String,
//                            val onlyFirstImage:StorageReference) {
//    //    constructor(empa: String) : this(title,publishingDate,updateDate,imagesDescription, imagesAuthors, author, text, images) {
////
////    }
//
//}
