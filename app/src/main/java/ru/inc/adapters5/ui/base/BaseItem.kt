package ru.inc.adapters5.ui.base

import ru.inc.adapters5.models.Apple

/**
 * Базовая модель
 *
 * @param type - Тип модели, для определения типа ViewHolder
 *
 * [BASKET] - Тип корзина
 * [APPLE_V] - Тип яблока, может находится в корзине и в общем списке
 * [AMOUNT] - Тип модели, для отображения сумм яблок
 * [ADD_APPLE] - Тип для добавления яблока в корзину, берем яблоко из основного списка
 */
abstract class BaseItem(
    open var type: Int
) {
    open var id: Long = 0

    companion object {
        const val BASKET = 2
        const val APPLE_V = 3
        const val AMOUNT = 4
        const val ADD_APPLE = 6
        const val APPLE_H = 8
    }
}

fun BaseItem.isApple(): Boolean = this is Apple //TODO переписать все проверки в интеракторе на подобные Extensions