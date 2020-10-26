package com.yoga.aplikasimyjob

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.yoga.aplikasimyjob.Dashboard.recomendasiAdapter
import kotlinx.android.synthetic.main.row_item_recomendasi.view.*


class PencarianActivity : AppCompatActivity() {


    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView
    lateinit var context : Context
    lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<User>()

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
    private fun loadFirebaseData(searchText: String) {

        if(searchText.isEmpty()){

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }else {


            val firebaseSearchQuery = mDatabase.orderByChild("role").startAt(searchText).endAt(
                searchText + "\uf8ff"
            )

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<userS, UsersViewHolder>(

                userS::class.java,
                R.layout.row_item_search,
                UsersViewHolder::class.java,
                firebaseSearchQuery


            ) {
                override fun populateViewHolder(
                    viewHolder: UsersViewHolder,
                    model: userS?,
                    position: Int
                ) {


                    viewHolder.mview.tv_role.setText(model?.role)
                    viewHolder.mview.tx.setText(model?.nama)
                    viewHolder.mview.tv_alamat.setText(model?.alamat)
                    Picasso.with(applicationContext).load(model?.urlportofolio).into(viewHolder.mview.iv_poster_image)

                }

            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }
    // // View Holder Class

    class UsersViewHolder(var mview: View) : RecyclerView.ViewHolder(mview) {

    }

    fun pindah(view: View) {
        startActivity(Intent(this@PencarianActivity, DetailActivity::class.java))
    }
}