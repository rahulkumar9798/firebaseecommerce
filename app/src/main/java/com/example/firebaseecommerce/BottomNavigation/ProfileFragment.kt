package com.example.firebaseecommerce.BottomNavigation

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.firebaseecommerce.RecyclerProfilePicAdapter
import com.example.firebaseecommerce.UserProfilePicModel
import com.example.firebaseecommerce.databinding.FragmentProfileBinding
import com.example.firebaseecommerce.databinding.UserImgUploadBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.util.Calendar

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val firestore = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)



        val iCam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val iGall = Intent(Intent.ACTION_GET_CONTENT)
        iGall.type = "image/*"

        //Fatch Image In Fire Base and Set Profile Image in User Profile Start.....

        firestore
            .collection("User")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val imgpath = it.get("profilePic").toString()
                Glide.with(requireContext()).load(imgpath).into(binding.imageView)
            }
        //Fatch Image In Fire Base and Set Profile Image in User Profile Close.....

            firestore.collection("User")
                .document(userId)
                .collection("profile_pictures")
                .get()
                .addOnSuccessListener {
                    val arrPropp = ArrayList<UserProfilePicModel>()

                    for (data in it.documents){
                       arrPropp.add(data.toObject(UserProfilePicModel::class.java)!!)
                    }
                    binding.recyclerProImg.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.recyclerProImg.adapter =
                        RecyclerProfilePicAdapter(requireContext(), arrPropp)
                }


        val camLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val imgBitmap = it.data!!.extras!!.get("data") as Bitmap
                    binding.imageView.setImageBitmap(imgBitmap)

                    val baos = ByteArrayOutputStream()

                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val imgBytes = baos.toByteArray()
                    updateProfilePic(imgBytes)

                }
            }


        val galLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val imgBitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        it.data!!.data
                    )
                    binding.imageView.setImageBitmap(imgBitmap)

                    val baos = ByteArrayOutputStream()

                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val imgBytes = baos.toByteArray()

                    updateProfilePic(imgBytes)
                }
            }




        binding.btnUpload.setOnClickListener {
            val dialogAdd= Dialog(requireContext())
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




        // Inflate the layout for this fragment
        return binding.root
    }

    fun updateProfilePic(imgBytes : ByteArray){
        val storageRef = Firebase.storage
        val timeStamp = Calendar.getInstance().timeInMillis
        val imgRef =
            storageRef.reference.child("app_images/profile_pic/IMG_$timeStamp.png")

        imgRef.putBytes(imgBytes)
            .addOnSuccessListener {
                Log.d("Success", "${it.metadata}")

                imgRef.downloadUrl.addOnSuccessListener {
                    Log.d("ImgUrl", "$it")




                    firestore
                        .collection("User")
                        .document(userId)
                        .collection("profile_pictures")
                        .add(mapOf(
                            "imgPath" to "$it"
                        ))






                    firestore
                        .collection("User")
                        .document(userId)
                        .update("profilePic", it.toString())



                }.addOnFailureListener {
                    Log.d("Failure load URL", "${it.message}")
                    it.printStackTrace()
                }


            }.addOnFailureListener {
                Log.d("Failure", "${it.message}")
                it.printStackTrace()
            }


    }


}