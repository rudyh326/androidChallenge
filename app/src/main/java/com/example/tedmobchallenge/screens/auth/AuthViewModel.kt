package com.example.tedmobchallenge.screens.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tedmobchallenge.datastore.UserStore
import com.example.tedmobchallenge.utils.onlyLettersAndNumbers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val store = UserStore(application)

    var currentUsername = ""

    private val _storedUsernames = MutableLiveData<List<String>>()

    val storedUsernames: LiveData<List<String>>
        get() = _storedUsernames

    init {
        getUsernames()
    }

    fun doesUsernameExist (editUsername: String) : Boolean {
            storedUsernames.value.let {
                if (it != null) {
                    if (editUsername in it) {
                        currentUsername = editUsername
                        return true
                    } else {
                        currentUsername = ""
                        return false
                    }
                }
                else return false
        }
    }

    private fun getUsernames() {
        viewModelScope.launch {
            store.getUsernames.collect {
                _storedUsernames.value = it
            }
        }
    }


    fun saveUsername (username: String) {
        viewModelScope.launch {
            store.saveUsername(username)
        }
    }

    fun isUsernameAcceptable(editUsername: String) : Boolean {
        if (editUsername == "") return false
        else {
            editUsername.let {
                if (
                    !it[0].isLetter() ||
                    it.length < 4 ||
                    it.length > 12 ||
                    !it.onlyLettersAndNumbers()
                )
                    return false
            }
        }
        return true
    }


}