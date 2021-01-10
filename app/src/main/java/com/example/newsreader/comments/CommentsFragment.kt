package com.example.komentarze

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.ui.CommentData.Comment
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var commentsAdapter :CommentsAdapter
    private lateinit var commentsViewModel: CommentsViewModel
    private lateinit var commentsRecyclerView: RecyclerView
    private lateinit var commentsList :ArrayList<Comment>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentsList = ArrayList<Comment>()
        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        prepareRecyclerView()

    }

    private fun prepareRecyclerView()
    {
        //przedtem zamiast requireView() było getView()
        commentsRecyclerView = requireView().findViewById(R.id.RecyclerViewCommFrag)
        commentsAdapter = CommentsAdapter(commentsList)
        val layoutManager = LinearLayoutManager(this.context)
        commentsRecyclerView.layoutManager = layoutManager
        commentsRecyclerView.itemAnimator = DefaultItemAnimator()
        commentsRecyclerView.adapter = commentsAdapter
        addObserverToCommentsLiveData()
    }
    private fun addObserverToCommentsLiveData()
    {
        //commentsViewModel.commentsArray.observe(this,object :Observer<ArrayList<Comment>>{
        commentsViewModel.commentsArray.observe(viewLifecycleOwner,object :Observer<ArrayList<Comment>>{
            override fun onChanged(t: ArrayList<Comment>?) {
                Log.println(Log.INFO, "comm fragm observer", "observer detected data change")
                commentsList = t!!
                (commentsRecyclerView.adapter as CommentsAdapter).setItems(commentsList)
                commentsRecyclerView.adapter!!.notifyDataSetChanged()
            }
        })
    }

    /*
    //function to test the recyclerView
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fillCommentsList(commentsList: ArrayList<Comment>) {
        commentsList.clear()
        for(i in 1 .. 50)
        {
            var comment = Comment()
            //val date: Date = Date(2021,1,5,17,14,i)
            //comment.date =Date.from(Instant.now())
            comment.date = Date(2021,1,5,17,38,i)
            comment.userName="User$i"
            comment.text = "$i comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment comment $i"
            comment.userUid="$i"
            commentsList.add(comment)
        }

    }


     */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}