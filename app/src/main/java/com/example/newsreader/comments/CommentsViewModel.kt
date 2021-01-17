package com.example.komentarze

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.archmvvm2.CommentsRepo
import com.example.newsreader.ui.CommentData.Comment

class CommentsViewModel() : ViewModel() {

    constructor(articleId:String) :this()
    {
        CommentsRepo.getCommentsOfArticle(articleId)
    }
    init {

    }
    private var articleUID = ""
    val commentsArray :LiveData<ArrayList<Comment>> get() { return CommentsRepo.getCommentsArrayMLD() }
    val commentAdded :LiveData<Boolean> get() { return  CommentsRepo.getCommentAdded()}
    val currentUserNick :LiveData<String> get() { return  CommentsRepo.getCurrentUserNick()}
    fun setCurrentNick(nick :String)
    {
        CommentsRepo.setUserNick(nick)
    }

    fun setArticleUID(artUID:String)
    {
        articleUID = artUID
        CommentsRepo.getCommentsOfArticle(artUID)
    }
    fun leaveComment(commentText :String, articleId: String)
    {
        CommentsRepo.leaveComment(commentText, articleId)
    }










}

/*
     fun downloadComments(articleUID: String)
    {
        this.articleUID.value = articleUID
        CommentsRepo.getCommentsOfArticle(articleUID)
        vmComments.value = CommentsRepo.arrayOfComments
        commentsDownloadedEvent.value = commentsDownloadedEvent.value!!.toInt() + 1
    }
     */

/*
fun setArticleUid(artUid:String)
    {
        articleUID.value = artUid

        getComments(artUid)
        //CommentsRepo.getCommentsOfArticle(articleUID.value!!)

    }
 */

/*

private fun getComments(articleUid: String)
    {


            db.collection("Articles").document(articleUid).collection("Comments")
                .orderBy("date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        CommentsRepo.arrayOfComments.clear()
                        for (document in task.result!!) {
                            try {

                                val userName = getAuthorNick(document)
                                val userUid = document["user"] as String
                                val text = document["text"] as String
                                val date = document["date"] as Timestamp
                                var comment = Comment()
                                comment.userName = userName
                                comment.text = text
                                //var javaDate = java.util.Date.UTC(date.year,date.month,date.day,date.)
                                comment.date = date.toDate()
                                comment.userName = userUid
                                CommentsRepo.arrayOfComments.add(comment)
                                Log.println(Log.INFO,"vm - getComments", "fetched comment: $text, date: ${comment.date}")

                            }
                            catch (e:Exception)
                            {
                                Log.println(Log.ERROR,"vm - getComments", "Failed to fetch document")
                            }

                        }
                        Log.println(Log.INFO,"vm - getComments","just before updating mutable live data")
                        //commentsDownloadedEvent.value = commentsDownloadedEvent.value!!.toInt() + 1
                        vmComments.value = CommentsRepo.arrayOfComments
                        Log.println(Log.INFO,"vm - getComments","After updating mutable live data")
                    }
                }

    }
 */

/*
private fun getAuthorNick(doc: QueryDocumentSnapshot?) :String
    {
        val userID = doc?.get("user") as String
        var nick=""
        if(userID==null) {
            throw Exception()
        }
       db.collection("Users").document(userID).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    nick = document["nick"] as String
                    Log.println(Log.INFO,"vm - getComments", "Fetched user name: $nick")
                } else {
                    Log.println(Log.ERROR,"vm - getComments", "Failed to fetch user name")
                    throw Exception()
                }
            }
            .addOnFailureListener { exception ->
                throw Exception()
            }
        return nick
    }
 */

