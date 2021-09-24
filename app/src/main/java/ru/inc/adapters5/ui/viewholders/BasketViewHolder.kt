package ru.inc.adapters5.ui.viewholders

import ru.inc.adapters5.databinding.ItemBasketBinding
import ru.inc.adapters5.models.Basket
import ru.inc.adapters5.ui.Listeners
import ru.inc.adapters5.ui.MainAdapter
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.ui.base.BaseViewHolder
import java.util.logging.Logger

class BasketViewHolder(
    private val view: ItemBasketBinding,
    private val listeners: Listeners,
): BaseViewHolder<BaseItem>(view.root) {

    private lateinit var adapter: MainAdapter

    private val log = Logger.getLogger(BasketViewHolder::class.java.name)

    override fun bind(model: BaseItem) {
        val basket = (model as Basket)
        adapter = MainAdapter(listeners)
        view.recycler.adapter = adapter
        view.basketNumberTv.text = basket.id.toString()
        view.appleCountInBasket.text = basket.countApple.toString()
        adapter.submitList(basket.listItems)

        view.deleteBasketIb.setOnClickListener {
            listeners.deleteBasket().invoke(basket.id)
        }
    }

    override fun onBind(model: BaseItem, payloads: MutableList<Any?>) {
        (payloads.last() as Basket)?. let { basket ->
            view.recycler.adapter = adapter
            view.appleCountInBasket.text = basket.countApple.toString()
            adapter.submitList(basket.listItems)
        }
    }
}