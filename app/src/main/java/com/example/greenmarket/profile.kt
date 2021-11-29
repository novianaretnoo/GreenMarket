package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.greenmarket.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class profile : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentImageButton : ImageButton = findViewById(R.id.imgbutton1profile)
        intentImageButton.setOnClickListener{ViewGoHome()}

        val intentImageButton2 : ImageButton = findViewById(R.id.imgbutton2profile)
        intentImageButton2.setOnClickListener{ViewGoExplore()}

        // Sign Out
        auth = FirebaseAuth.getInstance()
        binding.arrowLogout.setOnClickListener {
            auth.signOut()
            Intent(this@profile, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                finish()
            }
        }
    }

    private fun ViewGoExplore() {
        val intent = Intent(this, halaman2::class.java)
        startActivity(intent)
    }

    private fun ViewGoHome() {
        val intent = Intent(this, halaman1::class.java)
        startActivity(intent)
    }
}