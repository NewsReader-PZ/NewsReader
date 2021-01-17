package com.example.komentarze
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.ui.commentData.Comment

object DateUtils {
    @JvmStatic
    fun toSimpleString(date: java.util.Date) : String {
        val format = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat("dd/MM/yyyy")
        } else {
            return date.toString()
        }
        return format.format(date)
    }
}

internal class CommentsAdapter(private var commentsList: ArrayList<Comment>) :RecyclerView.Adapter<CommentsAdapter.MyViewHolder>(){

    fun setItems(newComments: ArrayList<Comment>) {
        commentsList = newComments
    }
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var nick: TextView = view.findViewById(R.id.textViewNickCommLayout)
        var dateOfPublishing: TextView = view.findViewById(R.id.textViewDateCommLayout)
        var commentText :TextView = view.findViewById(R.id.textViewCommentTextCommLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.commentlayout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.nick.text=comment.userName
        holder.dateOfPublishing.text = DateUtils.toSimpleString(comment.date)
        holder.commentText.text = comment.text
        Log.println(Log.INFO,"CommentsAdapter", "values to display ${comment.userName}, ${DateUtils.toSimpleString(comment.date)}, ${comment.text}")
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

}
