package com.example.newsreader.ui.loginAndRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.archmvvm2.CommentsRepo

class LoginViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
/* //attempt to replace observer with corutine
    fun userHasSignedIm(uid: String)
    {
        CommentsRepo.setUserID(uid)
        CommentsRepo.setCurrentUserNick()
        CommentsRepo.userHasSignedUp()
    }
    */
    suspend fun suspUserHasSignedIn(uid: String)
    {
        //attempt to replace observer with corutine
        CommentsRepo.setUserID(uid)
        CommentsRepo.setCurrentUserNick2()
        CommentsRepo.userHasSignedUp()
    }
    val text: LiveData<String> = _text
}