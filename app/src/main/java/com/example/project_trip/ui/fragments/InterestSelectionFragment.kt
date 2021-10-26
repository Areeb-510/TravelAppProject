package com.example.project_trip.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_trip.R
import com.example.project_trip.ui.adapters.InterestAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InterestSelectionFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val check = loaddata()

        if (check==true) {
                val action = InterestSelectionFragmentDirections.actionInterestSelectionFragmentToHomeScreenFragment()
                findNavController().navigate(action)
        }

        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_interest_selection, container, false)
    }

    private lateinit var continue_btn : AppCompatButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recylerview)
        recyclerView.layoutManager = GridLayoutManager(activity,2)
        recyclerView.adapter = InterestAdapter()

        val db = Firebase.firestore

        continue_btn = view.findViewById(R.id.continue_btn)

        continue_btn.setOnClickListener {

            if(InterestAdapter.selectedItems.size == 0){
                Toast.makeText(activity,"Please Select at least one interest", Toast.LENGTH_SHORT).show()
            }else{
                saveInteresttoDatabase()
                savedata()
                val action = InterestSelectionFragmentDirections.actionInterestSelectionFragmentToHomeScreenFragment()
                findNavController().navigate(action)
            }
        }

    }

    fun savedata(){
        val sharedPreferences = context?.getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putBoolean("BOOLEAN_KEY",true)
        }?.apply()
    }

    fun loaddata() : Boolean? {
        val sharedPreferences = context?.getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val savedboolean = sharedPreferences?.getBoolean("BOOLEAN_KEY",false)
        return savedboolean
    }

    fun saveInteresttoDatabase(){
        val db = Firebase.firestore
        val mAuth = FirebaseAuth.getInstance()
        val name = mAuth.currentUser?.displayName
        val interestRef = db.document("User Interest/$name")


        val interest_of_user = hashMapOf<String,String>()

        for(item in InterestAdapter.selectedItems){
            interest_of_user.put(item.title,item.title)
        }


        db.collection("User Interest").document("$name").set(interest_of_user)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                Toast.makeText(activity,"Failed", Toast.LENGTH_SHORT).show()
                Log.d("FirestoreFailed",it.toString())
            }
    }

}