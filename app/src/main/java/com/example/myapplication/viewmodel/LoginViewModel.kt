package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val loginSuccessLD = MutableLiveData<Boolean>()
    val loginErrorLD = MutableLiveData<Boolean>()

    fun login(username: String, password: String) {
        if (username == "student" && password == "123") {
            loginSuccessLD.value = true
            loginErrorLD.value = false
        } else {
            loginSuccessLD.value = false
            loginErrorLD.value = true
        }
    }
}