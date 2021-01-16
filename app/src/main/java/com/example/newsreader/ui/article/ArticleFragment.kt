package com.example.newsreader.ui.article

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsreader.Repository
import com.example.newsreader.databinding.ArticleFragmentBinding

class ArticleFragment : Fragment() {
    private val TAG = "ArticleFragment"
    companion object {
        fun newInstance() = ArticleFragment()
    }
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding:ArticleFragmentBinding = ArticleFragmentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this,ArticleViewModelFactory(args.articleId, application = requireActivity().application)).get(ArticleViewModel::class.java)
        val view = binding.root
        binding.currentArticle = Repository.CurrentArticle
        binding.articleViewModel= viewModel
        binding.lifecycleOwner = this
        binding.commentsButton.setOnClickListener {
            val action = ArticleFragmentDirections.actionArticleToCommentsFragment()
            action.articleId = args.articleId
            findNavController().navigate(action)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "Got articleId: ${args.articleId}")

        // TODO: Use the ViewModel
    }
    fun onArticleCommentsButtonClick(view: View){

    }
}
