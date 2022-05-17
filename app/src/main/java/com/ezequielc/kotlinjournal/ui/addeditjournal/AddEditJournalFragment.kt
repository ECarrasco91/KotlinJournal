package com.ezequielc.kotlinjournal.ui.addeditjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ezequielc.kotlinjournal.databinding.FragmentAddEditJournalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditJournalFragment : Fragment() {

    private var _binding: FragmentAddEditJournalBinding? = null
    private val binding get() = _binding!!
    private val addEditJournalViewModel: AddEditJournalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddEditJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}