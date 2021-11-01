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
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.project_trip.R
import com.example.project_trip.data.Places
import com.example.project_trip.ui.adapters.SuggestListAdapter
import com.google.gson.JsonObject
import org.json.JSONObject
import com.android.volley.toolbox.Volley

import com.android.volley.RequestQueue





class ProfileFragment : Fragment() {

    companion object{
        fun newInstance() : ProfileFragment = ProfileFragment()
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(SuggestListAdapter.places.size==0)
            getplacesdata()

    }

    fun getplacesdata(){
        val url = "https://d234-2405-201-600e-301e-c871-ac13-6af2-86c9.eu.ngrok.io"

        val queue = Volley.newRequestQueue(context)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Toast.makeText(activity,"success", Toast.LENGTH_SHORT).show()
                val placeArray = ArrayList<Places>()
                for(i in 0 until response.length()){
                    val JSONObject = response.getJSONObject(i)
                    val places = Places(JSONObject.getString("name"),"$url"+JSONObject.getString("image"),JSONObject.getString("state"),JSONObject.getString("country"))
//                    Toast.makeText(activity,"${places.name}",Toast.LENGTH_SHORT).show()
                    placeArray.add(places)
                }
//                mAdapter.updateplaces(placeArray)
                SuggestListAdapter.places.addAll(placeArray)
            },
            { error ->
                Toast.makeText(activity,"$error", Toast.LENGTH_LONG).show()
                Log.d("VOLLEY","$error")
            }
        )

        queue.add(jsonArrayRequest)
    }

}