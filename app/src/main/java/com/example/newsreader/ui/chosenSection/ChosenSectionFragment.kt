package com.example.newsreader.ui.chosenSection

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsreader.R
import com.example.newsreader.Repository
import com.example.newsreader.ui.home.HomeFragmentDirections
import com.example.newsreader.ui.home.HomeViewModel
import kotlin.coroutines.coroutineContext

class ChosenSectionFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance() = ChosenSectionFragment()
    }
    val args:ChosenSectionFragmentArgs by navArgs()


    private val TAG = "HomeFragment"
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenSectionViewModel: ChosenSectionViewModel
    private lateinit var chosenSection:String
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        chosenSection = when(args.chosenCategory){
            getString(R.string.poland)->"poland"
            getString(R.string.europe)->"europe"
            getString(R.string.world)->"world"
            getString(R.string.politics)->"politics"
            getString(R.string.business)->"business"
            getString(R.string.health)->"health"
            getString(R.string.opinion)->"opinion"
            getString(R.string.sports)->"sport"
            getString(R.string.technology)->"technology"
            getString(R.string.automobiles)->"automobiles"
            else->""
        }
        Repository.setArticlesForChosenSection(chosenSection, 10)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chosenSectionViewModel = ViewModelProvider(this).get(ChosenSectionViewModel::class.java)
        val view: View = inflater.inflate(R.layout.fragment_home,container,false)
        recyclerView = view.findViewById(R.id.mainArticlesList)
        recyclerView.setHasFixedSize(false)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshInHomeFragment)

        val llm: ChosenSectionViewModel.myLinearLayoutManager = ChosenSectionViewModel.myLinearLayoutManager(view.context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm

        val myAdapter = ChosenSectionAdapter(ChosenSectionAdapter.ChosenSectionListener { articleId->
            val action = ChosenSectionFragmentDirections.actionChosenSectionFragmentToArticle()
            action.articleId = articleId
            Log.i(TAG,"onItemCLick: ${action.articleId}")
            findNavController().navigate(action)
        })
        recyclerView.adapter = myAdapter
        //lifecycle.addObserver(chosenSectionViewModel)

        swipeRefreshLayout.setOnRefreshListener {
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout")
            onRefresh()
        }
        chosenSectionViewModel.selectedArticleArray.observe( viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.i(TAG,"Observer update, Data size: ${chosenSectionViewModel.selectedArticleArray.value?.size}")
            Log.i(TAG,"Observer update, Data size: ${it.size}")
            (recyclerView.adapter as ChosenSectionAdapter).data  = it })
//        val binding:HomeFragmentBinding = DataBindingUtil.setContentView(
//            requireActivity().parent,R.layout.fragment_home
//        )
        //val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val adapter = MainArticlesAdapter()
        return view
    }
//
//    override fun onStart() {
//        super.onStart()
//        Repository.setArticlesForHomeView()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Repository.setArticlesForHomeView()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onRefresh() {
        Repository.setArticlesForChosenSection(chosenSection, 10)
        swipeRefreshLayout.isRefreshing = false
    }

}