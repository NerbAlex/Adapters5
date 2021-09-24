package ru.inc.adapters5.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.inc.adapters5.databinding.*
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.ui.base.BaseViewHolder
import ru.inc.adapters5.ui.viewholders.*
import java.lang.IllegalArgumentException

object ViewTypeMapper {
    fun map(viewType: Int, parent: ViewGroup, listeners: Listeners): BaseViewHolder<BaseItem> {
        LayoutInflater.from(parent.context).let { inflater ->
            return when (viewType) {
                BaseItem.BASKET -> BasketViewHolder(ItemBasketBinding.inflate(inflater, parent, false), listeners)
                BaseItem.APPLE_V -> AppleViewHolder(ItemAppleVerticalBinding.inflate(inflater, parent, false), listeners.eatApple())
                BaseItem.AMOUNT -> AmountViewHolder(ItemAmountBinding.inflate(inflater, parent, false))
                BaseItem.APPLE_H -> AppleViewHolder(ItemAppleHorizontalBinding.inflate(inflater, parent, false), listeners.eatApple())
                BaseItem.ADD_APPLE -> AddAppleViewHolder(ItemAddAppleBinding.inflate(inflater, parent, false), listeners.addAppleToBasket())
                else -> throw IllegalArgumentException("Unknown viewType")
            }
        }
    }
}