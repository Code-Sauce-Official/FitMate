package com.acash.fitmate.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.acash.fitmate.R
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.activities.createProgressDialog
import com.acash.fitmate.models.Form
import com.acash.fitmate.models.Request
import com.acash.fitmate.models.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_user_info.*
import java.util.*

class UserInfoFragment : Fragment() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database by lazy {
        FirebaseFirestore.getInstance()
    }

    private val realtimeDb by lazy {
        FirebaseDatabase.getInstance("https://fitmate-f33d2-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formJsonString = arguments?.getString("FormJsonString").toString()
        val gson = Gson()
        val form = gson.fromJson(formJsonString, Form::class.java)

        progressDialog = requireContext().createProgressDialog("Fetching User Info...", false)
        progressDialog.show()

        motiveEt.setText(form.motive)
        tvName.text = form.name
        tvAge.text = (Calendar.getInstance().get(Calendar.YEAR) - form.yearOfBirth).toString()
        tvState.text = form.state

        database.collection("users").document(form.uid)
                .get()
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    if (it.exists()) {
                        val user = it.toObject(User::class.java)
                        user?.downloadUrlDp.let { url ->
                            if (url != "") {
                                Glide.with(this).load(url)
                                        .placeholder(R.drawable.defaultavatar)
                                        .error(R.drawable.defaultavatar).into(userImgView)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

        sendRequestBtn.setOnClickListener {
            (activity as MainActivity).currentUserInfo?.let { currUser ->
                progressDialog = requireContext().createProgressDialog("Sending Request...", false)
                progressDialog.show()
                val request = Request(
                        auth.uid.toString(),
                        currUser.name,
                        currUser.yearOfBirth,
                        currUser.gender,
                        currUser.state,
                        currUser.downloadUrlDp
                )

                val inbox = realtimeDb.reference.child("inbox/${currUser.uid}/${form.uid}")

                inbox.get()
                        .addOnSuccessListener { inboxDataSnapshot ->
                            if (inboxDataSnapshot.exists()) {
                                progressDialog.dismiss()
                                Toast.makeText(requireContext(), "This User is Already Your Partner", Toast.LENGTH_SHORT).show()
                            } else {
                                realtimeDb.reference.child("requests/${form.uid}/${auth.uid.toString()}")
                                        .get()
                                        .addOnSuccessListener { dataSnapshot ->
                                            if (!dataSnapshot.exists()) {
                                                realtimeDb.reference.child("requests/${form.uid}/${auth.uid.toString()}").setValue(request)
                                                progressDialog.dismiss()
                                                Toast.makeText(requireContext(), "Partner Request Sent", Toast.LENGTH_SHORT).show()
                                                return@addOnSuccessListener
                                            }
                                            progressDialog.dismiss()
                                            Toast.makeText(requireContext(), "Partner Request Already Sent", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener { exception ->
                                            progressDialog.dismiss()
                                            Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
                                        }
                            }
                        }
                        .addOnFailureListener { exception ->
                            progressDialog.dismiss()
                            Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
                        }
            }
        }

    }

}