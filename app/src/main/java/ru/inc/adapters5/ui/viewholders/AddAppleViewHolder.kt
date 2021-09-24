package ru.inc.adapters5.ui.viewholders

import ru.inc.adapters5.databinding.ItemAddAppleBinding
import ru.inc.adapters5.models.AddApple
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.ui.base.BaseViewHolder

class AddAppleViewHolder(
    private val view: ItemAddAppleBinding,
    private val addAppleToBasket: (basketId: Long) -> Unit
) :
    BaseViewHolder<BaseItem>(view.root) {
    override fun bind(model: BaseItem) {
        (model as AddApple).let { addApple ->
            view.addApple.setOnClickListener {
                addAppleToBasket.invoke(addApple.parentId)
            }
        }
    }

    override fun onBind(model: BaseItem, payloads: MutableList<Any?>) {}
}