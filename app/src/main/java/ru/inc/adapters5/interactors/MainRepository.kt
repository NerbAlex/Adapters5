package ru.inc.adapters5.interactors

import io.reactivex.rxjava3.core.Single
import ru.inc.adapters5.ui.base.BaseItem

interface MainRepository {
    fun getData(): Single<List<BaseItem>>
    fun updateData(list: List<BaseItem>): Single<List<BaseItem>>
}