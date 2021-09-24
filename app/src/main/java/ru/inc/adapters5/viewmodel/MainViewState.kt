package ru.inc.adapters5.viewmodel

import ru.inc.adapters5.ui.base.BaseItem

/**
 * Состояние главного экрана
 *
 * [Success] - Список моделей получен и готов для адаптера
 * [UnableToAddApple] - Невозможно добавить больше [UnableToAddApple.count] яблок, уведомить
 * [NoApplesInStock] - На складе больше нет еблок
 */
sealed class MainViewState {
    data class Success(val list: List<BaseItem>): MainViewState()
    data class Return(val list: List<BaseItem>): MainViewState()
    data class UnableToAddApple(val count: Int): MainViewState()
    object NoApplesInStock: MainViewState()
}
