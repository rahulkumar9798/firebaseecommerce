package com.example.firebaseecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseecommerce.databinding.ActivityUserOtpGetBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth

class UserOtpGetActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserOtpGetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOtpGetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOtpSubmit.setOnClickListener {
            val vid = intent.getStringExtra("vid")

            val smsCode = binding.edtOtp.text.toString()

            val credential = PhoneAuthProvider.getCredential(vid!!,smsCode)

            Firebase.
            auth
                .signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.d("Success", "Login in successfully...  ${it.user!!.uid}")

                    //sherePref
                    val pref = getSharedPreferences("login", MODE_PRIVATE)
                    pref.edit().putString("uid", "${it.user!!.uid}").apply()

                    startActivity(Intent(this, MainActivity::class.java))


                }.addOnFailureListener {

                }
        }
    }
}