package com.ezequielc.kotlinjournal.ui.journal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezequielc.kotlinjournal.R
import com.ezequielc.kotlinjournal.data.JournalItem
import com.ezequielc.kotlinjournal.databinding.FragmentJournalBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JournalFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private val journalViewModel: JournalViewModel by viewModels()
    private val journalAdapter = JournalAdapter(this)

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

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val journalItem = journalAdapter.currentList[viewHolder.adapterPosition]
                    journalViewModel.onJournalItemSwiped(journalItem)
                    showUndoDeleteSnackbar(journalItem)
                }
            }).attachToRecyclerView(journalListRecyclerView)
        }

        journalViewModel.posts.observe(viewLifecycleOwner) {
            journalAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(journalItem: JournalItem) {
        val title = context?.resources?.getString(R.string.edit_journal_post)
        val direction = JournalFragmentDirections
            .actionJournalFragmentToAddEditJournalFragment(title!!, journalItem)
        findNavController().navigate(direction)
    }

    private fun showUndoDeleteSnackbar(journalItem: JournalItem) {
        Snackbar.make(requireView(), R.string.journal_post_deleted, Snackbar.LENGTH_LONG)
            .setAction(R.string.undo_text) {
                journalViewModel.onUndoDeleteClick(journalItem)
            }.show()
    }

}