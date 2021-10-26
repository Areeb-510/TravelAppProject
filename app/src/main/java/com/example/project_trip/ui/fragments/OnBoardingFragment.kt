package com.example.project_trip.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.project_trip.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OnBoardingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if(user != null){
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToInterestSelectionFragment()
            findNavController().navigate(action)
        }
        arguments?.let {

        }
    }

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var progressBar : ProgressBar
    private lateinit var google_sign_in : CardView
    private lateinit var googlesignInTxt : TextView
    private lateinit var signupbtn : TextView
    private lateinit var tologin : TextView
    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var progress : ProgressBar
    private var i = 0
    private val handler = Handler()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity,gso)



        progress = view.findViewById(R.id.progress_bar)


        google_sign_in = view.findViewById(R.id.google_card)
        googlesignInTxt = view.findViewById(R.id.googletext)
        signupbtn = view.findViewById(R.id.sign_up)
        tologin = view.findViewById(R.id.tologin)


        google_sign_in.setOnClickListener {
            Toast.makeText(activity,"Clicked Google SignIn Button",Toast.LENGTH_SHORT).show()
            signIn()
        }




        signupbtn.setOnClickListener {
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToSignUpFragment()
            view.findNavController().navigate(action)
        }
        tologin.setOnClickListener {
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginActivityFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("OnBoardingFragment", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("OnBoardingFragment", "Google sign in failed", e)
                }
            }else{

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progress.visibility = View.VISIBLE

                    i = progress.progress

                    Thread(Runnable {
                        // this loop will run until the value of i becomes 99
                        while (i < 100) {
                            i += 1
                            // Update the progress bar and display the current value
                            handler.post(Runnable {
                                progress.progress = i
                                // setting current progress to the textview
                            })
                            try {
                                Thread.sleep(300)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }

                        // setting the visibility of the progressbar to invisible
                        // or you can use View.GONE instead of invisible
                        // View.GONE will remove the progressbar
                        progress.visibility = View.INVISIBLE

                    }).start()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
//                    val user = mAuth.currentUser
                    val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToInterestSelectionFragment()
                    findNavController().navigate(action)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }
}