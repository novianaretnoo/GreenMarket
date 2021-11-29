package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.greenmarket.database.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlin.math.sign

class halaman1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman1)

        val intentImageButtonExplore : ImageButton = findViewById(R.id.imgbutton2hlmn1)
        intentImageButtonExplore.setOnClickListener{ViewExplore()}

        val intentImageButtonProfile : ImageButton = findViewById(R.id.imgbutton4hlmn1)
        intentImageButtonProfile.setOnClickListener{ViewProfile()}

        val intentImageButtonPesan : ImageView = findViewById(R.id.letter)
        intentImageButtonPesan.setOnClickListener{ViewPesan()}

        val intentImageButtonNotif : ImageView = findViewById(R.id.bell)
        intentImageButtonNotif.setOnClickListener{ViewNotif()}

        val intentImageButtonCart : ImageButton = findViewById(R.id.button_cart)
        intentImageButtonCart.setOnClickListener {
            startActivity(Intent(this@halaman1, RecyclerView::class.java))
        }
    }

    private fun ViewNotif() {
        val intent = Intent(this, notif::class.java)
        startActivity(intent)
    }

    private fun ViewPesan() {
        val intent = Intent(this, pesan::class.java)
        startActivity(intent)
    }

    private fun ViewProfile() {
        val intent = Intent(this, profile::class.java)
        startActivity(intent)
    }

    private fun ViewExplore() {
        val intent = Intent(this, halaman2::class.java)
        startActivity(intent)
    }
}