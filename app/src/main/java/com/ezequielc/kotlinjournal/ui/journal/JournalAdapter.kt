package com.ezequielc.kotlinjournal.ui.journal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezequielc.kotlinjournal.R
import com.ezequielc.kotlinjournal.data.JournalItem
import com.ezequielc.kotlinjournal.databinding.JournalListItemLayoutBinding

class JournalAdapter(private val listener: OnItemClickListener) :
    ListAdapter<JournalItem, JournalAdapter.JournalViewHolder>(JournalComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = JournalListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JournalViewHolder(private val binding: JournalListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: JournalItem) {
                binding.apply {
                    itemTitleTextView.text = item.title
                    itemPostTextView.text = item.post

                    // https://developer.android.com/guide/topics/resources/string-resource#formatting-strings
                    itemTimestampTextView.apply {
                        val res = context.resources
                        text = res.getString(R.string.created_text, item.timestampFormatted)
                    }

                    root.setOnClickListener {
                        listener.onItemClick(item)
                    }
                }
            }
    }

    class JournalComparator : DiffUtil.ItemCallback<JournalItem>() {
        override fun areItemsTheSame(oldItem: JournalItem, newItem: JournalItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: JournalItem, newItem: JournalItem) =
            oldItem == newItem
    }
}