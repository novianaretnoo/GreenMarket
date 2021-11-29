package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        val dalem = AnimationUtils.loadAnimation(this, R.anim.dalem);

        val jek = findViewById(R.id.jek) as ImageView
        val gm = findViewById(R.id.gm2) as ImageView
        val btn = findViewById(R.id.button) as Button

        jek.startAnimation(ttb)
        gm.startAnimation(dalem)
        btn.startAnimation(dalem)



        val intentButton : Button = findViewById(R.id.button)
        intentButton.setOnClickListener{viewget()}


    }

    private fun viewget() {
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
    }
}