package io.kulloveth.firebaseandroidauthsample.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import io.kulloveth.firebaseandroidauthsample.R
import io.kulloveth.firebaseandroidauthsample.databinding.FragmentDashboardBinding
import io.kulloveth.firebaseandroidauthsample.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    @Inject
    lateinit var fAuth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()
    private var binding: FragmentDashboardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fAuth.currentUser?.photoUrl != null) {
            binding?.iv?.load(fAuth.currentUser?.photoUrl)
            {
                transformations(CircleCropTransformation())
            }
        } else {
            binding?.iv?.load(R.drawable.ic_account_circle_24) {
                transformations(CircleCropTransformation())
            }
        }

        val args : DashBoardFragmentArgs by navArgs()
        binding?.signTv?.text = String.format(
            resources.getString(R.string.user_greet),
            args.name
        )
    }

}