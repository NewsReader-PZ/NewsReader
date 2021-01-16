package com.example.komentarze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsreader.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeaveCommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaveCommentFragment : Fragment() {
    lateinit var textViewLeaveComment : TextView
    lateinit var textViewNick : TextView
    lateinit var textViewComment: EditText
    lateinit var buttonLeaveComment :Button

    lateinit var commentsViewModel :CommentsViewModel

    private val args:LeaveCommentFragmentArgs by navArgs()
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_leave_comment, container, false)
        //return inflater.inflate(R.layout.fragment_leave_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignUIElementsToVariables()

        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        buttonLeaveComment.setOnClickListener { leaveComment() }
        val acti = this.activity
        //commentsViewModel.commentAdded.observe(this, object :Observer<Boolean>{ // przedtem była taka linijka
        commentsViewModel.commentAdded.observe(viewLifecycleOwner, object :Observer<Boolean>{
            override fun onChanged(t: Boolean?) {
                if(t ?: true)
                {
                    Toast.makeText(acti,"Comment posted.",Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(acti,"Failed to post comment.",Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun assignUIElementsToVariables()
    {
        //przedtem w miejsce requireView było getView()!!
        textViewLeaveComment = requireView().findViewById(R.id.textViewLeaveCommFrag)
        textViewNick = requireView().findViewById(R.id.textViewUserNickLeaveCommFrag)
        textViewComment = requireView().findViewById(R.id.editTextUserCommentLeaveCommFrag)
        buttonLeaveComment = requireView().findViewById(R.id.buttonLeaveCommentLeaveCommFrag)


    }
    private fun leaveComment()
    {
        val text = textViewComment.text.toString()
        if(!text.isEmpty()) {
            commentsViewModel.leaveComment(text, args.articleId)
        }
        else
            Toast.makeText(this.activity,"Cannot post empty comment.",Toast.LENGTH_SHORT).show()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaveCommentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaveCommentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}