package com.ezequielc.kotlinjournal.ui.dialog

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ezequielc.kotlinjournal.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllDialogFragment : DialogFragment() {

    private val deleteAllViewModel: DeleteAllViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setNegativeButton(R.string.negative_button_text, null)
            .setPositiveButton(R.string.positive_button_text) { _, _ ->
                deleteAllViewModel.onConfirmDelete()
            }
            .create()
}