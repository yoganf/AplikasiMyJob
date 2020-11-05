package com.yoga.aplikasimyjob

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_daftar_foto.*
import java.util.*

class DaftarActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String
    lateinit var sjudul: String
    lateinit var snomertelfon: String
    lateinit var starif: String
    lateinit var sdeskripsi: String
    lateinit var spilihkategoripekerjaan: String
    lateinit var spilihkota: String
//    lateinit var surlportofolio : String


    lateinit var storageReferensi: StorageReference
    lateinit var user: User
    lateinit var storage: FirebaseStorage
    var statusAdd: Boolean = false

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@DaftarActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
        }
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btn_portofolio.setOnClickListener {

            et_judul.visibility = View.VISIBLE
            et_nomer.visibility = View.VISIBLE
            et_tarif.visibility = View.VISIBLE
            et_deskripsi.visibility = View.VISIBLE
            spinnerKota2.visibility = View.VISIBLE
            spinnerPekerjaan2.visibility = View.VISIBLE
            textView36.visibility = View.VISIBLE
            textView37.visibility = View.VISIBLE
            btn_portofolio.visibility = View.INVISIBLE

        }

        val pilihpekerjaan = resources.getStringArray(R.array.pilih_kategori)

        val spinner = findViewById<Spinner>(R.id.spinnerPekerjaan2)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, pilihpekerjaan
            )
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@DaftarActivity,
                    getString(R.string.selected_item) + " " +
                            "" + pilihpekerjaan[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        val pilihkota = resources.getStringArray(R.array.pilih_kota)
        val spinner2 = findViewById<Spinner>(R.id.spinnerKota2)

        if (spinner2 != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, pilihkota
            )
            spinner2.adapter = adapter
        }
        spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@DaftarActivity,
                    getString(R.string.selected_item2) + " " +
                            "" + pilihkota[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        btn_mulai.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()
            sjudul = et_judul.text.toString()
            snomertelfon = et_nomer.text.toString()
            starif = et_tarif.text.toString()
            sdeskripsi = et_deskripsi.text.toString()
            spilihkategoripekerjaan = spinnerPekerjaan2.selectedItem.toString()
            spilihkota = spinnerKota2.selectedItem.toString()

            if (sUsername.equals("")) {
                et_username.error = "Silahkan isi Username"
                et_username.requestFocus()
            } else if (sPassword.equals("")) {
                et_password.error = "Silahkan isi Password"
                et_password.requestFocus()
            } else if (sNama.equals("")) {
                et_nama.error = "Silahkan isi Nama"
                et_nama.requestFocus()
            } else if (sEmail.equals("")) {
                et_email.error = "Silahkan isi Email"
                et_email.requestFocus()
            } else {
                saveUser(
                    sUsername, sPassword, sNama, sEmail, sjudul, snomertelfon, starif, sdeskripsi,
                    spilihkategoripekerjaan, spilihkota,
                )

            }

        }
    }

    private fun saveUser(
        sUsername: String, sPassword: String, sNama: String, sEmail: String,
        sjudul: String, snomertelfon: String, starif: String, sdeskripsi: String,
        spilihkategoripekerjaan: String, spilihkota: String,
    ) {

        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.nama = sNama
        user.password = sPassword
        user.judul = sjudul
        user.notlp = snomertelfon
        user.tarif = starif
        user.deskripsi = sdeskripsi
        user.alamat = spilihkota
        user.role = spilihkategoripekerjaan

        if (sUsername != null) {
            checkingUsername(sUsername, user)

        }

    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseDatabase.child(iUsername)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val user = dataSnapshot.getValue(User::class.java)
                    if (user == null) {
                        mFirebaseDatabase.child(iUsername).setValue(data)

                        preferences.setValues("nama", data.nama.toString())
                        preferences.setValues("username", data.username.toString())
                        preferences.setValues("url", "")
                        preferences.setValues("email", data.email.toString())
                        preferences.setValues("judul", data.judul.toString())
                        preferences.setValues("tarif", data.tarif.toString())
                        preferences.setValues("notlp", data.notlp.toString())
                        preferences.setValues("deskripsi", data.deskripsi.toString())
                        preferences.setValues("role", data.role.toString())
                        preferences.setValues("alamat", data.alamat.toString())
                        preferences.setValues("status", "1")

                        val intent = Intent(this@DaftarActivity, DaftarFotoActivity::class.java)
                            .putExtra("data", data)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            this@DaftarActivity,
                            "User sudah digunakan",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@DaftarActivity,
                        "" + error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}