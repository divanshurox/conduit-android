package com.example.conduit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.models.entities.User
import com.example.api.models.entities.UserUpdateData
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

    fun getUser(token: String){
        viewModelScope.launch{
            try{
                AuthRepo.getUserProfile(token)?.let{
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

    fun updateProfile(bio: String,imageUrl: String,email: String,newPassword: String?){
        viewModelScope.launch{
            try{
                val userUpdateData: UserUpdateData = if(newPassword.isNullOrEmpty()){
                    UserUpdateData(bio,email,imageUrl,_user.value?.username.toString(),null)
                }else{
                    UserUpdateData(bio,email,imageUrl,_user.value?.username.toString(),newPassword)
                }
                AuthRepo.updateProfile(userUpdateData)?.let{
                    _user.value = it
                }
            }catch(e: Exception){
                Log.d("AuthViewModel","${e.message}")
            }
        }
    }

    fun logout(){
        _user.value = null
    }
}