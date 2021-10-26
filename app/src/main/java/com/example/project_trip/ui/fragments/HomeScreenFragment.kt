package com.example.project_trip.ui.fragments

import PagerAdapters
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.project_trip.*
import com.example.project_trip.ui.adapters.InterestAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeScreenFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    private lateinit var interestshow : TextView
    private var showtext : String = ""
    private lateinit var tabLayout: TabLayout
    private lateinit var viewpager : ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.tablayout)
        viewpager = view.findViewById(R.id.viewpager)

        var pagerAdapters = PagerAdapters(childFragmentManager)

        pagerAdapters.addFragment(ProfileFragment(),"")
        pagerAdapters.addFragment(MyListFragment(),"")
        pagerAdapters.addFragment(RecommendationFragment(),"")
        pagerAdapters.addFragment(DocumentsFragment(),"")

        viewpager.adapter = pagerAdapters

        tabLayout.setupWithViewPager(viewpager)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_baseline_home_24)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_baseline_view_list_24)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_baseline_favorite_24)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_baseline_insert_drive_file_24)


        val db = Firebase.firestore
        val mAuth = FirebaseAuth.getInstance()
        val name = mAuth.currentUser?.displayName
        val interestRef = db.document("User Interest/$name")

//        interestshow = view.findViewById(R.id.interestshow)

        val interest_of_user = hashMapOf<String,String>()

        for(item in InterestAdapter.selectedItems){
            interest_of_user.put(item.title,item.title)
        }

        interestRef.get()
            .addOnSuccessListener {
                if(it.exists()){
                    val map = it.getData()
                    if (map != null) {
                        for(item in map){
                            showtext = showtext + item.value + "\n"
                        }
                    }
//                    interestshow.setText(showtext)
                }else{
                    Toast.makeText(activity,"Document does not exist", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {


                Toast.makeText(activity,"Error occured",Toast.LENGTH_SHORT).show()
            }

    }
}