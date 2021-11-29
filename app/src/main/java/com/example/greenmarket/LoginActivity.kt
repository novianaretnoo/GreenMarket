package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.greenmarket.databinding.LoginBinding
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.internal.InternalTokenProvider

class LoginActivity: AppCompatActivity() {

    private lateinit var binding : LoginBinding

    // Sign Up
    private lateinit var authSignUp : FirebaseAuth

    // Log In
    private lateinit var authLogin : FirebaseAuth

    // Google
    private lateinit var authGoogle : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    companion object{
        private const val RC_SIGN_IN = 0
    }

    // Facebook
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Sign Up
        authSignUp = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(email.isEmpty()){
                binding.etEmail.error = "Email harus diisi"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = "Email tidak diisi"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }else if(password.isEmpty() || password.length < 7){
                binding.etPassword.error = "Password minimal harus 8 karakter"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)
        }

        // Log In
        authLogin = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(email.isEmpty()){
                binding.etEmail.error = "Email harus diisi"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = "Email tidak diisi"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }else if(password.isEmpty() || password.length < 7){
                binding.etPassword.error = "Password minimal harus 8 karakter"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        // Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        authGoogle = FirebaseAuth.getInstance()
        binding.btnGoogle.setOnClickListener {
            signIn()
        }

        // Log Out Google
        binding.tvLogoutGoogle.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Berhasil Log Out", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Facebook
        callbackManager = CallbackManager.Factory.create()

        binding.btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this@LoginActivity, listOf("public_profile", "email"))
        }

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                val intent = Intent(this@LoginActivity, halaman1::class.java)
                startActivity(intent)
                finish()
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Log In dibatalkan", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_SHORT).show()
            }
        })

        // Logout Facebook
        binding.tvLogoutFacebook.setOnClickListener {
            LoginManager.getInstance().logOut()
        }
    }

    // Function Sign Up
    private fun registerUser(email: String, password: String) {
        authSignUp.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Intent(this@LoginActivity, halaman1::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Function Log In
    private fun loginUser(email: String, password: String) {
        authLogin.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Intent(this@LoginActivity, halaman1::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Function Google
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w("LoginActivity", "Google sign in failed", e)
                }
            }else{
                Log.w("LoginActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authGoogle.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginActivity", "signInWithCredential:success")
                    val intent = Intent(this@LoginActivity, halaman1::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }

    // Sign Up & Log in Check
    override fun onStart() {
        super.onStart()
        if(authSignUp.currentUser != null) {
            Intent(this@LoginActivity, halaman1::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                finish()
            }
        }else if(authLogin.currentUser != null) {
            Intent(this@LoginActivity, halaman1::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                finish()
            }
        }

        // Google Check
        val user = authGoogle.currentUser
        Handler()
            if(user != null){
                val dashboardIntent = Intent(this@LoginActivity, halaman1::class.java)
                startActivity(dashboardIntent)
                finish()
            }

        // Facebook Check
//        val person = AccessToken.getCurrentAccessToken()
//        Handler()
//            if(person != null && !person.isExpired){
//                val dashboardIntent = Intent(this@LoginActivity, halaman1::class.java)
//                startActivity(dashboardIntent)
//                finish()
//            }
    }
}