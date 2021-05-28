package com.acash.fitmate.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.acash.fitmate.R
import kotlinx.android.synthetic.main.fragment_email.*

class EmailFragment : Fragment() {
    private lateinit var spanNewUser:SpannableString
    private lateinit var spanOldUser:SpannableString

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSpannableStrings()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvSpannable.apply {
            movementMethod = LinkMovementMethod()
            text = spanNewUser
        }

        authBtn.setOnClickListener {
            val childFragment = childFragmentManager.fragments[0]

            if(childFragment is SignInFragment){
                childFragment.onClickSignIn()
            }
            else if(childFragment is SignUpFragment){
                childFragment.onClickSignUp()
            }
        }
    }

    fun setChildFragment(fragment:Fragment,span:SpannableString){
        childFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.emailContainer, fragment)
            .commit()

        if(fragment is SignUpFragment){
            authBtn.text = getString(R.string.sign_up)
        }else{
            authBtn.text = getString(R.string.sign_in)
        }

        tvSpannable.apply{
            movementMethod = LinkMovementMethod()
            text = span
        }
    }

    private fun setSpannableStrings() {

        spanNewUser = SpannableString(getString(R.string.new_user_sign_up))

        val clickableSpanNewUser = object : ClickableSpan() {

            override fun onClick(widget: View) {
                setChildFragment(SignUpFragment(),spanOldUser)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(requireContext(),R.color.mustard)
            }
        }

        spanNewUser.setSpan(
            clickableSpanNewUser,
            spanNewUser.length - 7,
            spanNewUser.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        spanOldUser = SpannableString(getString(R.string.already_have_an_account_sign_in))

        val clickableSpanOldUser = object : ClickableSpan(){

            override fun onClick(widget: View) {
                setChildFragment(SignInFragment(),spanNewUser)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(requireContext(), R.color.mustard)
            }
        }

        spanOldUser.setSpan(
            clickableSpanOldUser,
            spanOldUser.length-7,
            spanOldUser.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    }
}