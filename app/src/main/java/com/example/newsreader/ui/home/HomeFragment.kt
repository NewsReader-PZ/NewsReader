package com.example.newsreader.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MotionEventCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsreader.R
import com.example.newsreader.Repository
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{
    private val TAG = "HomeFragment"
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repository.setArticlesForHomeView()
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater.inflate(R.layout.fragment_home,container,false)
        recyclerView = view.findViewById(R.id.mainArticlesList)
        recyclerView.setHasFixedSize(false)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshInHomeFragment)
        val llm:LinearLayoutManager = LinearLayoutManager(view.context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm

        val myAdapter = MainArticlesAdapter(homeViewModel, MainArticlesAdapter.MainArticlesListener {articleId->
            val action = HomeFragmentDirections.actionNavigationHomeToArticle()
            action.articleId = articleId
           // Log.i(TAG,"onItemCLick: ${action.articleId}")
            findNavController().navigate(action)
        })
        myAdapter.headersArrayList.add(getString(R.string.europe))
        myAdapter.headersArrayList.add(getString(R.string.opinion))
        recyclerView.adapter = myAdapter
        lifecycle.addObserver(homeViewModel)

        swipeRefreshLayout.setOnRefreshListener {
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout")
            onRefresh()
        }
//        val binding:HomeFragmentBinding = DataBindingUtil.setContentView(
//            requireActivity().parent,R.layout.fragment_home
//        )
        //val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val adapter = MainArticlesAdapter()
        return view
    }

    override fun onStart() {
        super.onStart()
        Repository.setArticlesForHomeView()
    }

    override fun onResume() {
        super.onResume()
        Repository.setArticlesForHomeView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.articlesArray.observe( viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.i(TAG,"Observer update, Data size: ${homeViewModel.articlesArray.value?.size}")
            Log.i(TAG,"Observer update, Data size: ${it.size}")
            (recyclerView.adapter as MainArticlesAdapter).data  = it })
        }

    override fun onRefresh() {
        Repository.setArticlesForHomeView()
        swipeRefreshLayout.isRefreshing = false
    }
}
