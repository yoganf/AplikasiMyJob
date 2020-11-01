package com.yoga.aplikasimyjob

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
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
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.android.synthetic.main.activity_daftar.btn_back
import kotlinx.android.synthetic.main.activity_daftar.btn_mulai
import kotlinx.android.synthetic.main.activity_daftar_foto.*
import kotlinx.android.synthetic.main.activity_edit_portofolio.*
import kotlinx.android.synthetic.main.activity_edit_portofolio.btn_save
import kotlinx.android.synthetic.main.activity_edit_portofolio.iv_add
import kotlinx.android.synthetic.main.activity_edit_portofolio.tv_hello
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*


class EditPortofolioActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var url: String
    lateinit var user : User
    lateinit var storage : FirebaseStorage
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var storageReferensi : StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_portofolio)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")
        user = intent.getParcelableExtra ("data")

        tv_hello.text = "Selamat Datang\n"+user.nama
//        tv_hello.setText(preferences.getValues("nama"))

        iv_add.setOnClickListener{
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_upload)
                iv_poster.setImageResource(R.drawable.user_pp)
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
//
//        btn_home.setOnClickListener{
//            finishAffinity()
//
//            var goHome = Intent(this@DaftarFotoActivity, HomeActivity::class.java)
//            startActivity(goHome)
//        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReferensi.child("images/"+UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@EditPortofolioActivity, "Uploaded", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
                            finishAffinity()
                            var intent = Intent(this@EditPortofolioActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@EditPortofolioActivity, "Upload Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                            taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload "+progress.toInt()+" %")
                    }

            } else {

            }
        }
    }

    private fun saveToFirebase(urlportofolio: String) {

        mFirebaseDatabase.child(user.username!!).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user.urlportofolio = urlportofolio
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValues("nama", user.nama.toString())
                preferences.setValues("user", user.username.toString())
                preferences.setValues("url", user.url.toString())
                preferences.setValues("urlportofolio", user.urlportofolio.toString())
                preferences.setValues("email", user.email.toString())
                preferences.setValues("judul", user.judul.toString())
                preferences.setValues("tarif", user.tarif.toString())
                preferences.setValues("notlp", user.notlp.toString())
                preferences.setValues("deskripsi", user.deskripsi.toString())
                preferences.setValues("role", user.role.toString())
                preferences.setValues("alamat", user.alamat.toString())
                preferences.setValues("status", "1")

                finishAffinity()
                val intent = Intent (this@EditPortofolioActivity, HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun onPermissionGranted(response: PermissionGrantedResponse?) {
        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .start()
    }

    fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Tidak bisa menambahkan Foto Profil", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesa? Klik tombol Lewati saja", Toast.LENGTH_LONG).show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK'
            statusAdd = true
            filePath = data?.data!!

            filePath = data.getData()!!

            Glide.with(this)
                .load(filePath)
                .into(iv_poster)

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