package ru.inc.adapters5.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.inc.adapters5.models.Amount
import ru.inc.adapters5.models.Basket
import ru.inc.adapters5.ui.base.BaseItem
import java.util.logging.Logger

class MainDiffCallback : DiffUtil.ItemCallback<BaseItem>() {

    private val log = Logger.getLogger(MainAdapter::class.java.name)

    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem) = oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return when (true) {
            (oldItem is Basket && newItem is Basket) -> oldItem.countApple == newItem.countApple
            (oldItem is Amount && newItem is Amount) -> oldItem == newItem
            else -> areItemsTheSame(oldItem, newItem)
        }
    }

    override fun getChangePayload(oldItem: BaseItem, newItem: BaseItem): Any? {
        return when (true) {
            (oldItem is Amount && newItem is Amount) -> {
                return if (oldItem.amountWarehouse != newItem.amountWarehouse ||
                    oldItem.amountBasket != newItem.amountBasket ||
                    oldItem.amountAll != newItem.amountAll
                ) newItem
                else return null
            }
            (oldItem is Basket && newItem is Basket) -> {
                return if (oldItem.countApple != newItem.countApple) newItem
                else return null
            }
            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}