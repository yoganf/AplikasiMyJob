package com.yoga.aplikasimyjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.iv_poster
import kotlinx.android.synthetic.main.activity_detail.tv_nama
import kotlinx.android.synthetic.main.activity_detail.tv_tarif

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btn_back2.setOnClickListener{
            var intent = Intent(this@DetailActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        val data = intent.getParcelableExtra<User>("data")
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
            .child(data.judul.toString())

        tv_nama.text = data.nama
        tv_tarif.text = data.username

        Glide.with(this)
            .load(data.url)
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profil)

        tv_judul2.text = data.judul
        tv_role.text = data.role
        tv_tarif.text = data.tarif
        tv_alamat.text = data.alamat
        tv_nomertelfon.text=data.notlp
        tv_deskripsi.text=data.deskripsi

        Glide.with(this)
            .load(data.urlportofolio)
            .into(iv_poster)

    }
}
