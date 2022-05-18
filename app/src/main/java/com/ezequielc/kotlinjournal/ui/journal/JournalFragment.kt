package com.ezequielc.kotlinjournal.ui.journal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezequielc.kotlinjournal.databinding.FragmentJournalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private val journalViewModel: JournalViewModel by viewModels()
    private val journalAdapter = JournalAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            journalListRecyclerView.apply {
                adapter = journalAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        journalViewModel.posts.observe(viewLifecycleOwner) {
            journalAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}