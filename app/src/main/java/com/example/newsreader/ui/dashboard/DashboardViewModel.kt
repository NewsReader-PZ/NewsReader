package com.example.newsreader.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.archmvvm2.CommentsRepo

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }

    fun userHasSignedIm(uid: String)
    {
        CommentsRepo.setUserID(uid)
        CommentsRepo.setCurrentUserNick()
        CommentsRepo.userHasSignedUp()
    }
    val text: LiveData<String> = _text
}