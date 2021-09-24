package ru.inc.adapters5.extensions

import java.util.logging.Logger

private const val isAdapters = true
private const val isInteractor = false
private const val isViewModel = true
private const val isDebug = true

private const val TAG = "Adapters5_"

fun Logger.adapters(log: String) {
    if (isAdapters) info("$TAG: $log")
}

fun Logger.interactor(log: String) {
    if (isInteractor) info("$TAG: $log")
}

fun Logger.viewModel(log: String) {
    if (isViewModel) info("$TAG: $log")
}

fun Logger.debug(log: String) {
    if (isDebug) info("$TAG: $log")
}