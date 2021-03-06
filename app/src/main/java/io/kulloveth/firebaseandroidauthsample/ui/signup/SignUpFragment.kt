package io.kulloveth.firebaseandroidauthsample.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import io.kulloveth.firebaseandroidauthsample.R
import io.kulloveth.firebaseandroidauthsample.databinding.FragmentSignUpBinding
import io.kulloveth.firebaseandroidauthsample.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.wellnesscity.data.model.Status
import io.wellnesscity.utils.Constants
import javax.inject.Inject

fun View.showsnackBar(message:String){
    Snackbar.make(this,message, Snackbar.LENGTH_LONG).show()
}
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()
    private val lViewModel: LoginViewModel by viewModels()

        @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    private var binding: FragmentSignUpBinding? = null

    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.signUpBtn?.setOnClickListener {
            val emailText = binding?.emailEt?.text?.toString()
            val passwordText =  binding?.passwordEt?.text.toString()
            val fullNameText =  binding?.fullNameEt?.text?.toString()
            viewModel.signUpUser( emailText.toString(), passwordText, fullNameText.toString()).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                viewModel.saveUser( it.data?.email.toString(), it.data?.fullName.toString())
                                view.showsnackBar("User account registered")
                            }
                            Status.ERROR -> {
                                view.showsnackBar(it.message!!)
                            }
                            Status.LOADING -> {
                                view.showsnackBar("...")
                            }
                        } })
        }
        binding?.loginTv?.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.signUpFragment) {
                NavHostFragment.findNavController(this).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
            }
        }
        binding?.googleSignIn?.setOnClickListener {
            signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RC_SIGN_IN) {


            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)


                lViewModel.signInWithGoogle(account!!).observe(viewLifecycleOwner, {
                    if (it.status == Status.SUCCESS) {
                        if (findNavController().currentDestination?.id == R.id.signUpFragment) {
                            NavHostFragment.findNavController(this).navigate(
                                SignUpFragmentDirections.actionSignUpFragmentToDashBoardFragment()
                            )
                            //Timber.d("display ${fAuth.currentUser?.displayName} ")
                        }
                        viewModel.saveUser(
                            auth.currentUser?.email!!,
                            auth.currentUser?.displayName!!
                        )
                    } else if (it.status == Status.ERROR) {
                        requireView().showsnackBar(it.message!!)
                    }
                })
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
    }
}