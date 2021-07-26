package com.acash.fitmate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.fitmate.R
import com.acash.fitmate.viewholders.RequestViewHolder
import com.acash.fitmate.models.Request
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_requests.*

class RequestsFragment : Fragment() {

    private lateinit var requestsAdapter: FirebaseRecyclerAdapter<Request, RequestViewHolder>

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database by lazy {
        FirebaseDatabase.getInstance("https://fitmate-f33d2-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupAdapter()
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRequests.apply {
            adapter = requestsAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun setupAdapter() {
        val baseQuery = database.reference.child("requests/${auth.uid}")

        val options = FirebaseRecyclerOptions.Builder<Request>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(baseQuery, Request::class.java)
            .build()

        requestsAdapter = object : FirebaseRecyclerAdapter<Request, RequestViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder =
                RequestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_request, parent, false)
                ,requireActivity())

            override fun onBindViewHolder(holder: RequestViewHolder, position: Int, request:Request) {
                holder.bind(request)
            }
        }
    }
}