package com.gukguk.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gukguk.habittracker.R
import com.gukguk.habittracker.databinding.FragmentLoginBinding
import androidx.navigation.Navigation
import com.gukguk.habittracker.databinding.ActivityMainBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
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

        binding.txtSalah.visibility = View.GONE
        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            if (username == "test" && password == "123") {
                binding.txtSalah.visibility = View.GONE

                val action = LoginFragmentDirections.actionHabitListFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                binding.txtSalah.visibility = View.VISIBLE
            }
        }
    }
}