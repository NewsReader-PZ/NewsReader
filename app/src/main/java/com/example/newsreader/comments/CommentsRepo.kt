package com.example.archmvvm2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsreader.ui.CommentData.Comment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList


object CommentsRepo {

    private var CRUserID =""
    private var CRUserNick = ""
    fun setUserID(u :String)
    {
        CRUserID =u
    }
    /*
    fun downloadUIDAndNick()
    {
        downloadUserUiD()
        //downloadUserNick()
    }


    private fun downloadUserUiD()
    {
        this.CRUserID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        Log.println(Log.INFO, "download user ID", "user ID: *$CRUserID*")
    }

     */
    fun downloadUserNick()
    {
        if( CRUserID =="")
        {
            return
        }

        try {

            CRUserNick = getCommentAuthorName(CRUserID)
            if(CRUserNick=="")
            {
                Log.println(Log.INFO, "download user nick", "user nick: *$CRUserNick*")
            }
        }
        catch (e :Throwable)
        {
            Log.println(Log.ERROR, "download user nick", "faile to download user nick")
        }
        /*
        db.collection("Users").document(CRUserID).get().addOnCompleteListener{task ->
            if(task.isSuccessful)
            {
             val doc = task!!.result
                CRUserNick = doc!!.get("nick") as String
            }
        }
        */
    }

    private val db = FirebaseFirestore.getInstance()
    private val commentsArrayMLD = MutableLiveData<ArrayList<Comment>>()
    private val commentAdded = MutableLiveData<Boolean>()
    private var articleUid=""
    var arrayOfComments = ArrayList<Comment>()
        private set

    fun getCommentsArrayMLD() :LiveData<ArrayList<Comment>>
    {
        return commentsArrayMLD
    }
    fun getCommentAdded() :LiveData<Boolean>
    {
        return commentAdded
    }


    fun getCommentsOfArticle(articleUid: String)
    {
        //val comments = db.collection(  "Articles/" + articlePath +"/Comments")
        //val comments =
        this.articleUid = articleUid
         db.collection("Articles").document(articleUid).collection("Comments")
            .orderBy("date", Query.Direction.DESCENDING).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    arrayOfComments.clear()
                    for (document in task.result!!) {
                        try {
                            val userUid = document["user"] as String
                            //val userName = getCommentAuthorName(userUid)
                            val userName = document["nick"] as String
                            val text = document["text"] as String
                            val date = document["date"] as Timestamp
                            val id = document.id
                            var comment = Comment(date = date.toDate(),
                            text = text,
                            id = id,
                            userUid = userUid,
                            userName = userName,
                            articleId = articleUid)
                            //var javaDate = java.util.Date.UTC(date.year,date.month,date.day,date.)

                            arrayOfComments.add(comment)
                            Log.println(Log.INFO,"comments repo", "fetched comment: $text, date: ${comment.date}")

                        }
                        catch (e:Exception)
                        {
                            Log.println(Log.ERROR,"comments repo", "Failed to fetch document")
                        }

                    }
                    Log.println(Log.INFO,"getCommentsOfArticle","just before updating mutable live data")
                    //commentsDownloadedEvent.value = commentsDownloadedEvent.value!!.toInt() + 1
                    commentsArrayMLD.value = arrayOfComments
                    Log.println(Log.INFO,"getCommentsOfArticle","After updating mutable live data")
                }
            }
    }

    @Throws(Exception::class)
    fun getCommentAuthorName(userID :String): String {


        var nickOfUser=""
        if(userID==null) {
            throw Exception()
        }
        try {


            db.collection("Users").document(userID).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            nickOfUser = document["nick"] as String
                            Log.println(Log.INFO, "comments repo", "Fetched user name: $nickOfUser")
                            return@addOnSuccessListener
                        } else {
                            Log.println(Log.ERROR, "comments repo", "Failed to fetch user name")
                            throw Exception()
                        }
                    }
                    .addOnFailureListener { exception ->
                        throw Exception()
                    }

            Log.println(Log.INFO, "getNickFunction", "before return - nick: $nickOfUser")
            return nickOfUser
        }
        catch(e:Exception)
        {
            Log.println(Log.ERROR, "getNickFunction", "error while searching for nick")
        }
        return "unknown nick"
    }

    fun leaveComment(commentText :String, articleUid: String)  {
        try {

                val currentUser = getUserUid()
            val nick = getUserNick()
            val date = Timestamp.now().toDate()
            val hashMap = hashMapOf(
                "nick" to nick,
                "user" to currentUser,
                "date" to date,
                "text" to commentText
            )
            db.collection("Articles").document(articleUid).collection("Comments").document()
                .set(hashMap)
                .addOnSuccessListener { commentAdded.value = true }
                .addOnFailureListener { commentAdded.value = false }
        }
        catch (e: Throwable)
        {
            commentAdded.value = false
        }
    }
    private fun getUserUid() :String
    {
        return CRUserID;
    }
    private fun getUserNick() :String
    {
        return CRUserNick;
    }

}