package ru.inc.adapters5.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: T)
    abstract fun onBind(model: T, payloads: MutableList<Any?>)
}