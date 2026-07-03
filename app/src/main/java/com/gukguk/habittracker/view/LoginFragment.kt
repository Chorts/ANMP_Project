package com.gukguk.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.gukguk.habittracker.R
import com.gukguk.habittracker.databinding.FragmentLoginBinding
import androidx.navigation.Navigation
import com.gukguk.habittracker.model.AppDatabase
import com.gukguk.habittracker.util.SessionManager
import kotlinx.coroutines.launch

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

        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        val sessionManager = SessionManager(requireContext())

        binding.txtSalah.visibility = View.GONE

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                val user = userDao.login(username, password)

                if (user != null) {
                    binding.txtSalah.visibility = View.GONE
                    sessionManager.saveLogin(user.id)

                    val action = LoginFragmentDirections.actionHabitListFragment()
                    Navigation.findNavController(it).navigate(action)
                } else {
                    binding.txtSalah.visibility = View.VISIBLE
                }
            }
        }
    }
}