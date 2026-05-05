package com.gukguk.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gukguk.habittracker.R
import com.gukguk.habittracker.databinding.FragmentHabitListBinding
import com.gukguk.habittracker.viewmodel.HabitListViewModel
import kotlin.jvm.java

class HabitListFragment : Fragment() {
    private lateinit var viewModel: HabitListViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var binding: FragmentHabitListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HabitListViewModel::class.java)
        habitListAdapter = HabitListAdapter(arrayListOf(), viewModel)
        binding.recViewHabit.adapter = habitListAdapter
        viewModel.loadHabit()

        binding.recViewHabit.layoutManager = LinearLayoutManager(context)
        binding.recViewHabit.adapter = habitListAdapter

        observeViewModel()

        binding.refreshLayout.setOnRefreshListener {
            binding.recViewHabit.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.loadHabit()
            binding.refreshLayout.isRefreshing = false
        }

        binding.fabCreateHabit.setOnClickListener {
            val action = HabitListFragmentDirections.actionCreateHabitFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner, Observer {
            habitListAdapter.updateHabitList(it)
        })
        viewModel.habitLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.recViewHabit.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recViewHabit.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })

    }
}