package com.gustavofc97.covalto.utlis

import kotlin.coroutines.CoroutineContext

data class CoroutineContextProvider(
    val mainContext: CoroutineContext,
    val backgroundContext: CoroutineContext
)