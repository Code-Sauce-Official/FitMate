package com.acash.fitmate.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.acash.fitmate.R
import com.acash.fitmate.activities.*
import com.acash.fitmate.models.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfileFragment : Fragment() {

    private val storage by lazy {
        FirebaseStorage.getInstance()
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database by lazy {
        FirebaseFirestore.getInstance()
    }

    private var myCalendar: Calendar = Calendar.getInstance()

    private var downloadUrlDp: String = ""

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var dpUri: Uri

    private lateinit var progressDialog: ProgressDialog

    private val genderArray = arrayOf("Male", "Female", "Other")
    private val stateArrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        dpUri = uri
                        image_view.setImageURI(uri)
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genderAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item_dropdown_menu,
            genderArray
        )

        genderDropDown.setAdapter(genderAdapter)

        val bufferedReader = requireContext().assets.open("states.txt").bufferedReader()
        var line = bufferedReader.readLine()

        while (line != null) {
            val state = line.trim()
            stateArrayList.add(state)
            line = bufferedReader.readLine()
        }

        val stateAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item_dropdown_menu,
            stateArrayList
        )

        stateDropDown.setAdapter(stateAdapter)

        dobEt.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()
            }

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )

            val maxDateCal = Calendar.getInstance()
            maxDateCal.set(Calendar.YEAR, 2003)
            maxDateCal.set(Calendar.MONTH, 11)
            maxDateCal.set(Calendar.DAY_OF_MONTH, 31)

            datePickerDialog.datePicker.maxDate = maxDateCal.timeInMillis
            datePickerDialog.show()
        }

        nameEt.addTextChangedListener {
            nameInput.isErrorEnabled = false
        }

        genderDropDown.addTextChangedListener {
            genderInput.isErrorEnabled = false
        }

        stateDropDown.addTextChangedListener {
            stateInput.isErrorEnabled = false
        }

        dobEt.addTextChangedListener {
            dobInput.isErrorEnabled = false
        }

        uploadPicBtn.setOnClickListener {
            checkPermissionsForImage()
        }

        saveBtn.setOnClickListener {
            if (noErrors()) {
                progressDialog = requireContext().createProgressDialog("Saving Data, Please wait...", false)
                progressDialog.show()
                if(::dpUri.isInitialized)
                    uploadDp()
                else uploadDataToFirestore()
            }
        }

    }

    private fun updateDate() {
        val myFormat = "d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        dobEt.setText(sdf.format(myCalendar.time))
    }

    private fun checkNameErrors(): Boolean {
        if (nameEt.text.isNullOrEmpty()) {
            nameInput.error = "Name cannot be empty!"
            return false
        }
        return true
    }

    private fun checkGenderErrors(): Boolean {
        if (genderDropDown.text.isNullOrEmpty()) {
            genderInput.error = "Gender cannot be empty!"
            return false
        }
        return true
    }

    private fun checkStateErrors(): Boolean {
        if (stateDropDown.text.isNullOrEmpty()) {
            stateInput.error = "State cannot be empty!"
            return false
        }
        return true
    }

    private fun checkDobErrors(): Boolean {
        if (dobEt.text.isNullOrEmpty()) {
            dobInput.error = "D.O.B cannot be empty!"
            return false
        }
        return true
    }

    private fun noErrors(): Boolean {
        var noError = true

        if (!checkNameErrors()) {
            noError = false
        }

        if (!checkGenderErrors()) {
            noError = false
        }

        if (!checkStateErrors()) {
            noError = false
        }

        if (!checkDobErrors()) {
            noError = false
        }

        if (noError) {

            if (!tncCheckBox.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "Accepting Terms and Conditions is mandatory to proceed!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

            return true
        } else return false
    }

    private fun checkPermissionsForImage() {
        if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requireActivity().requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 121)
        }

        if (requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requireActivity().requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 131)
        }

        if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        )
            selectImageFromGallery()
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun uploadDp() {
        val ref = storage.reference.child("uploads/" + auth.uid.toString() + "/Dp")

        val bitmap: Bitmap = if (Build.VERSION.SDK_INT <= 28) {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, dpUri)
        } else {
            val source = ImageDecoder.createSource(requireContext().contentResolver, dpUri)
            ImageDecoder.decodeBitmap(source)
        }

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        val fileInBytes = baos.toByteArray()

        val uploadTask = ref.putBytes(fileInBytes)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUrlDp = task.result.toString()
                uploadDataToFirestore()
            } else {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadDataToFirestore() {
        val user = User(
            auth.uid.toString(),
            nameEt.text.toString(),
            genderDropDown.text.toString(),
            dobEt.text.toString(),
            myCalendar.get(Calendar.YEAR),
            stateDropDown.text.toString(),
            downloadUrlDp
        )

        database.collection("users").document(auth.uid.toString()).set(user)
            .addOnSuccessListener {
                progressDialog.dismiss()
                requireActivity().supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                    .replace(R.id.fragment_container, CommunitiesFragment())
                    .commit()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }

    }

}