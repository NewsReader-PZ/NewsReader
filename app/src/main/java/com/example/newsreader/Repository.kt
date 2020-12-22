package com.example.newsreader

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.core.FirestoreClient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.text.FieldPosition

object Repository {
    private const val TAG:String = "Repository"
    private val db = Firebase.firestore
    private var _articlesArray:MutableLiveData<ArrayList<ArticleData>> = MutableLiveData<ArrayList<ArticleData>>()
    val articlesArray:LiveData<ArrayList<ArticleData>>
    get() {
        return _articlesArray
    }
    private var _currentArticle:MutableLiveData<ArticleFullData> = MutableLiveData<ArticleFullData>()
    val currentArticle:LiveData<ArticleFullData>
        get() {
            return _currentArticle
        }
    init {
        _articlesArray.value = ArrayList()
        Log.i(TAG,"init")
    }
    fun setCurrentArticle(articleId:String){
        val currentArticle = "Current article"
        //var articlesArray:ArrayList<ArticleData> = ArrayList()
        Log.i(TAG,"$currentArticle articleId ${articleId}")
        Log.i(TAG,"$currentArticle FieldPath id ${FieldPath.documentId()}")
        val docRef = db.collection("Articles")
            .whereEqualTo(FieldPath.documentId(),articleId)
            .get()
            .addOnSuccessListener {  document ->
                if (document != null) {

                    Log.d(TAG, "$currentArticle documentSnapshot data size: ${document.documents.size}")
                    val dataSize = document.size()
                    //articlesArray = Array<ArticleData>(dataSize) { it -> ArticleData() }
                    //articlesArray = document.toObjects(MyArticlesArray::class.java)
                      //  }
                        _currentArticle.value = ArticleFullData(
                            title = document.documents[0].get("title") as String,
                            publishingDate = document.documents[0].get("publishingDate") as Timestamp,
                            updateDate = document.documents[0].get("updateDate") as Timestamp,
                            author = document.documents[0].get("author") as String,
                           subheading =  document.documents[0].get("subheading") as String,
                           id =  document.documents[0].id,
                        imagesAuthors = ArrayList(),
                        imagesDescription = ArrayList())
                Log.i(TAG, _currentArticle.value.toString())
                       // _articlesArray.value?.get(i)?.let { Log.i(TAG, it.toString()) }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    fun getClickedArticleId(position: Int): String?{
        Log.i(TAG, "getClickedArticleId: ${_articlesArray.value?.get(position)?.id}")
       return _articlesArray.value?.get(position)?.id
    }
    fun getArticlesArray():ArrayList<ArticleData>{
        return if(_articlesArray.value != null) _articlesArray.value!!
        else ArrayList<ArticleData>()
    }
    fun setArticlesForHomeView(){
        //var articlesArray:ArrayList<ArticleData> = ArrayList()
        val docRef = db.collection("Articles")
                .get()
                .addOnSuccessListener {  document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data size: ${document.documents.size}")
                        val dataSize = document.size()
                        //articlesArray = Array<ArticleData>(dataSize) { it -> ArticleData() }
                        //articlesArray = document.toObjects(MyArticlesArray::class.java)
                        for (i in 0 until dataSize){
                            val arrayContainsId = _articlesArray.value?.filter { it.id ==  document.documents[i].id}
                            if (arrayContainsId != null) {
                                if(arrayContainsId.isNotEmpty()) continue
                            }
                            val articleData = ArticleData(
                                document.documents[i].get("title") as String,
                                document.documents[i].get("author") as String,
                                document.documents[i].get("subheading") as String,
                                document.documents[i].id
                            )
                            _articlesArray.value?.add(articleData)
                            Log.i(TAG, articleData.toString())
                            _articlesArray.value?.get(i)?.let { Log.i(TAG, it.toString()) }
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        //Log.i(TAG,"Returning articlesArray (last element)${articlesArray[articlesArray.size-1].toString()}")
        //Log.i(TAG, "Returning array size: ${articlesArray.size}")
    }

}