package ru.inc.adapters5.ui.viewholders

import ru.inc.adapters5.databinding.ItemAmountBinding
import ru.inc.adapters5.models.Amount
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.ui.base.BaseViewHolder

class AmountViewHolder(private val view: ItemAmountBinding): BaseViewHolder<BaseItem>(view.root) {
    override fun bind(model: BaseItem) {
        (model as Amount).let {
            view.countAll.text = it.amountAll.toString()
            view.countBasket.text = it.amountBasket.toString()
            view.countWarehouse.text = it.amountWarehouse.toString()
        }
    }

    override fun onBind(model: BaseItem, payloads: MutableList<Any?>) {
        (payloads.last() as Amount).let { amount ->
            view.countAll.text = amount.amountAll.toString()
            view.countBasket.text = amount.amountBasket.toString()
            view.countWarehouse.text = amount.amountWarehouse.toString()
        }
    }
}