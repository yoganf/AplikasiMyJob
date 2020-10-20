package com.yoga.aplikasimyjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_on_board_dua.*

class OnBoardDuaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board_dua)

        btn_mulai.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@OnBoardDuaActivity,
                LoginActivity::class.java)
            startActivity(intent)
        }
    }
}