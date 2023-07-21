package org.jash.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CommonAdapter<D>(val layoutId:Int, val variableId:Int, val datas:MutableList<D> = mutableListOf()) : Adapter<CommonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder =
        CommonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent,false))


    override fun getItemCount(): Int  = datas.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.binding.setVariable(variableId, datas[position])
    }
    operator fun plusAssign(collection: Collection<D>) {
        val start = datas.size
        datas.addAll(collection)
        notifyItemRangeInserted(start, collection.size)
    }
    fun clear() {
        val size = datas.size
        datas.clear()
        notifyItemRangeRemoved(0, size)
    }
}
class CommonViewHolder(val binding: ViewDataBinding):ViewHolder(binding.root)