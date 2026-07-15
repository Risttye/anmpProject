package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val loginSuccessLD = MutableLiveData<Boolean>()
    val loginErrorLD = MutableLiveData<Boolean>()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun login(username: String, password: String) {
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().getUser(username, password)
            if (user != null) {
                loginSuccessLD.postValue(true)
                loginErrorLD.postValue(false)
            } else {
                loginSuccessLD.postValue(false)
                loginErrorLD.postValue(true)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}