package com.acash.fitmate.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.fitmate.viewholders.InboxViewHolder
import com.acash.fitmate.R
import com.acash.fitmate.activities.ChatActivity
import com.acash.fitmate.activities.NAME
import com.acash.fitmate.activities.THUMBIMG
import com.acash.fitmate.activities.UID
import com.acash.fitmate.models.Inbox
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_partners.*

class PartnersFragment : Fragment() {
    private lateinit var inboxAdapter: FirebaseRecyclerAdapter<Inbox, InboxViewHolder>

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
        setupAdapter()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partners, container, false)
    }

    private fun setupAdapter() {
        val baseQuery = database.reference.child("inbox/${auth.uid}").orderByChild("time/time")

        val options = FirebaseRecyclerOptions.Builder<Inbox>()
                .setLifecycleOwner(viewLifecycleOwner)
                .setQuery(baseQuery, Inbox::class.java)
                .build()

        inboxAdapter = object : FirebaseRecyclerAdapter<Inbox, InboxViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder =
                    InboxViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_partner, parent, false))

            override fun onBindViewHolder(holder: InboxViewHolder, position: Int, inbox: Inbox) {
                holder.bind(inbox) { name, uid, thumbImg ->
                    val intent = Intent(requireContext(), ChatActivity::class.java)
                    intent.putExtra(NAME, name)
                    intent.putExtra(UID, uid)
                    intent.putExtra(THUMBIMG, thumbImg)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        chatRview.apply {
            layoutManager = mLayoutManager
            adapter = inboxAdapter
        }
    }
}