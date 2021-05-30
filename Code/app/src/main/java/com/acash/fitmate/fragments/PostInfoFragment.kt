package com.acash.fitmate.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.MatchViewHolder
import com.acash.fitmate.R
import com.acash.fitmate.activities.EmptyViewHolder
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.activities.createAlertDialog
import com.acash.fitmate.activities.createProgressDialog
import com.acash.fitmate.models.Form
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_post_info.*
import java.text.SimpleDateFormat

const val NORMAL_VIEW_TYPE = 1
const val DELETED_VIEW_TYPE = 2

class PostInfoFragment : Fragment() {

    private lateinit var database: Query

    private lateinit var matchesAdapter: FirestorePagingAdapter<Form,RecyclerView.ViewHolder>

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formJsonString = arguments?.getString("FormJsonString").toString()
        val postNo = arguments?.getString("PostNo").toString()
        val gson = Gson()
        val form = gson.fromJson(formJsonString, Form::class.java)

        tvPostNo.text = postNo
        tvCategory.text = getString(R.string.category_colorful, form.category)
        tvMotive.text = getString(R.string.motive_colorful, form.motive)

        val myFormat = "d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)

        form.createdDate?.let {
            tvCreatedDate.text = sdf.format(it)
        }

        if (form.isGenderSpecific) {
            tvGenderPref.text = getString(R.string.preference_colorful, "Same Gender")
            database = FirebaseFirestore.getInstance().collection("Forms")
                .whereEqualTo("category", form.category)
                .whereEqualTo("state", form.state)
                .whereEqualTo("gender", form.gender)
                .orderBy("yearOfBirth")
                .startAt(form.yearOfBirth - 3)
                .endAt(form.yearOfBirth + 3)
        } else {
            tvGenderPref.text = getString(R.string.preference_colorful, "Gender Independent")
            database = FirebaseFirestore.getInstance().collection("Forms")
                .whereEqualTo("category", form.category)
                .whereEqualTo("state", form.state)
                .orderBy("yearOfBirth")
                .startAt(form.yearOfBirth - 3)
                .endAt(form.yearOfBirth + 3)
        }

        setupAdapter()

        rvMatches.apply {
            adapter = matchesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        deleteBtn.setOnClickListener {
            requireContext().createAlertDialog(
                "Confirmation",
                "Are you sure you want to delete this post?",
                "Yes",
                "No"
            ){
                progressDialog = requireContext().createProgressDialog("Please Wait...", false)
                progressDialog.show()
                deletePostFromFirestore(form.formId)
            }
        }
    }

    private fun setupAdapter() {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .build()

        val options = FirestorePagingOptions.Builder<Form>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(database, config, Form::class.java)
            .build()

        matchesAdapter = object : FirestorePagingAdapter<Form, RecyclerView.ViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
                return if (viewType == NORMAL_VIEW_TYPE)
                    MatchViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.list_item_match, parent, false
                        ),
                        requireActivity()
                    )
                else EmptyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.empty_view, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: RecyclerView.ViewHolder,
                position: Int,
                model: Form
            ) {
                if(holder is MatchViewHolder) {
                    holder.bind(model)
                }
            }

            override fun getItemViewType(position: Int): Int {
                val item = getItem(position)?.toObject(Form::class.java)
                return if (item?.uid == auth.uid) {
                    DELETED_VIEW_TYPE
                } else NORMAL_VIEW_TYPE
            }
        }
    }

    private fun deletePostFromFirestore(formId: String) {

        FirebaseFirestore.getInstance().collection("Forms").document(formId)
            .delete()
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Post removed successfully", Toast.LENGTH_SHORT)
                    .show()
                (activity as MainActivity).onBackPressed()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
    }
}