package com.german.firebaseauth.auth

import com.german.firebaseauth.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class AuthViewModel : BaseViewModel() {


    fun registerUserWithEmailPassword(email: String, password: String){
        authRepo.registerUserWithEmailPassword(email, password)
    }
    fun signInUserWithEmailPassword(email: String, password: String){
        authRepo.signInUserWithEmailPassword(email, password)
    }
    fun signInUserWithGoogle(googleSignInAccount: GoogleSignInAccount){
        authRepo.signInUserWithGoogle(googleSignInAccount)
    }
}