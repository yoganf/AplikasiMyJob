package com.yoga.aplikasimyjob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this@MainActivity, OnBoardActivity::class.java)
            startActivity(intent)
            finish()

        }, 4000)
    }
}