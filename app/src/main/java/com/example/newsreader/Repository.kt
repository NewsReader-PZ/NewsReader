package com.example.newsreader

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.core.FirestoreClient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.sql.Timestamp
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

object Repository:LifecycleObserver {
    private const val TAG:String = "Repository"
    private val db = Firebase.firestore
    var _articlesArray:MutableLiveData<ArrayList<ArticleData>> = MutableLiveData<ArrayList<ArticleData>>()
    val storageReference:StorageReference = FirebaseStorage.getInstance().reference
    val articlesArray:LiveData<ArrayList<ArticleData>>
    get() {
        return _articlesArray
    }
//    private var _currentArticle:MutableLiveData<CurrentArticle> = MutableLiveData<CurrentArticle>()
//    val currentArticle:LiveData<CurrentArticle>
//        get() {
//            return _currentArticle
//        }
    init {
        _articlesArray.value = ArrayList()
        Log.i(TAG,"init")
    }
    fun setCurrentArticle(articleId:String){
        val currentArticle = "Current article"
        //var articlesArray:ArrayList<ArticleData> = ArrayList()
        Log.i(TAG,"$currentArticle articleId $articleId")
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
//                    val storageReference:StorageReference = FirebaseStorage.getInstance(articleId).reference
//                    storageReference.getBytes(2000000).result
                            CurrentArticle.apply {
                                _author.value = document.documents[0].get("author") as String
                                _title.value = document.documents[0].get("title") as String
                                _publishingDate.value = document.documents[0].get("publishingDate") as com.google.firebase.Timestamp
                                _updateDate.value = document.documents[0].get("updateDate") as com.google.firebase.Timestamp
                                _subheading.value =  document.documents[0].get("subheading") as String
                                id =  document.documents[0].id
                                _images.value?.add(storageReference.child("Articles/${document.documents[0].id}/0.jpg"))
                                var i = 0
//                                while(storageReference.child("Articles/${document.documents[i].id}/$i.jpg")){
//                                    _images.value?.add(storageReference.child("Articles/${document.documents[i].id}/$i.jpg"))
//                                    i++
//                                }
                                _authorImage.value = storageReference.child("Authors/${author.value.toString().toLowerCase(Locale.ROOT)}.jpg")
                                _imagesAuthors.value = document.documents[0].get("imageAuthors") as ArrayList<String>
                                _imagesDescription.value = document.documents[0].get("imageDescriptions") as ArrayList<String>
                                _text.value = document.documents[0].get("text") as String
                            }

                Log.i(TAG, CurrentArticle.toString())
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
                                document.documents[i].id,
                                storageReference.child("Articles/${document.documents[i].id}/0.jpg")
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
    object CurrentArticle {
        private val stf:SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy | HH:mm")
        var _title: MutableLiveData<String> = MutableLiveData("")
        val title: LiveData<String>
        get() {
            return _title
        }
        var _publishingDate:MutableLiveData<com.google.firebase.Timestamp>  = MutableLiveData(com.google.firebase.Timestamp(Date()))
        val publishingDate:LiveData<String>
        get() {
            val date = stf.format(_publishingDate.value?.toDate())
            return MutableLiveData(date)
        }
        var _updateDate:MutableLiveData<com.google.firebase.Timestamp> = MutableLiveData (com.google.firebase.Timestamp(Date()))
        set(value) {
            field.value = value.value
            field = value
        }
        val updateDate:LiveData<String>
        get() {
            if(_updateDate.value == _publishingDate.value) return MutableLiveData("")
            val date = stf.format(_updateDate.value?.toDate())
            return MutableLiveData(date)
        }
        var _imagesDescription :MutableLiveData<ArrayList<String>> = MutableLiveData()
        val imagesDescription:LiveData<ArrayList<String>>
        get() {
            return _imagesDescription
        }
        var _imagesAuthors :MutableLiveData<ArrayList<String>> = MutableLiveData()
        val imagesAuthors:LiveData<ArrayList<String>>
            get() {
                return _imagesAuthors
            }
        var _author:MutableLiveData<String> = MutableLiveData("")
        val author:LiveData<String>
        get(){
            return _author
        }
        var _authorImage:MutableLiveData<StorageReference> = MutableLiveData()
        val authorImage:LiveData<StorageReference>
            get(){
                return _authorImage
            }
        var _subheading:MutableLiveData<String> = MutableLiveData("")
        set(value) {
            field.value = value.value
            field = value
        }
        val subheading:LiveData<String>
        get() {
            return _subheading
        }
        var _text:MutableLiveData<String> = MutableLiveData("")
        val text:LiveData<String>
        get() {
            return _text
        }
        lateinit var id:String
        var _images:MutableLiveData<ArrayList<StorageReference>> = MutableLiveData(ArrayList(1))
        val images:LiveData<ArrayList<StorageReference>>
        get() {
            return _images
        }
        override fun toString(): String {
            return "{$title;$author;$subheading;$text}"
        }
    }
}