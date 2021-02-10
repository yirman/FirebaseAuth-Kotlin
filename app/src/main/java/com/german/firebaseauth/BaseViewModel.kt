package com.german.firebaseauth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.german.firebaseauth.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser

abstract class BaseViewModel : ViewModel(){


    protected var authRepo: AuthRepository = AuthRepository();

    fun getCurrentUser(): LiveData<FirebaseUser> {
        return authRepo.currentUserMutableLiveData
    }

    fun requestCurrentUser(){
        authRepo.requestCurrentUser()
    }
}