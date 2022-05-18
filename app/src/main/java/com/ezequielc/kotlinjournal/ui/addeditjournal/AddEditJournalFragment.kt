package com.ezequielc.kotlinjournal.ui.addeditjournal

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.ezequielc.kotlinjournal.Event
import com.ezequielc.kotlinjournal.R
import com.ezequielc.kotlinjournal.databinding.FragmentAddEditJournalBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditJournalFragment : Fragment() {

    private var _binding: FragmentAddEditJournalBinding? = null
    private val binding get() = _binding!!
    private val addEditJournalViewModel: AddEditJournalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddEditJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            // If JournalItem exists, TextView is visible and text is set to:
            // "Created: {timestamp}"
            if (addEditJournalViewModel.journalItem != null) {
                timestampTextView.isVisible
                timestampTextView.apply {
                    val res = context.resources
                    val timestamp = addEditJournalViewModel.journalItem?.timestampFormatted
                    text = res.getString(R.string.created_text, timestamp)
                }
            }

            // TitleEditText is set to an existing title of JournalItem or blank
            // and a listener is attached if text in EditText is changed
            titleEditText.editText?.setText(addEditJournalViewModel.journalTitle)
            titleEditText.editText?.addTextChangedListener {
                addEditJournalViewModel.journalTitle = it.toString()
            }

            // PostEditText is set to an existing post of JournalItem or blank
            // and a listener is attached if text in EditText is changed
            postEditText.editText?.setText(addEditJournalViewModel.journalPost)
            postEditText.editText?.addTextChangedListener {
                addEditJournalViewModel.journalPost = it.toString()
            }

            // Set raw input to function together with ime option in xml layout
            postEditText.editText?.setRawInputType(
                InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

            // Set a listener when the user clicks on the DONE button in keyboard
            postEditText.editText?.setOnEditorActionListener { _, action, _ ->
                return@setOnEditorActionListener when (action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        addEditJournalViewModel.saveJournalPost()
                        findNavController().navigate(R.id.action_AddEditJournalFragment_to_JournalFragment)
                        true
                    }
                    else -> false
                }
            }
        }

        addEditJournalViewModel.let {
            view.setupSnackbar(this, it.snackbarMessage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Setup of snackbar to be displayed based on Event from ViewModel
    fun View.setupSnackbar(
        lifecycleOwner: LifecycleOwner,
        snackbarEvent: LiveData<Event<Int>>
    ) {
        snackbarEvent.observe(lifecycleOwner) { event ->
            event.getContentIfNotHandled().let { resourceId ->
                val message = context.getString(resourceId!!)
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

}