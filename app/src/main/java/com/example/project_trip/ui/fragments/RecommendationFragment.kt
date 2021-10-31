package com.example.project_trip.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.project_trip.R
import com.example.project_trip.data.Places
import com.example.project_trip.ui.adapters.SuggestListAdapter

class RecommendationFragment : Fragment() {

    companion object{
        fun newInstance() : RecommendationFragment = RecommendationFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    private lateinit var recyclerview : RecyclerView
    private lateinit var mAdapter : SuggestListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerview = view.findViewById(R.id.recylerview2)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = SuggestListAdapter()
        recyclerview.adapter = mAdapter
    }


}