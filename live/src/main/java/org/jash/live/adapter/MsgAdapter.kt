package org.jash.live.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.jash.live.databinding.JoinLeaveItemBinding
import org.jash.live.databinding.MsgLeftItemBinding
import org.jash.live.model.Message

class MsgAdapter(val data:MutableList<Any> = mutableListOf()):Adapter<MsgViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when(data[position]) {
            is Message -> 0
            is String -> 1
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        return MsgViewHolder(when(viewType) {
            0 -> MsgLeftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            1 -> JoinLeaveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> MsgLeftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        when(holder.binding) {
            is MsgLeftItemBinding -> holder.binding.msg = data[position] as Message
            is JoinLeaveItemBinding -> holder.binding.str = data[position] as String
        }
    }
    operator fun plusAssign(msg:Message) {
        data += msg
        notifyItemInserted(data.size)
    }
    operator fun plusAssign(str:String) {
        data += str
        notifyItemInserted(data.size)
    }

}
class MsgViewHolder(val binding:ViewDataBinding):ViewHolder(binding.root)