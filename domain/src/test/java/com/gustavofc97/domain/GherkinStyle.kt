package com.gustavofc97.domain

infix fun <T> Any.given(block: () -> T) = block.invoke()

infix fun <T> Any.whenever(block: () -> T) = block.invoke()

infix fun Any.then(block: () -> Unit) = block.invoke()