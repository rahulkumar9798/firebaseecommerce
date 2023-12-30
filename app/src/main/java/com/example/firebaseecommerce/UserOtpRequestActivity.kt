package com.example.firebaseecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseecommerce.databinding.ActivityUserOtpRequestBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit

class UserOtpRequestActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserOtpRequestBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOtpRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        binding.btnRequestOtp.setOnClickListener {

            val mobNum = binding.edtphnNumber.text.toString()

            val option = PhoneAuthOptions
                .newBuilder(firebaseAuth)
                .setPhoneNumber(mobNum) //phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object  : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Log.d("Verification Successful", "${p0.smsCode}")
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Log.d("Verification Failed", "${p0.message}")
                        p0.printStackTrace()
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(p0, p1)
                        Log.d("Code Send", "${p0}")
                        //navigate to OTP Screen

                        startActivity(Intent(this@UserOtpRequestActivity, UserOtpGetActivity::class.java).putExtra("vid", "$p0"))

                    }

                    override fun onCodeAutoRetrievalTimeOut(p0: String) {
                        super.onCodeAutoRetrievalTimeOut(p0)
                    }

                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(option)




            startActivity(Intent(this, UserOtpGetActivity::class.java))
        }

//        binding.btnRequestOtp.setOnClickListener {
//            startActivity(Intent(this, UserOtpGetActivity::class.java))
//        }
    }
}