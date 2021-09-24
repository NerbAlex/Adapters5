package ru.inc.adapters5.models

import ru.inc.adapters5.ui.base.BaseItem

/**
 * Модель для добавления яблока в корзине
 */
data class AddApple(
    override var type: Int = ADD_APPLE,
    val parentId: Long
) : BaseItem(type)
