package com.yoga.aplikasimyjob

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_profiluser.*
import kotlinx.android.synthetic.main.fragment_profil.*


class ProfilFragment : Fragment() {


    private lateinit var preferences: Preferences
    private lateinit var mDatabase : DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        tv_nama.setText(preferences.getValues("nama"))
        tv_nomor.setText(preferences.getValues("notlp"))
        tv_email.setText(preferences.getValues("email"))

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profil3)

        btn_e_profil.setOnClickListener{
            val intent=(EditDataProfilActivity::class.java)
        }

    }


}