package com.example.newsreader

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsreader.ui.articleData.ArticleSmaller
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Repository:LifecycleObserver {
    private const val TAG:String = "Repository"
    private val db = Firebase.firestore
    val storageReference:StorageReference = FirebaseStorage.getInstance().reference
    private var _articlesArray:MutableLiveData<ArrayList<ArticleSmaller>> = MutableLiveData<ArrayList<ArticleSmaller>>()
    val articlesArray:LiveData<ArrayList<ArticleSmaller>>
        get() {
            return _articlesArray
        }
    private var _chosenArticlesArray:MutableLiveData<ArrayList<ArticleSmaller>> = MutableLiveData<ArrayList<ArticleSmaller>>()
    val chosenArticlesArray:LiveData<ArrayList<ArticleSmaller>>
        get() {
            return _chosenArticlesArray
        }
    init {
        _articlesArray.value = ArrayList()
        _chosenArticlesArray.value = ArrayList()
        Log.i(TAG,"init")
    }
    fun setCurrentArticle(articleId:String){
        val currentArticle = "Current article"
        //var articlesArray:ArrayList<ArticleSmaller> = ArrayList()
        Log.i(TAG,"$currentArticle articleId $articleId")
        Log.i(TAG,"$currentArticle FieldPath id ${FieldPath.documentId()}")
        db.collection("Articles")
            .whereEqualTo(FieldPath.documentId(),articleId)
            .get()
            .addOnSuccessListener {  document ->
                if (document != null) {

                    Log.d(TAG, "$currentArticle documentSnapshot data size: ${document.documents.size}")
                    //val dataSize = document.size()
                    //articlesArray = Array<ArticleSmaller>(dataSize) { it -> ArticleSmaller() }
                    //articlesArray = document.toObjects(MyArticlesArray::class.java)
                      //  }
//                    val storageReference:StorageReference = FirebaseStorage.getInstance(articleId).reference
//                    storageReference.getBytes(2000000).result
                            CurrentArticle.apply {
                                _author.value = document.documents[0].get("author") as String
                                _title.value = document.documents[0].get("title") as String
                                _publishingDate.postValue(document.documents[0].get("publishingDate") as com.google.firebase.Timestamp)
                                _publishingDate.value = _publishingDate.value

                                _subheading.value =  document.documents[0].get("subheading") as String
                                id =  document.documents[0].id
                                _images.value?.add(storageReference.child("Articles/${document.documents[0].id}/0.jpg"))
                                _images.value = _images.value
                                //var i = 0
//                                while(storageReference.child("Articles/${document.documents[i].id}/$i.jpg")){
//                                    _images.value?.add(storageReference.child("Articles/${document.documents[i].id}/$i.jpg"))
//                                    i++
//                                }
                                _authorImage.value = storageReference.child("Authors/${author.value.toString().toLowerCase(Locale.ROOT)}.jpg")
                                _imagesAuthors.value = document.documents[0].get("imageAuthors") as ArrayList<String>
                                _imagesDescription.value = document.documents[0].get("imageDescriptions") as ArrayList<String>
                                _text.value = document.documents[0].get("text") as String
                                _updateDate.value = document.documents[0].get("updateDate") as com.google.firebase.Timestamp
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
    fun setArticlesForHomeView(){
        //var articlesArray:ArrayList<ArticleSmaller> = ArrayList()
        db.collection("Articles")
            .orderBy("publishingDate",Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {  document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data size: ${document.documents.size}")
                        var get = 5
                        for (i in 0 until get){
                            val arrayContainsId = _articlesArray.value?.filter { it.id ==  document.documents[i].id}
                            if (arrayContainsId != null) {
                                if(arrayContainsId.isNotEmpty()) {
                                    continue
                                }
                            }
                            val articleData = ArticleSmaller(
                                document.documents[i].get("title") as String,
                                document.documents[i].get("author") as String,
                                document.documents[i].get("category") as String,
                                document.documents[i].get("subheading") as String,
                                document.documents[i].id,
                                storageReference.child("Articles/${document.documents[i].id}/0.jpg")
                            )
                            _articlesArray.value?.add(articleData)
                            Log.i(TAG, articleData.toString())
                            _articlesArray.value?.get(i)?.let { Log.i(TAG, "From articlesArray $it") }
                        }
                        setArticlesForChosenSectionForHomeView("europe",2)
                        setArticlesForChosenSectionForHomeView("opinion",3)
                        _articlesArray.value = _articlesArray.value
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
    fun setArticlesForChosenSection(category:String, amount: Int){
        //var articlesArray:ArrayList<ArticleSmaller> = ArrayList()
        db.collection("Articles")
                .whereEqualTo("category",category)
                .orderBy("publishingDate",Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener  {  document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data size: ${document.documents.size}")
                        val dataSize = document.size()
                        val numberOfArticles = when{
                            dataSize < amount -> dataSize
                            else -> amount
                        }
                        _chosenArticlesArray.value?.clear()
                        for (i in 0 until numberOfArticles){
                            val arrayContainsId = _chosenArticlesArray.value?.filter { it.id ==  document.documents[i].id}
                            if (arrayContainsId != null) {
                                if(arrayContainsId.isNotEmpty()) continue
                            }
                            val articleData = ArticleSmaller(
                                    document.documents[i].get("title") as String,
                                    document.documents[i].get("author") as String,
                                    document.documents[i].get("subheading") as String,
                                    document.documents[i].get("category") as String,
                                    document.documents[i].id,
                                    storageReference.child("Articles/${document.documents[i].id}/0.jpg")
                            )
                            _chosenArticlesArray.value?.add(articleData)
                            Log.i(TAG, articleData.toString())
                            _chosenArticlesArray.value?.get(i)?.let { Log.i(TAG, "From chosenArticlesArray $it") }
                        }
                        _chosenArticlesArray.value = _chosenArticlesArray.value
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        //Log.i(TAG,"Returning articlesArray (last element)${articlesArray[articlesArray.size-1].toString()}")
        //Log.i(TAG, "Returning chosen articleArray size: ${_chosenArticlesArray.value?.size}")
    }
    private fun setArticlesForChosenSectionForHomeView(category:String, amount: Int){
        //var articlesArray:ArrayList<ArticleSmaller> = ArrayList()
        db.collection("Articles")
            .whereEqualTo("category",category)
            .orderBy("updateDate",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener  {  document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data size: ${document.documents.size}")
                    val dataSize = document.size()
                    val numberOfArticles = when{
                        dataSize < amount -> dataSize
                        else -> amount
                    }
                    for (i in 0 until numberOfArticles){
                        val arrayContainsId = _articlesArray.value?.filter { it.id ==  document.documents[i].id}
                        if (arrayContainsId != null) {
                            if(arrayContainsId.isNotEmpty()) continue
                        }
                        val articleData = ArticleSmaller(
                            document.documents[i].get("title") as String,
                            document.documents[i].get("author") as String,
                            document.documents[i].get("subheading") as String,
                                document.documents[i].get("category") as String,
                            document.documents[i].id,
                            storageReference.child("Articles/${document.documents[i].id}/0.jpg")
                        )
                        _articlesArray.value?.add(articleData)
                        Log.i(TAG, articleData.toString())
                        _articlesArray.value?.get(i)?.let { Log.i(TAG, "From chosenArticlesArray $it") }
                    }
                    _articlesArray.value = _articlesArray.value
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        //Log.i(TAG,"Returning articlesArray (last element)${articlesArray[articlesArray.size-1].toString()}")
        //Log.i(TAG, "Returning chosen articleArray size: ${_chosenArticlesArray.value?.size}")
    }
    object CurrentArticle {
        private val stf:SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy | HH:mm")
        var _title: MutableLiveData<String> = MutableLiveData("")
        val title: LiveData<String>
        get() {
            return _title
        }
        var _publishingDate:MutableLiveData<com.google.firebase.Timestamp> = MutableLiveData(com.google.firebase.Timestamp(Date()))
        val publishingDate:MediatorLiveData<String> = MediatorLiveData();
        var _updateDate:MutableLiveData<com.google.firebase.Timestamp> = MutableLiveData (com.google.firebase.Timestamp(Date()))
        val updateDate:MediatorLiveData<String> = MediatorLiveData()
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
        init {
            publishingDate.addSource(_publishingDate) {
                publishingDate.value = stf.format(it.toDate())
            }
            updateDate.addSource(_updateDate){
                if(it == _publishingDate.value) updateDate.value = ""
                else updateDate.value = "${stf.format(it.toDate())}"
            }
        }
        fun clearAllFields(){
            _title.value = ""
            _authorImage.value
            _subheading.value = ""
            _text.value = ""
            _images = MutableLiveData()
            _imagesDescription = MutableLiveData()
            _updateDate.value = com.google.firebase.Timestamp(Date())
            _publishingDate.value = com.google.firebase.Timestamp(Date())
        }
    }
}