package ru.inc.adapters5.extensions

import kotlin.random.Random

fun randomId() = System.currentTimeMillis() + Random.nextInt()
