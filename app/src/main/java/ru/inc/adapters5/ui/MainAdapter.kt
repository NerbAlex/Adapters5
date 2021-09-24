package ru.inc.adapters5.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.ui.base.BaseViewHolder

class MainAdapter(private var listeners: Listeners) :
    ListAdapter<BaseItem, BaseViewHolder<BaseItem>>(MainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewTypeMapper.map(viewType, parent, listeners)

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItem>, position: Int) = holder.bind(getItem(position))

    override fun getItemViewType(position: Int) = getItem(position).type

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItem>, position: Int, payloads: MutableList<Any?>) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(currentList[position], payloads)
        }
    }
}
