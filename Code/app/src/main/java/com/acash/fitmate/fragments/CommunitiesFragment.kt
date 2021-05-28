package com.acash.fitmate.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.fitmate.R
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.activities.createProgressDialog
import com.acash.fitmate.adapters.CommunitiesAdapter
import com.acash.fitmate.models.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_communities.*

class CommunitiesFragment : Fragment() {

    val selectedCommunities = HashSet<String>()

    private val database by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_communities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val communitiesAdapter = CommunitiesAdapter(Community.getCommunities(),this)

        rvCommunities.apply {
            adapter = communitiesAdapter
            layoutManager = GridLayoutManager(requireContext(),3,LinearLayoutManager.VERTICAL,false)
        }

        joinBtn.setOnClickListener {
            if(selectedCommunities.size==0){
                Toast.makeText(requireContext(),"No communities selected!",Toast.LENGTH_SHORT).show()
            }else {
                progressDialog = requireContext().createProgressDialog("Saving Data, Please wait...", false)
                progressDialog.show()
                val listSelectedCommunities = ArrayList<String>(selectedCommunities)
                database.collection("users").document(auth.uid.toString())
                    .update(
                        mapOf(
                            "communities" to listSelectedCommunities,
                        )
                    )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressDialog.dismiss()
                            startActivity(
                                Intent(requireContext(), MainActivity::class.java)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            )
                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                task.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        skipBtn.setOnClickListener {
            startActivity(
                Intent(requireContext(), MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

}