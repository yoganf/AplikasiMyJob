package com.yoga.aplikasimyjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.android.synthetic.main.activity_daftar.btn_back
import kotlinx.android.synthetic.main.activity_daftar.btn_mulai
import kotlinx.android.synthetic.main.activity_edit_portofolio.*


class EditPortofolioActivity : AppCompatActivity() {

    lateinit var sjudul:String
    lateinit var snomertelfon:String
    lateinit var starif:String
    lateinit var sdeskripsi:String
    lateinit var spilihkategoripekerjaan : String
    lateinit var spilihkota : String

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_portofolio)

//        val pilihpekerjaan = resources.getStringArray(R.array.pilih_kategori)
//
//        val spinner = findViewById<Spinner>(R.id.spinnerPekerjaan)
//        if (spinner != null) {
//            val adapter = ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item, pilihpekerjaan
//            )
//            spinner.adapter = adapter
//        }
//
//        spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View, position: Int, id: Long)
//            {
//                Toast.makeText(
//                    this@EditPortofolioActivity,
//                    getString(R.string.selected_item) + " " +
//                            "" + pilihpekerjaan[position], Toast.LENGTH_SHORT
//                ).show()
//            }
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//        }
//
//        val pilihkota = resources.getStringArray(R.array.pilih_kota)
//        val spinner2 = findViewById<Spinner>(R.id.spinnerKota)
//
//        if (spinner2 != null) {
//            val adapter = ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item, pilihkota
//            )
//            spinner2.adapter = adapter
//        }
//        spinner2.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View, position: Int, id: Long
//            ) {
//                Toast.makeText(
//                    this@EditPortofolioActivity,
//                    getString(R.string.selected_item2) + " " +
//                            "" + pilihkota[position], Toast.LENGTH_SHORT
//                ).show()
//            }
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//        }
//
//        btn_back.setOnClickListener {
//            finishAffinity()
//
//            val intent = Intent(this@EditPortofolioActivity,
//                ProfilFragment::class.java)
//            startActivity(intent)
//        }
//
//        mFirebaseInstance = FirebaseDatabase.getInstance()
//        mDatabase = FirebaseDatabase.getInstance().getReference()
//        mFirebaseDatabase = mFirebaseInstance.getReference("User")
//
//        preferences = Preferences(this)
//
//        btn_mulai.setOnClickListener {
//
//            sjudul = et_judul.text.toString()
//            snomertelfon = et_nmr_tlp.text.toString()
//            starif = et_tarifjasa.text.toString()
//            sdeskripsi = et_deskripsi.text.toString()
//            spilihkategoripekerjaan = spinnerPekerjaan.selectedItem.toString()
//            spilihkota = spinnerKota.selectedItem.toString()
//
//            if (sjudul.equals("")) {
//                et_judul.error = "Silahkan isi judul jasa yang ditawarkan"
//                et_judul.requestFocus()
//            } else if (snomertelfon.equals("")) {
//                et_nmr_tlp.error = "Silahkan isi nomer telfon anda"
//                et_nmr_tlp.requestFocus()
//            } else if (starif.equals("")) {
//                et_tarifjasa.error = "Silahkan isi tarif jasa anda"
//                et_tarifjasa.requestFocus()
//            } else if (sdeskripsi.equals("")) {
//                et_deskripsi.error = "Silahkan isi deskripsi jasa yang ditawarkan"
//                et_deskripsi.requestFocus()
//            } else {
//                saveUser(sjudul, snomertelfon, starif, sdeskripsi,spilihkategoripekerjaan,spilihkota)
//
//            }
//        }
//    }
//    private fun saveUser(sjudul: String, snomertelfon: String, starif: String, sdeskripsi: String,
//                         spilihkategoripekerjaan: String, spilihkota : String) {
//
//        val user = User()
//        user.judul = sjudul
//        user.notlp = snomertelfon
//        user.tarif = starif
//        user.deskripsi = sdeskripsi
//        user.alamat = spilihkota
//        user.role = spilihkategoripekerjaan
//
//        if (snomertelfon != null) {
//            checkingNomerTelfon(snomertelfon, user)
//
//        }
//    }
//    private fun checkingNomerTelfon(inomer: String, data: User) {
//        mFirebaseDatabase.child(inomer).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                val user = dataSnapshot.getValue(User::class.java)
//                if (user == null) {
//                    mFirebaseDatabase.child(inomer).setValue(data)
//
//                    preferences.setValues("judul", data.judul.toString())
//                    preferences.setValues("tarif", data.tarif.toString())
//                    preferences.setValues("notlp", data.notlp.toString())
//                    preferences.setValues("deskripsi", data.deskripsi.toString())
//                    preferences.setValues("role",data.role.toString())
//                    preferences.setValues("alamat",data.alamat.toString())
//                    preferences.setValues("status", "1")
//
//                    val intent = Intent(this@EditPortofolioActivity,
//                        UploadFotoPortofolioActivity::class.java).putExtra("data", data)
//                    startActivity(intent)
//
//                } else {
//                    Toast.makeText(this@EditPortofolioActivity, "Nomer telfon sudah digunakan", Toast.LENGTH_LONG).show()
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@EditPortofolioActivity, ""+error.message, Toast.LENGTH_LONG).show()
//            }
//        })
    }
}