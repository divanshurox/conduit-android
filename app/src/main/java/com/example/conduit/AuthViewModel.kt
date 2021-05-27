package com.example.conduit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.models.entities.User
import com.example.conduit.data.AuthRepo
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun login(email: String,password: String){
        viewModelScope.launch{
            try{
                AuthRepo.login(email,password)?.let {
                    _user.value = it
                }
            }catch(e: Exception){
                Log.d("AuthViewModel","${e.message}")
            }
        }
    }

    fun register(username:String,email:String,password:String){
        viewModelScope.launch{
            try{
                AuthRepo.register(username,email,password)?.let{
                    _user.value = it
                }
            }catch(e: Exception){
                Log.d("AuthViewModel","${e.message}")
            }
        }
    }
}