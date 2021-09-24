package ru.inc.adapters5.viewmodel

import io.reactivex.rxjava3.core.Single
import ru.inc.adapters5.models.Apple

interface MainInteractor {

    fun getData(): Single<MainViewState>
    fun eatApple(apple: Apple): Single<MainViewState>
    fun deleteBasket(basketId: Long): Single<MainViewState>
    fun addBasket(): Single<MainViewState>
    fun addAppleToBasket(basketId: Long): Single<MainViewState>
    fun addAppleToWarehouse(): Single<MainViewState>
    fun deleteAllBaskets(): Single<MainViewState>
}