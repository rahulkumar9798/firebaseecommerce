package com.example.firebaseecommerce


import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebaseecommerce.databinding.ActivitySignUpBinding
import com.example.firebaseecommerce.databinding.UserImgUploadBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    val arrIds = ArrayList<String>()

    lateinit var mAuth : FirebaseAuth
    var dateFormate = SimpleDateFormat("dd/MM/YYYY")
    var dob = ""
    var gender = ""
    var profilepic = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth
        val iCam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val iGall = Intent(Intent.ACTION_GET_CONTENT)
        iGall.type = "image/*"

        val camLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val imgBitmap = it.data!!.extras!!.get("data") as Bitmap
                    binding.imageView.setImageBitmap(imgBitmap)

                    val baos = ByteArrayOutputStream()

                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val imgBytes = baos.toByteArray()

                    val storageRef = Firebase.storage
                    val timeStamp = Calendar.getInstance().timeInMillis
                    val imgRef =
                        storageRef.reference.child("app_images/profile_pic/IMG_$timeStamp.png")

                    imgRef.putBytes(imgBytes)
                        .addOnSuccessListener {
                            Log.d("Success", "${it.metadata}")

                            //image downloaded det url start----------
                            imgRef.downloadUrl.addOnSuccessListener {
                                Log.d("ImgUrl", "$it")

                                profilepic = "$it"
                            }.addOnFailureListener {

                            }


                        }.addOnFailureListener {
                            Log.d("Failure", "${it.message}")
                            it.printStackTrace()
                        }

                }
            }

        val galLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val imgBitmap = MediaStore.Images.Media.getBitmap(
                        applicationContext.contentResolver,
                        it.data!!.data
                    )
                    binding.imageView.setImageBitmap(imgBitmap)

                    val baos = ByteArrayOutputStream()

                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val imgBytes = baos.toByteArray()

                    val storageRef = Firebase.storage
                    val timeStamp = Calendar.getInstance().timeInMillis
                    val imgRef =
                        storageRef.reference.child("app_images/profile_pic/IMG_$timeStamp.png")

                    imgRef.putBytes(imgBytes)
                        .addOnSuccessListener {
                            Log.d("Success", "${it.metadata}")

                            //image downloaded det url start----------
                            imgRef.downloadUrl.addOnSuccessListener {
                                Log.d("ImgUrl", "$it")

                                profilepic = "$it"

                            }.addOnFailureListener {

                            }
                        }.addOnFailureListener {
                            Log.d("Failure", "${it.message}")
                            it.printStackTrace()
                        }

                }
            }

        binding.btnUpload.setOnClickListener {
            val dialogAdd= Dialog(this)
            val dialogBinding = UserImgUploadBinding.inflate(layoutInflater)
            dialogAdd.setContentView(dialogBinding.root)

            dialogBinding.camraUpload.setOnClickListener {
                camLauncher.launch(iCam)
                dialogAdd.dismiss()
            }

            dialogBinding.galleryupload.setOnClickListener {
                galLauncher.launch(iGall)
                dialogAdd.dismiss()
            }
            dialogAdd.show()
        }

        binding.selectDate.setOnClickListener {

            val getDate = Calendar.getInstance()

            val datepicker = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)

                val date = dateFormate.format(selectDate.time)
                Toast.makeText(this, "Date : " + date, Toast.LENGTH_SHORT).show()
                binding.selectDate.text = date
                // for select date
                dob = date

            },getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH)   )
            datepicker.show()
        }

        //Spinner State Select Start
        arrIds .apply {

            add("Select State")
            add("West Bengal")
            add("Rajasthan")
            add("Orissa")
            add("Madhya Pradesh")
        }

        val mAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrIds )

        binding.spinnerID.adapter = mAdapter

        //Spinner State Select Close

        binding.radioGrpGender.setOnCheckedChangeListener { radioGroup, i ->
            gender = if (R.id.radio_male == i){
                "Male"
            }else{
                "Female"
            }
        }


        binding.btnSubmit.setOnClickListener {
            val name = binding.edtName.text.toString()
            val phnNo = binding.edtMob.text.toString()
            val email = binding.edtEmail.text.toString()
            val  pass = binding.edtPassword.text.toString()

            val firestore = Firebase.firestore


            val userMap = mapOf<String, Any>(
                "profilePic" to profilepic,
                "name" to name,
                "phnNo" to phnNo,
                "email" to email,
                "pass" to pass,
                "dob" to dob,
                "gender" to gender


            )


            mAuth.createUserWithEmailAndPassword(email, pass)


                .addOnSuccessListener {

                    Log.d("uid", "${it.user!!.uid}")

                    firestore.collection("User")
                        .document("${it.user!!.uid}")
                        .set(userMap)
                        .addOnSuccessListener {

                            Log.d("seccess", "UserAdded ${it}!!")
                            startActivity(Intent(this@SignUpActivity, UserLoginActivity::class.java))
                        }

                        .addOnFailureListener {
                            Log.d("User Add Failure", "UserAdded ${it.message}!!")
                            it.printStackTrace()
                        }

                }.addOnFailureListener {
                    Log.d("Create User Failure", "UserAdded ${it.message}!!")
                    it.printStackTrace()
                }



        }

    }
}