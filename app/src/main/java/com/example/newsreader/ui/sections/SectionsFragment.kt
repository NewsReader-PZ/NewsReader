package com.example.newsreader.ui.sections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.newsreader.R


class SectionsFragment : Fragment() {
    val TAG:String = "SectionsFragment"
    private lateinit var sectionsViewModel: SectionsViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_sections,container,false)
        recyclerView = view.findViewById(R.id.sectionsList)
        recyclerView.setHasFixedSize(true)
        val llm: LinearLayoutManager = LinearLayoutManager(view.context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm

        val myAdapter = SectionsAdapter( SectionsAdapter.SectionsListener { sectionName->
            Log.i(TAG,"onItemCLick: $sectionName")
            val action = SectionsFragmentDirections.actionNavigationSectionsToChosenSectionFragment()
            action.chosenCategory = sectionName
            findNavController().navigate(action)
        })
        myAdapter.data.add(SectionData(getString(R.string.poland), R.drawable.poland))
        myAdapter.data.add(SectionData(getString(R.string.europe), R.drawable.europe))
        myAdapter.data.add(SectionData(getString(R.string.world), R.drawable.world))
        myAdapter.data.add(SectionData(getString(R.string.politics), R.drawable.politics))
        myAdapter.data.add(SectionData(getString(R.string.business), R.drawable.business))
        myAdapter.data.add(SectionData(getString(R.string.health), R.drawable.health))
        myAdapter.data.add(SectionData(getString(R.string.opinion), R.drawable.opinion))
        myAdapter.data.add(SectionData(getString(R.string.sports), R.drawable.sports))
        myAdapter.data.add(SectionData(getString(R.string.technology), R.drawable.technology))
        myAdapter.data.add(SectionData(getString(R.string.automobiles), R.drawable.car))
        recyclerView.adapter = myAdapter
//        val binding:HomeFragmentBinding = DataBindingUtil.setContentView(
//            requireActivity().parent,R.layout.fragment_home
//        )
        //val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val adapter = MainArticlesAdapter()
        return view
    }
}