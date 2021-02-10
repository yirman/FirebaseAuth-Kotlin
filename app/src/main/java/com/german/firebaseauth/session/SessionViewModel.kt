package com.german.firebaseauth.session

import com.german.firebaseauth.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class SessionViewModel : BaseViewModel() {

    fun logoutUserWithEmailPassword(){
        authRepo.logoutUserWithEmailPassword()
    }
    fun logoutUserWithGoogle(googleSignInClient: GoogleSignInClient){
        authRepo.logoutUserWithGoogle(googleSignInClient)
    }
}