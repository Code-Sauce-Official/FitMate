package com.acash.fitmate.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.acash.fitmate.R
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.activities.createProgressDialog
import com.acash.fitmate.models.Community
import com.acash.fitmate.models.Form
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fill_form.*

class FillFormFragment : Fragment() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database by lazy {
        FirebaseFirestore.getInstance()
    }

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item_dropdown_menu,
            Community.getCommunitiesName()
        )

        categoryDropDown.setAdapter(categoryAdapter)

        categoryDropDown.addTextChangedListener {
            categoryInput.isErrorEnabled = false
        }

        motiveEt.addTextChangedListener {
            motiveInput.isErrorEnabled = false
        }

        createPostBtn.setOnClickListener {
            if (noErrors()) {
                uploadForm()
            }
        }
    }

    private fun uploadForm() {
        (activity as MainActivity).currentUserInfo?.let { user ->
            progressDialog = requireContext().createProgressDialog("Saving Data, Please wait...", false)
            val formId = database.collection("Forms").document().id
            var isGenderSpecific = false

            if((requireActivity().findViewById<RadioButton>(genderPrefRadioGroup.checkedRadioButtonId)).text.toString()=="Same Gender"){
                isGenderSpecific = true
            }

            val form = Form(
                formId,
                auth.uid.toString(),
                categoryDropDown.text.toString(),
                user.state,
                user.name,
                user.gender,
                isGenderSpecific,
                user.yearOfBirth,
                motiveEt.text.toString()
            )

            progressDialog.show()
            database.collection("Forms").document(formId).set(form)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Post added successfully", Toast.LENGTH_SHORT)
                        .show()
                    (activity as MainActivity).onBackPressed()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun noErrors():Boolean{
        var noErrors = true

        if(categoryDropDown.text.isNullOrEmpty()){
            categoryInput.error = "Please select a category"
            noErrors = false
        }

        if(motiveEt.text.isNullOrEmpty()){
            motiveInput.error = "Motive cannot be empty"
            noErrors = false
        }

        return if(noErrors){
            if(genderPrefRadioGroup.checkedRadioButtonId==-1){
                Toast.makeText(requireContext(),"Please select your Gender Preference",Toast.LENGTH_SHORT).show()
                false
            }else true
        }else false
    }


}