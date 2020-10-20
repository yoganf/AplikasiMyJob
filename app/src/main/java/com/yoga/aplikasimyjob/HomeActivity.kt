package com.yoga.aplikasimyjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.yoga.aplikasimyjob.Dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentProfil = ProfilFragment()
        val fragmentpencarian = PencarianFragment()
        val fragmentHome = DashboardFragment()

        setFragment(fragmentHome)
        iv_menu1.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(iv_menu1, R.drawable.home_icon)
            changeIcon(iv_menu2, R.drawable.search_icon)
            changeIcon(iv_menu3, R.drawable.profil_icon)
        }

        iv_menu2.setOnClickListener {
            setFragment(fragmentpencarian)

            changeIcon(iv_menu1, R.drawable.home_icon)
            changeIcon(iv_menu2, R.drawable.search_icon)
            changeIcon(iv_menu3, R.drawable.profil_icon)
        }

        iv_menu3.setOnClickListener {
            setFragment(fragmentProfil)

            changeIcon(iv_menu1, R.drawable.home_icon)
            changeIcon(iv_menu2, R.drawable.search_icon)
            changeIcon(iv_menu3, R.drawable.profil_icon)
        }}

    protected fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }
    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}