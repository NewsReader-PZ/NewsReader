package com.example.newsreader

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsreader.ui.CommentData.Comment
import java.util.*

//@Database(entities = [Comment::class, ArticleSmaller::class], version = 1)
@Database(entities = [Comment::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    //abstract fun articleDao(): ArticleSmaller

}
class Converters{
    @TypeConverter
    fun convertDateToString(date: Date?):String{
        if (date== null) return ""
        return date.toString()
    }
    @TypeConverter
    fun convertStringToDate(string: String):Date{
        if (string == "") return Date()
        return Date(string)
    }
}
