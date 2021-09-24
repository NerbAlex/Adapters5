package ru.inc.adapters5

import android.app.Application
import ru.inc.adapters5.interactors.MainInteractorImpl
import ru.inc.adapters5.model.MainRepositoryImpl

class MyApp : Application() {

    private lateinit var repository: MainRepositoryImpl
    lateinit var interactor: MainInteractorImpl
        private set


    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dependencies()
    }

    private fun dependencies() {
        repository = MainRepositoryImpl()
        interactor = MainInteractorImpl(repository)
    }
}