package com.yoga.aplikasimyjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_on_board.*

class OnBoardActivity : AppCompatActivity() {

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")) {
            finishAffinity()

            val intent = Intent(this@OnBoardActivity,
                LoginActivity::class.java)
            startActivity(intent)
        }

        btn_lanjut.setOnClickListener {
            val intent = Intent(this@OnBoardActivity,
                OnBoardDuaActivity::class.java)
            startActivity(intent)
        }

        btn_mulai.setOnClickListener {
            preferences.setValues("onboarding", "1")
            finishAffinity()

            val intent = Intent(this@OnBoardActivity,
                LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
