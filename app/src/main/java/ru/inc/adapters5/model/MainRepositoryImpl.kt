package ru.inc.adapters5.model

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.inc.adapters5.extensions.randomId
import ru.inc.adapters5.interactors.MainRepository
import ru.inc.adapters5.models.AddApple
import ru.inc.adapters5.models.Amount
import ru.inc.adapters5.models.Apple
import ru.inc.adapters5.models.Basket
import ru.inc.adapters5.ui.base.BaseItem

class MainRepositoryImpl : MainRepository {

    private var listData = listOf(
        Basket(
            countApple = 0,
            id = 1,
            listItems = mutableListOf(
                AddApple(parentId = 1)
            )
        ),
        Basket(
            2,
            countApple = 2,
            listItems = mutableListOf(
                AddApple(parentId = 2),
                Apple(
                    id = randomId(),
                    parentId = 2,
                    isBasket = true,
                    type = BaseItem.APPLE_H
                ),
                Apple(
                    id = randomId(),
                    isBasket = true,
                    type = BaseItem.APPLE_H
                ),
            )
        ),
        Apple(
            id = randomId(),
            isBasket = false,
            type = BaseItem.APPLE_V
        ),
        Apple(
            id = randomId(),
            isBasket = false,
            type = BaseItem.APPLE_V
        ),
        Apple(
            id = randomId(),
            isBasket = false,
            type = BaseItem.APPLE_V
        ),
        Amount(
            id = randomId(),
            amountAll = 5,
            amountWarehouse = 3,
            amountBasket = 2
        )
    )

    override fun getData(): Single<List<BaseItem>> = Single.just(listData).subscribeOn(Schedulers.computation())

    override fun updateData(list: List<BaseItem>): Single<List<BaseItem>> {
        listData = list
        return Single.just(listData)
    }
}