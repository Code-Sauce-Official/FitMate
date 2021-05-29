package com.acash.fitmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.fitmate.R
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.adapters.PostsAdapter
import com.acash.fitmate.models.Form
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_your_posts.*

class YourPostsFragment : Fragment() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_your_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loadedPosts = false

        val posts = ArrayList<Form>()
        val postsAdapter = PostsAdapter(posts, requireActivity())

        rvPosts.apply {
            adapter = postsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        database.collection("Forms")
            .whereEqualTo("uid", auth.uid.toString())
            .orderBy("createdDate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                loadedPosts = true
                if (!queryDocumentSnapshots.isEmpty) {
                    for (snapshot in queryDocumentSnapshots) {
                        posts.add(snapshot.toObject(Form::class.java))
                    }
                    postsAdapter.notifyDataSetChanged()
                    rvPosts?.scrollToPosition(0)
                }else{
                    tvNoPosts.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

        findPartnersBtn.setOnClickListener {
            if (loadedPosts) {
                if (posts.size < 5) {
                    (activity as MainActivity).setFragment(FillFormFragment())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Post Limit exceeded! Delete a few posts and then try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    requireContext(),
                    "Waiting for posts to load",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
