package com.acash.fitmate.activities

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.acash.fitmate.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    fun setFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}

fun Context.createProgressDialog(message: String, isCancelable:Boolean): ProgressDialog {
    return ProgressDialog(this).apply{
        setMessage(message)
        setCancelable(isCancelable)
        setCanceledOnTouchOutside(false)
    }
}