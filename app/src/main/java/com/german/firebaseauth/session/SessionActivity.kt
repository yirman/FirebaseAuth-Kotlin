package com.german.firebaseauth.session

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.german.firebaseauth.R
import com.german.firebaseauth.auth.AuthActivity
import com.german.firebaseauth.databinding.ActivitySessionBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SessionActivity : AppCompatActivity() {

    lateinit var binding: ActivitySessionBinding

    lateinit var sessionViewModel: SessionViewModel
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = getString(R.string.app_name)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        sessionViewModel = ViewModelProvider(this).get(SessionViewModel::class.java)
        sessionViewModel.getCurrentUser().observe(this, Observer { user ->
            if (user != null){
                binding.tvMessage.setText("Sesión activa con " + user.email)
            }
        })

        sessionViewModel.requestCurrentUser()

        binding.btnSignout.setOnClickListener{
            sessionViewModel.logoutUserWithEmailPassword()
            sessionViewModel.logoutUserWithGoogle(googleSignInClient)
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}