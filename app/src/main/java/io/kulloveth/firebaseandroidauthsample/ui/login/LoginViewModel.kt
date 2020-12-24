package io.kulloveth.firebaseandroidauthsample.ui.login

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import io.kulloveth.firebaseandroidauthsample.data.NetworkControl
import io.kulloveth.firebaseandroidauthsample.data.Repository
import io.wellnesscity.data.model.Resource
import io.wellnesscity.data.model.User
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val networkControl: NetworkControl,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()
    private val gMailUserLiveData = MutableLiveData<Resource<User>>()
    fun signInUser(email: String, password: String): LiveData<Resource<User>> {

        when {
            TextUtils.isEmpty(email) && TextUtils.isEmpty(password) -> {
                userLiveData.postValue(Resource.error(null, "Enter email and password"))
                        }
            networkControl.isConnected() -> {
                userLiveData.postValue(Resource.loading(null))
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                    if (it.result?.signInMethods?.size == 0) {
                        userLiveData.postValue(Resource.error(null, "Email does not exist"))
                    } else {
                        repository.signInUser(email, password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                firebaseAuth.currentUser?.isEmailVerified?.let { verified ->
                                    if (verified) {
                                        repository.fetchUser().addOnCompleteListener { userTask->
                                            if (userTask.isSuccessful){
                                                userTask.result?.documents?.forEach {
                                                    if (it.data!!["email"] == email){
                                                        val name = it.data?.getValue("fullName")
                                                        userLiveData.postValue(Resource.success( User(firebaseAuth.currentUser?.email!!, name?.toString()!!)))
                                                    }
                                                }
                                            }else{
                                                userLiveData.postValue(Resource.error(null,userTask.exception?.message.toString()))
                                            }
                                        }
                                    } else {
                                        userLiveData.postValue(Resource.error(null, "Email is not verified, check your email"))
                                    }
                                }
                            } else {
                                userLiveData.postValue(Resource.error(null, task.exception?.message.toString()))
                                Timber.e(task.exception.toString())
                            }
                        }
                    }
                }
            } else -> {
            userLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return userLiveData
    }

    fun signInWithGoogle(acct: GoogleSignInAccount):LiveData<Resource<User>>{
      repository.signInWithGoogle(acct).addOnCompleteListener { task->
          if (task.isSuccessful){
              gMailUserLiveData.postValue(Resource.success(User(firebaseAuth.currentUser?.email!!,firebaseAuth.currentUser?.displayName!!)))
          }else{
              gMailUserLiveData.postValue(Resource.error(null,"couldn't sign in user"))
          }

      }
        return gMailUserLiveData
    }
}