package com.jkb.junbin.sharing.feature.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jkb.junbin.sharing.feature.message.databinding.MessageListItemBinding

class MessageListAdapter :
    ListAdapter<Message, MessageListAdapter.MessageVH>(DynamicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVH {
        return MessageVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.message_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageVH, position: Int) {
        holder.binding.message = getItem(position)
        holder.binding.executePendingBindings()
    }

    class MessageVH(val binding: MessageListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private class DynamicDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}