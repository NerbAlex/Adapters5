package ru.inc.adapters5.interactors

import io.reactivex.rxjava3.core.Single
import ru.inc.adapters5.extensions.randomId
import ru.inc.adapters5.model.MainRepositoryImpl
import ru.inc.adapters5.models.AddApple
import ru.inc.adapters5.models.Amount
import ru.inc.adapters5.models.Apple
import ru.inc.adapters5.models.Basket
import ru.inc.adapters5.ui.base.BaseItem
import ru.inc.adapters5.viewmodel.MainInteractor
import ru.inc.adapters5.viewmodel.MainViewState
import java.util.logging.Logger

/**
 * Класс бизнесс логики
 */
class MainInteractorImpl(
    private val repository: MainRepositoryImpl
) : MainInteractor {

    companion object {
        private const val LIMIT_APPLES_IN_BASKET = 3
    }

    private val log = Logger.getLogger(MainInteractorImpl::class.java.name)

    override fun getData(): Single<MainViewState> = repository.getData().flatMap {
        Single.fromCallable {
            MainViewState.Success(it)
        }
    }

    override fun eatApple(apple: Apple): Single<MainViewState> = repository.getData().flatMap { list ->
        Single.fromCallable {
            val mutableList = list.toMutableList()
            mutableList.forEachIndexed { i, baseItem ->
                if (baseItem is Basket) {
                    baseItem.listItems.forEachIndexed { j, basketItem ->
                        if (basketItem is Apple && basketItem.id == apple.id) {
                            val newItem = baseItem.copy()
                            newItem.listItems.removeAt(j)
                            newItem.countApple -= 1
                            mutableList.removeAt(i)
                            mutableList.add(i, newItem)
                            calculateAmount(mutableList)
                            return@fromCallable mutableList
                        }
                    }
                }
            }
            emptyList()
        }.flatMap {
            repository.updateData(it).flatMap { savedList ->
                Single.just(MainViewState.Success(savedList))
            }
        }
    }

    override fun addBasket(): Single<MainViewState> = repository.getData().flatMap {
        Single.fromCallable {
            val mutableList = it.toMutableList()
            val countBaskets = basketCount(it)
            val freeBasketId = searchFreeBasketId(it)
            mutableList.add(
                countBaskets,
                Basket(
                    (freeBasketId).toLong(), 0, mutableListOf(AddApple(parentId = (freeBasketId).toLong()))
                )
            )
            mutableList
        }.flatMap {
            repository.updateData(it).flatMap { savedList ->
                Single.just(MainViewState.Success(savedList))
            }
        }
    }

    override fun addAppleToBasket(basketId: Long): Single<MainViewState> = repository.getData().flatMap { list ->
        return@flatMap Single.fromCallable {
            val mutableList = list.toMutableList()
            val apple = findApple(mutableList) ?: return@fromCallable MainViewState.NoApplesInStock

            mutableList.forEachIndexed { index, baseItem ->
                if (baseItem is Basket && baseItem.id == basketId) {
                    if (baseItem.countApple == LIMIT_APPLES_IN_BASKET) {
                        return@fromCallable MainViewState.UnableToAddApple(baseItem.countApple)
                    } else {
                        val newItem = baseItem.copy()
                        newItem.countApple += 1
                        apple.parentId = newItem.id
                        newItem.listItems.add(apple)
                        mutableList.removeAt(index)
                        mutableList.add(index, newItem)
                        calculateAmount(mutableList)
                        return@fromCallable MainViewState.Success(mutableList)
                    }
                }
            }
            MainViewState.NoApplesInStock
        }.flatMap {
            when (it) {
                is MainViewState.Success -> {
                    repository.updateData(it.list).flatMap { savedList ->
                        Single.just(MainViewState.Success(savedList))
                    }
                }
                is MainViewState.UnableToAddApple -> Single.just(MainViewState.UnableToAddApple(it.count))
                is MainViewState.NoApplesInStock -> Single.just(MainViewState.NoApplesInStock)
                else -> throw IllegalArgumentException("unknown state")
            }
        }
    }

    private fun calculateAmount(mutableList: MutableList<BaseItem>) {
        var warehouseCount = 0
        var allCount = 0
        var basketsCount = 0
        mutableList.forEach { baseItem ->
            if (baseItem is Apple) {
                allCount++
                warehouseCount++
            } else if (baseItem is Basket) {
                baseItem.listItems.forEach { basketItem ->
                    if (basketItem is Apple && basketItem.type == BaseItem.APPLE_H) {
                        allCount++
                        basketsCount++
                    }
                }
            }
        }
        val newItem = (mutableList.last() as Amount).copy(
            amountBasket = basketsCount,
            amountWarehouse = warehouseCount,
            amountAll = allCount
        )
        mutableList.removeLast()
        mutableList.add(newItem)
    }

    override fun addAppleToWarehouse(): Single<MainViewState> = repository.getData().flatMap { list ->
        return@flatMap Single.fromCallable {
            val mutableList = list.toMutableList()
            val index = basketCount(mutableList)
            mutableList.add(index, Apple(randomId(), type = BaseItem.APPLE_V, isBasket = false))
            calculateAmount(mutableList)
            return@fromCallable mutableList
        }.flatMap {
            repository.updateData(it).flatMap { savedList ->
                Single.just(MainViewState.Success(savedList))
            }
        }
    }

    override fun deleteAllBaskets(): Single<MainViewState> = repository.getData().flatMap { list ->
        val mutableList = list.toMutableList()
        return@flatMap Single.fromCallable {
            mutableList.forEachIndexed { index, baseItem ->
                if (baseItem is Basket) {
                    val listApples = baseItem.listItems
                    listApples.removeFirst()
                    listApples.forEach {
                        if (it is Apple) {
                            it.isBasket = false
                            it.parentId = 0
                            it.type = BaseItem.APPLE_V
                        }
                    }
                    mutableList.removeAt(index)
                    mutableList.addAll(basketCount(mutableList), listApples)
                    calculateAmount(mutableList)
                    return@fromCallable MainViewState.Return(mutableList)
                } else {
                    return@fromCallable MainViewState.Success(mutableList)
                }
            }
            return@fromCallable MainViewState.Success(mutableList)
        }.flatMap {
            when (it) {
                is MainViewState.Success -> {
                    repository.updateData(it.list).flatMap { savedList ->
                        Single.just(MainViewState.Success(savedList))
                    }
                }
                is MainViewState.Return -> {
                    repository.updateData(it.list).flatMap {
                        Single.just(MainViewState.Return(emptyList()))
                    }
                }
                else -> throw IllegalArgumentException("invalid state")
            }
        }
    }


    override fun deleteBasket(basketId: Long): Single<MainViewState> = repository.getData().flatMap { list ->
        Single.fromCallable {
            val mutableList = list.toMutableList()
            mutableList.forEachIndexed { index, baseItem ->
                if (baseItem is Basket && baseItem.id == basketId) {
                    val listApples = baseItem.listItems
                    listApples.removeFirst()
                    listApples.forEach {
                        if (it is Apple) {
                            it.isBasket = false
                            it.parentId = 0
                            it.type = BaseItem.APPLE_V
                        }
                    }
                    mutableList.removeAt(index)
                    mutableList.addAll(basketCount(mutableList), listApples)
                    calculateAmount(mutableList)
                    return@fromCallable mutableList
                }
            }
            mutableList
        }.flatMap {
            repository.updateData(it).flatMap { savedList ->
                Single.just(MainViewState.Success(savedList))
            }
        }
    }

    private fun findApple(mutableList: MutableList<BaseItem>): Apple? {
        var apple: Apple? = null
        mutableList.forEachIndexed { index, baseItem ->
            if (baseItem is Apple) {
                apple = baseItem.copy(type = BaseItem.APPLE_H, isBasket = true)
                mutableList.removeAt(index)
                return apple
            }
        }
        return apple
    }

    private fun basketCount(list: List<BaseItem>): Int {
        var basketCounter = 0

        list.forEach { baseItem ->
            if (baseItem is Basket) {
                basketCounter++
            }
        }
        return basketCounter
    }

    private fun searchFreeBasketId(list: List<BaseItem>): Int {
        val idList: MutableList<Int> = mutableListOf()
        idList.add(0)

        list.forEach { baseItem ->
            if (baseItem is Basket) {
                idList.add(baseItem.id.toInt())
                idList.sort()
            }
        }
        return idList.last().plus(1)
    }
}