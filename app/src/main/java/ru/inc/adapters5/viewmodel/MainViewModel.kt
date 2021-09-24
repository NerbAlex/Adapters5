package ru.inc.adapters5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.inc.adapters5.extensions.viewModel
import ru.inc.adapters5.models.Apple
import java.util.logging.Logger

class MainViewModel(private val interactor: MainInteractor) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mutableLiveData: MutableLiveData<MainViewState> = MutableLiveData()
    val liveData: LiveData<MainViewState> = mutableLiveData
    private val log = Logger.getLogger(MainViewModel::class.java.name)


    fun updateData() {
        compositeDisposable.add(interactor.getData().subscribe({
            mutableLiveData.postValue(it)
        }, {}))
    }

    fun eatApple(apple: Apple) {
        compositeDisposable.add(interactor.eatApple(apple).subscribe({
            log.viewModel("eatApple() Success: $it")
            mutableLiveData.postValue(it)
        }, {}))
    }

    fun addBasket() {
        compositeDisposable.add(interactor.addBasket().subscribe({
            mutableLiveData.postValue(it)
        }, {}))
    }

    fun addAppleToBasket(basketId: Long) {
        compositeDisposable.add(interactor.addAppleToBasket(basketId).subscribe({
            mutableLiveData.postValue(it)
        }, {}))
    }

    fun addApple() {
        compositeDisposable.add(interactor.addAppleToWarehouse().subscribe({
            mutableLiveData.postValue(it)
        }, {}))
    }

    fun deleteBasket(basketId: Long) {
        compositeDisposable.add(interactor.deleteBasket(basketId).subscribe({
            mutableLiveData.postValue(it)
        }, {}))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun deleteAllBaskets() {
        compositeDisposable.add(interactor.deleteAllBaskets().subscribe({
            when (it) {
                is MainViewState.Success -> mutableLiveData.postValue(it)
                else -> deleteAllBaskets()
            }
        }, {}))
    }
}