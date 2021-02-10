package com.german.firebaseauth.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.german.firebaseauth.R
import com.german.firebaseauth.session.SessionActivity
import com.german.firebaseauth.databinding.ActivityMainBinding
import com.german.firebaseauth.utils.Constants.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.sign_in.view.*
import kotlinx.android.synthetic.main.sign_in.view.editTextSigninEmail
import kotlinx.android.synthetic.main.sign_in.view.editTextSigninPassword
import kotlinx.android.synthetic.main.sign_up.view.*

class AuthActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    lateinit var authViewModel: AuthViewModel
    lateinit var googleSignIn: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        binding.root.btnSignin.setOnClickListener {
            authViewModel.signInUserWithEmailPassword(binding.root.editTextSigninEmail.text.toString(), binding.root.editTextSigninPassword.text.toString())
        }

        binding.root.btnSignup.setOnClickListener {
            authViewModel.registerUserWithEmailPassword(binding.root.editTextSignupEmail.text.toString(), binding.root.editTextSignupPassword.text.toString())
        }

        binding.root.googleSignInButton.setOnClickListener {
            val signInIntent = GoogleSignIn.getClient(this, googleSignIn).signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        binding.root.tvSignup.setOnClickListener{
            binding.root.viewFlipper.showNext()
            clearEmailAndPassword()
        }
        binding.root.tvSignin.setOnClickListener{
            binding.root.viewFlipper.showPrevious()
            clearEmailAndPassword()
        }

        authViewModel.getCurrentUser().observe(this, Observer { user ->
            if (user != null){
                val intent = Intent(this, SessionActivity::class.java)
                startActivity(intent)
                finish()
            } else{

                Log.d("MainActivity", "Usuario es nulo")
            }
        })

        authViewModel.requestCurrentUser()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            when {
                googleSignInAccount != null -> {
                    authViewModel.signInUserWithGoogle(googleSignInAccount)
                }
            }
        }

    }
    fun clearEmailAndPassword(){
        binding.root.editTextSigninEmail.setText("")
        binding.root.editTextSignupEmail.setText("")
        binding.root.editTextSignupPassword.setText("")
        binding.root.editTextSigninPassword.setText("")
    }

}