package com.yoga.aplikasimyjob

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.fragment_profil.tv_nama
import kotlinx.android.synthetic.main.fragment_profil.tv_user
import kotlinx.android.synthetic.main.fragment_profil.tv_email


class ProfilFragment : Fragment() {

    lateinit var sNama: String
    lateinit var sUser: String
    lateinit var sEmail: String
    lateinit var seditportofolio: String
    lateinit var user : User


    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mFirebaseDatabase: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        bt_edit_portofolio.setOnClickListener{

            val intent = Intent(this@ProfilFragment.context, EditPortofolioActivity::class.java)
            startActivity(intent)
        }
        bt_edit_profil.setOnClickListener {

            sUser = et_username.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()

            updateData()
        }

        btn_logout.setOnClickListener {

                preferences.clear()

                Toast.makeText(context, "keluar", Toast.LENGTH_LONG).show()

                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }


            preferences = Preferences(activity!!.applicationContext)
            mDatabase = FirebaseDatabase.getInstance().getReference("User")

            tv_nama.setText(preferences.getValues("nama"))
            tv_user.setText(preferences.getValues("username"))
            tv_email.setText(preferences.getValues("email"))

            Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profil3)
        }

    fun updateData(){
        var updateEditTextString = tv_nama.text.toString()

        var map = mutableMapOf<String,Any>()
        map["nama"] = updateEditTextString


        FirebaseDatabase.getInstance().reference
            .child("User")
            .child("yoga13")

            .updateChildren(map)
        var intent = Intent(this@ProfilFragment.context, HomeActivity::class.java)
        startActivity(intent)

    }
}







