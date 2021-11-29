package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val splasScreentimeout : Long= 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)


        val gm = AnimationUtils.loadAnimation(this, R.anim.gm);
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        val green = findViewById(R.id.green) as ImageView
        val progesBar = findViewById(R.id.progressBar) as ProgressBar
        val crtd= findViewById(R.id.crtd) as TextView
        val versi = findViewById(R.id.versi) as TextView

        green.startAnimation(gm)
        progesBar.startAnimation(btt)
        crtd.startAnimation(btt)
        versi.startAnimation(btt)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, splasScreentimeout)


    }
}