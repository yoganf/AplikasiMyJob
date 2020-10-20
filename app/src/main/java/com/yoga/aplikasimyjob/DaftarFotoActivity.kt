package com.yoga.aplikasimyjob

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_daftar_foto.*
import java.util.*
import kotlin.math.log

class DaftarFotoActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var user : User
    lateinit var storage : FirebaseStorage
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var storageReferensi : StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_foto)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")
        user = intent.getParcelableExtra("data")

        tv_hello.text = "Selamat Datang\n"+user.nama

        iv_add.setOnClickListener{
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_upload)
                iv_profil.setImageResource(R.drawable.user_pp)
            } else {
 //               Dexter.withActivity(this)
   //                 .withPermission(android.Manifest.permission.CAMERA)
     //               .withListener(this)
       //             .check()
                ImagePicker.with(this)
                    .cropSquare()
                    .compress(1024)
                    .start()
            }
        }

        btn_home.setOnClickListener{
            finishAffinity()

            var goHome = Intent(this@DaftarFotoActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

//                Log.v("User","file uri upload 2"+filePath)

                var ref = storageReferensi.child("images/"+UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@DaftarFotoActivity, "Uploaded", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())

//                            Log.v("User", "url" + it.toString())

                            finishAffinity()
                            var intent = Intent(this@DaftarFotoActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
//            if (filePath != null) {
//                var progressDialog = ProgressDialog(this)
//                progressDialog.setTitle("Uploading...")
//                progressDialog.show()
//
//                var ref = storageReferensi.child("images/"+UUID.randomUUID().toString())
//                ref.putFile(filePath)
//                    .addOnSuccessListener {
//                        progressDialog.dismiss()
//                        Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()
//
//                        ref.downloadUrl.addOnSuccessListener {
//                            FirebaseDatabase.getInstance().getReference("User")
//                            preferences.setValues("url", it.toString())
//                        }
//
//                        finishAffinity()
//                        var goHome = Intent(this@DaftarFotoActivity, HomeActivity::class.java)
//                        startActivity(goHome)
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@DaftarFotoActivity, "Upload Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                            taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload "+progress.toInt()+" %")
                    }

            } else {

            }
        }
    }

    private fun saveToFirebase(url: String) {

        mFirebaseDatabase.child(user.username!!).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValues("nama", user.nama.toString())
                preferences.setValues("user", user.username.toString())
                preferences.setValues("url", user.url.toString())
                preferences.setValues("email", user.email.toString())
                preferences.setValues("status", "1")

                finishAffinity()
                val intent = Intent (this@DaftarFotoActivity, HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
//                takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .start()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Tidak bisa menambahkan Foto Profil", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesa? Klik tombol Lewati saja", Toast.LENGTH_LONG).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//              filePath = data.getData()!!
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(iv_profile)
//
//            btn_save.visibility = View.VISIBLE
//            iv_add.setImageResource(R.drawable.ic_btn_delete)
//        }
//    }
 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     super.onActivityResult(requestCode, resultCode, data)
     if (resultCode == Activity.RESULT_OK) {
         //Image Uri will not be null for RESULT_OK'
         statusAdd = true
         filePath = data?.data!!

         filePath = data.getData()!!

               Glide.with(this)
                   .load(filePath)
                   .apply(RequestOptions.circleCropTransform())
                   .into(iv_profil)

                    Log.v("User", "file uri upload"+filePath )

         btn_save.visibility = View.VISIBLE
         iv_add.setImageResource(R.drawable.ic_btn_delete)

     } else if (resultCode == ImagePicker.RESULT_ERROR) {
         Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
     } else {
         Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
     }
 }
}
