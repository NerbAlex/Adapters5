package ru.inc.adapters5.models

import ru.inc.adapters5.ui.base.BaseItem

/**
 * Модель корзины
 *
 * @param id - id для сравнения и описания корзин
 * @param listItems - лист моделей корзины
 */
data class Basket(
    override var id: Long,
    var countApple: Int,
    val listItems: MutableList<BaseItem>
): BaseItem(BASKET)
