package com.yoga.aplikasimyjob

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pencarian.*
import kotlinx.android.synthetic.main.item_pencarian.view.*
import kotlinx.android.synthetic.main.row_item_recomendasi.view.tv_alamat
import kotlinx.android.synthetic.main.row_item_recomendasi.view.tv_role


class PencarianActivity : AppCompatActivity() {


    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView
    lateinit var context : Context
    lateinit var mDatabase : DatabaseReference

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<userS, UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pencarian)

        mSearchText =findViewById(R.id.SearchText)
        mRecyclerView = findViewById(R.id.result_list)


        mDatabase = FirebaseDatabase.getInstance().getReference("User")


        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        mSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = mSearchText.getText().toString().trim()

                loadFirebaseData(searchText)
            }
        })
    }

    private fun copyTextToClipboard() {
        val textToCopy = tv_nomer.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Nomer Telfon Tersalin, Silahkan Mulai Menghubungi", Toast.LENGTH_LONG).show()

    }

    private fun loadFirebaseData(searchText: String) {

        if(searchText.isEmpty()){

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }else {


            val firebaseSearchQuery = mDatabase.orderByChild("judul").startAt(searchText).endAt(
                searchText + "\uf8ff"
            )

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<userS, UsersViewHolder>(

                userS::class.java,
                R.layout.item_pencarian,
                UsersViewHolder::class.java,
                firebaseSearchQuery


            ) {
                override fun populateViewHolder(
                    viewHolder: UsersViewHolder,
                    model: userS?,
                    position: Int
                ) {

                    viewHolder.mview.tv_judul.setText(model?.judul)
                    viewHolder.mview.tv_role.setText(model?.role)
                    viewHolder.mview.tv_nama.setText(model?.nama)
                    viewHolder.mview.tv_alamat.setText(model?.alamat)
                    viewHolder.mview.tv_tarif.setText(model?.tarif)
                    viewHolder.mview.tv_deskripsi.setText(model?.deskripsi)
                    viewHolder.mview.tv_nomer.setText(model?.notlp)

                    Picasso.with(applicationContext).load(model?.urlportofolio).into(viewHolder.mview.iv_poster)
                    Picasso.with(applicationContext).load(model?.url).into(viewHolder.mview.iv_profil)


                }

            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }
    // // View Holder Class

    class UsersViewHolder(var mview: View) : RecyclerView.ViewHolder(mview) {

    }

    fun copy(view: View) {
        copyTextToClipboard()}
}