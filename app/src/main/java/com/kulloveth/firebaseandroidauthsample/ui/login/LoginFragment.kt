package com.kulloveth.firebaseandroidauthsample.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.kulloveth.firebaseandroidauthsample.R
import com.kulloveth.firebaseandroidauthsample.databinding.FragmentLoginBinding
import com.kulloveth.firebaseandroidauthsample.ui.signup.showsnackBar
import dagger.hilt.android.AndroidEntryPoint
import io.wellnesscity.data.model.Status


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var binding: FragmentLoginBinding? = null

//    @Inject
//    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.signUpTv?.setOnClickListener {

            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
        }
        binding?.signInBtn?.setOnClickListener {
            val emailText = binding?.emailEt?.text?.toString()
            val passwordText =  binding?.passwordEt?.text.toString()
            viewModel.signInUser(emailText!!, passwordText).observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.LOADING -> {
                        view.showsnackBar("...")
                    }

                    Status.SUCCESS -> {
                        view.showsnackBar("Login successful")
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.actionLoginFragmentToDashBoardFragment())
                        }
                    }

                    Status.ERROR -> {
                        view.showsnackBar(it.message!!)
                    }
                }
            })
        }

//        binding?.googleSignIn?.setOnClickListener {
//            signIn()
//        }
    }

//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//
//            //Getting the GoogleSignIn Task
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                //Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)
//
//                //authenticating with firebase
//                viewModel.signInWithGoogle(account!!).observe(viewLifecycleOwner, {
//                    if (it.status == Status.SUCCESS) {
//                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
//                            NavHostFragment.findNavController(this).navigate(
//                                LoginFragmentDirections.actionLoginFragmentToMainFragment())
//                           // Timber.d("display ${fAuth.currentUser?.displayName} ")
//                        }
//                    } else if (it.status == Status.ERROR) {
//                        requireView().showsnackBar(it.message!!)
//                    }
//                })
//            } catch (e: ApiException) {
//                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    private fun signIn() {
//        //getting the google signin intent
//        val signInIntent: Intent = googleSignInClient.signInIntent
//
//        //starting the activity for result
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//
//    }

}