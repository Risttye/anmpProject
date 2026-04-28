package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.viewmodel.LoginViewModel


class LoginFragment : Fragment() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.login(username, password)
        }

        observeViewModel(view)
    }

    private fun observeViewModel(view: View) {
        viewModel.loginSuccessLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Navigation.findNavController(view)
                    .navigate(R.id.actionDashboard)
            }
        })

        viewModel.loginErrorLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })
}


}