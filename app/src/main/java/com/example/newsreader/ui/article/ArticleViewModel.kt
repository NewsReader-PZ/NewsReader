package com.example.newsreader.ui.article

import android.app.Application
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.newsreader.GlideApp
import com.example.newsreader.Repository
import com.google.firebase.storage.StorageReference
import java.lang.IndexOutOfBoundsException
import kotlin.coroutines.coroutineContext

class ArticleViewModel(private val articleId: String, application: Application) : AndroidViewModel(application){

        init {
            Repository.setCurrentArticle(articleId)
        }
    val _firstStorageReference:MutableLiveData<StorageReference> = MutableLiveData(Repository.storageReference.child("Articles/${articleId}/0.jpg"))
    val firstStorageReference: LiveData<StorageReference>
    get() {
        return _firstStorageReference
    }
    fun returnStorageReference(): StorageReference{
        return Repository.storageReference.child("Articles/${articleId}/0.jpg")
    }
    companion object {
        val TAG = "ArticleViewModel"
        @JvmStatic
        @BindingAdapter("storageReference")
        fun loadImage(view: ImageView, storageReference: StorageReference?) { // This methods should not have any return type, = declaration would make it return that object declaration.
                GlideApp.with(view.context).load(storageReference).into(view)
        }

    }

    override fun onCleared() {
        super.onCleared()
        Repository.CurrentArticle.clearAllFields()
    }
}