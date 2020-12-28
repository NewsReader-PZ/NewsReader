package com.example.newsreader.ui.article

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.navArgs
import com.example.newsreader.GlideApp
import com.example.newsreader.R
import com.example.newsreader.Repository
import com.example.newsreader.databinding.ArticleFragmentBinding

class Article : Fragment() {
    private val TAG = "ArticleFragment"
    companion object {
        fun newInstance() = Article()
    }
    //private var binding:Article = null
    private val args: ArticleArgs by navArgs()
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding:ArticleFragmentBinding = ArticleFragmentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this,ArticleViewModelFactory(args.articleId)).get(ArticleViewModel::class.java)
        val view = binding.root
        binding.currentArticle = Repository.CurrentArticle
        binding.articleViewModel= viewModel
        binding.lifecycleOwner = this
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "Got articleId: ${args.articleId}")

        //val imageButton:ImageButton = view.findViewById(R.id.article_gallery)
//        GlideApp.with(this)
//                .load(Repository.CurrentArticle.images.value?.get(0))
//                // .override(150, 100)
//                .into(imageButton)
        // TODO: Use the ViewModel
    }

}