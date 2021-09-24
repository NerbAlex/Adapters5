package ru.inc.adapters5.ui.viewholders

import androidx.viewbinding.ViewBinding
import ru.inc.adapters5.databinding.ItemAppleHorizontalBinding
import ru.inc.adapters5.databinding.ItemAppleVerticalBinding
import ru.inc.adapters5.models.Apple
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.ui.base.BaseViewHolder

class AppleViewHolder(private val view: ViewBinding, private val eatApple: (apple: Apple?) -> Unit) :
    BaseViewHolder<BaseItem>(view.root) {

    override fun bind(model: BaseItem) {
        (model as Apple).let {
            if (model.type == BaseItem.APPLE_V) {
                (view as ItemAppleVerticalBinding).let { vertical ->
                    vertical.appleBarcodeTv.text = it.id.toString()
                    vertical.appleEatBtn.setOnClickListener {
                        eatApple.invoke(null)
                    }
                }
            } else {
                (view as ItemAppleHorizontalBinding).let { horizontal ->
                    horizontal.appleBarcodeTv.text = it.id.toString()
                    horizontal.appleEatBtn.setOnClickListener {
                        eatApple.invoke(model)
                    }
                }
            }
        }
    }

    override fun onBind(model: BaseItem, payloads: MutableList<Any?>) {}
}