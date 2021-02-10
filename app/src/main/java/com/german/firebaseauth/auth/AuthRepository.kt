package com.german.firebaseauth.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthRepository() {

    private var firebaseAuth: FirebaseAuth = Firebase.auth

    val currentUserMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()

    fun requestCurrentUser() {
        currentUserMutableLiveData.value = firebaseAuth.currentUser
    }

    fun registerUserWithEmailPassword(email: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getOnCompleteListener())
    }

    fun signInUserWithEmailPassword(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getOnCompleteListener())
    }

    fun signInUserWithGoogle(googleSignInAccount: GoogleSignInAccount){
        val credential: AuthCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getOnCompleteListener())
    }

    private fun getOnCompleteListener(): OnCompleteListener<AuthResult> {
        return OnCompleteListener {
            when {
                it.isSuccessful -> {
                    val user = firebaseAuth.currentUser
                    currentUserMutableLiveData.value = user
                }
                else -> {
                    Log.w("UserRepo", "createUserWithEmail:failure", it.exception)
                }
            }
        }
    }

    fun logoutUserWithEmailPassword(){
        firebaseAuth.signOut()
    }

    fun logoutUserWithGoogle(googleSignInClient: GoogleSignInClient){
        googleSignInClient.signOut()
    }

}