package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.logging.impl.AppLoggerFactory
import com.alth.events.logging.impl.loggerFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class PagingViewModel<T>(
    private val numBeforeNextFetch: Int,
    private val pageSize: Int,
) : ViewModel() {
    private var page = 0

    private val _isLoadingNextData = MutableStateFlow(false)
    val isLoadingNextData = _isLoadingNextData.asStateFlow()

    private var fetchJob: Job = Job().also { it.complete() }

    /**
     * Gets data for pager - no details of paging need to be known
     */
    protected abstract suspend fun getNextPageImpl(page: Int, pageSize: Int): T
    protected abstract suspend fun setNextPageImpl(page: T)

    private suspend fun getNextPage() {
        _isLoadingNextData.value = true
        val t = getNextPageImpl(page, pageSize)
        setNextPageImpl(t)
        page += 1
        _isLoadingNextData.value = false
    }

    protected suspend fun stopAndResetPaging(onComplete: () -> Unit) {
        fetchJob.cancelAndJoin()
        page = 0
        onComplete()
    }

    fun consumeItem(item: Int) {
        loggerFactory.getLogger("TEST").info("Foo bar $item")
        if (!isLoadingNextData.value) {
            if (item >= page * pageSize - numBeforeNextFetch) {
                fetchJob = viewModelScope.launch {
                    getNextPage()
                }
            }
        }
    }
}