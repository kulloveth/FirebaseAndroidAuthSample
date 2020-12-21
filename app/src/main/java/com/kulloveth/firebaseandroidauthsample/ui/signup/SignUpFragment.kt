package com.kulloveth.firebaseandroidauthsample.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.kulloveth.firebaseandroidauthsample.databinding.FragmentSignUpBinding
import com.kulloveth.firebaseandroidauthsample.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()
    private val lViewModel: LoginViewModel by viewModels()

    //    @Inject
//    lateinit var googleSignInClient: GoogleSignInClient
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

//        binding?.signUpBtn?.setOnClickListener {
//            viewModel.signUpUser(
//                binding?.emailEt?.text?.toString()!!,
//                binding?.passwordEt?.text.toString()!!,
//                binding?.fullNameEt?.text?.toString()!!
//            )
//                .observe(viewLifecycleOwner,
//                    {
//                        when (it.status) {
//                            Status.SUCCESS -> {
//                                view.showsnackBar("User account registered")
//                                viewModel.saveUser(
//                                    binding?.emailEt?.text?.toString()!!,
//                                    binding?.fullNameEt?.text?.toString()!!
//                                )
//                            }
//                            Status.ERROR -> {
//                                view.showsnackBar(it.message!!)
//                            }
//                            Status.LOADING -> {
//                                view.showsnackBar("...")
//                            }
//                        }
//                    })
//        }
//        binding?.loginTv?.setOnClickListener {
//            if (findNavController().currentDestination?.id == R.id.signUpFragment) {
//                NavHostFragment.findNavController(this)
//                    .navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
//            }
//        }
//        binding?.googleSignIn?.setOnClickListener {
//            signIn()
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Constants.RC_SIGN_IN) {
//
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//
//                val account = task.getResult(ApiException::class.java)
//
//
//                lViewModel.signInWithGoogle(account!!).observe(viewLifecycleOwner, {
//                    if (it.status == Status.SUCCESS) {
//                        if (findNavController().currentDestination?.id == R.id.signUpFragment) {
//                            NavHostFragment.findNavController(this).navigate(
//                                SignUpFragmentDirections.actionSignUpFragmentToMainFragment()
//                            )
//                            //Timber.d("display ${fAuth.currentUser?.displayName} ")
//                        }
//                        viewModel.saveUser(
//                            auth.currentUser?.email!!,
//                            auth.currentUser?.displayName!!
//                        )
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
//        val signInIntent: Intent = googleSignInClient.signInIntent
//
//        startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
//    }
}