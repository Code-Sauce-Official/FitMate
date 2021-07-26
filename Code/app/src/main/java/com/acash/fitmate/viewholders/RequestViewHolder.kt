package com.acash.fitmate.viewholders

import android.app.Activity
import android.app.ProgressDialog
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.activities.createProgressDialog
import com.acash.fitmate.models.Inbox
import com.acash.fitmate.models.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.list_item_request.view.*
import java.util.*

class RequestViewHolder(itemView: View, private val activityRef: Activity) :
    RecyclerView.ViewHolder(itemView) {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val realtimeDb by lazy {
        FirebaseDatabase.getInstance("https://fitmate-f33d2-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    private lateinit var progressDialog: ProgressDialog

    fun bind(request: Request) {
        itemView.apply {
            tvName.text = request.name
            tvGender.text = request.gender
            tvAge.text =
                (Calendar.getInstance().get(Calendar.YEAR) - request.yearOfBirth).toString()

            acceptBtn.setOnClickListener {
                progressDialog = activityRef.createProgressDialog("Please Wait...", false)
                progressDialog.show()

                val inboxMapCurrUser = Inbox(
                    "",
                    request.uid,
                    request.name,
                    request.downloadUrlDp,
                    0
                )

                realtimeDb.reference.child("inbox/${auth.uid.toString()}/${request.uid}")
                    .setValue(inboxMapCurrUser)
                    .addOnSuccessListener {
                        val inboxMapOtherUser = Inbox(
                            "",
                            auth.uid.toString(),
                            (activityRef as MainActivity).currentUserInfo?.name ?: "",
                            (activityRef as MainActivity).currentUserInfo?.downloadUrlDp ?: "",
                            0
                        )

                        realtimeDb.reference.child("inbox/${request.uid}/${auth.uid.toString()}")
                            .setValue(inboxMapOtherUser)
                            .addOnSuccessListener {
                                realtimeDb.reference.child("requests/${auth.uid.toString()}/${request.uid}")
                                    .removeValue()
                                    .addOnSuccessListener {
                                        progressDialog.dismiss()
                                        Toast.makeText(activityRef,"Added partner successfully", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        progressDialog.dismiss()
                                        Toast.makeText(activityRef,it.message, Toast.LENGTH_SHORT).show()
                                    }
                            }
                            .addOnFailureListener {
                                progressDialog.dismiss()
                                Toast.makeText(activityRef, it.message, Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(activityRef, it.message, Toast.LENGTH_SHORT).show()
                    }
            }

            rejectBtn.setOnClickListener {
                realtimeDb.reference.child("requests/${auth.uid.toString()}/${request.uid}")
                    .removeValue()
            }
        }
    }
}