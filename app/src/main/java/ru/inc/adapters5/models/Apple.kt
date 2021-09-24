package ru.inc.adapters5.models

import ru.inc.adapters5.ui.base.BaseItem

/**
 * Модель Яблока
 *
 * @param id - штрих-код товара
 * @param isBasket - Флаг принадлежности к корзине
 * @param parentId - id родительской корзины
 */
data class Apple(
    override var id: Long,
    var isBasket: Boolean = false,
    var parentId: Long = 0,
    override var type: Int = APPLE_V
): BaseItem(type)