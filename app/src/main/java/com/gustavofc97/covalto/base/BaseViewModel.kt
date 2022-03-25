package com.gustavofc97.covalto.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavofc97.covalto.utlis.CoroutineContextProvider
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val _loadingEvent = MutableLiveData<Boolean>()
    val loadingEvent: LiveData<Boolean> = _loadingEvent

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    private val parentJob: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext by lazy { coroutineContextProvider.mainContext + parentJob }


    override fun onCleared() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
        super.onCleared()
    }

    protected fun launchInBackground(
        showLoading: Boolean = true,
        job: suspend CoroutineScope.() -> Unit
    ) =
        launch(job, coroutineContextProvider.backgroundContext, showLoading)

    private fun launch(
        job: suspend CoroutineScope.() -> Unit,
        coroutineContext: CoroutineContext,
        loading: Boolean = true
    ) = viewModelScope.launch {
        withContext(coroutineContext) {
            if (loading) _loadingEvent.postValue(true)
            job()
            if (loading) _loadingEvent.postValue(false)
        }
    }
}