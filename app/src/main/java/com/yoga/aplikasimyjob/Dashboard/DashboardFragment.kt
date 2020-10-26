package com.yoga.aplikasimyjob.Dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.yoga.aplikasimyjob.*
import com.yoga.aplikasimyjob.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase : DatabaseReference

    private var dataList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        tx.setText(preferences.getValues("nama"))
        jenis_pekerjaan.setText(preferences.getValues("role"))

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profil)

        rv_recomendasi.layoutManager = LinearLayoutManager(context!!.applicationContext)
        getData()
    }

    private fun getData()  {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val user = getdataSnapshot.getValue(User::class.java)
                    dataList.add(user!!)
                }
                rv_recomendasi.adapter = recomendasiAdapter(dataList) {
                    val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("data", it)
                    startActivity(intent)
                }
            }
        })
    }
}
