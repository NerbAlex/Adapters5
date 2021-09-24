package ru.inc.adapters5.models

import ru.inc.adapters5.ui.base.BaseItem

/**
 * Модель суммы яблок
 *
 * @param amountBasket - сумма всех яблок в корзинах
 * @param amountAll - сумма всех яблок
 * @param amountWarehouse - сумма яблок на складе
 */
data class Amount(
    override var id: Long,
    var amountBasket: Int,
    var amountAll: Int,
    var amountWarehouse: Int
): BaseItem(AMOUNT)