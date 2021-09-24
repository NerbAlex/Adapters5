package ru.inc.adapters5.ui

import ru.inc.adapters5.models.Apple

interface Listeners {
    fun eatApple(): (apple: Apple?) -> Unit
    fun addAppleToBasket(): (basketId: Long) -> Unit
    fun deleteBasket(): (basketId: Long) -> Unit
}