package com.shekhar.lokal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shekhar.lokal.data.db.JobDetailDao
import com.shekhar.lokal.data.db.JobDetailEntity
import com.shekhar.lokal.data.model.JobDetail
import com.shekhar.lokal.data.repository.JobRepository
import com.shekhar.lokal.navigation.fromEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.List

@OptIn(ExperimentalCoroutinesApi::class)
class JobViewModel(private val repository: JobRepository, private val dao: JobDetailDao) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val _isBookmark = MutableStateFlow<Int?>(null)

    val isBookmark: StateFlow<Boolean> = _isBookmark.filterNotNull().flatMapLatest { dao.isJobExists(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = false)

    private val _isLoadingNext = MutableStateFlow(false)
    val isLoadingNext = _isLoadingNext.asStateFlow()

    private val _jobList = MutableStateFlow<List<JobDetail>>(emptyList())
    val jobList = _jobList.asStateFlow()

    private val _bookmarkJob = MutableStateFlow<List<JobDetail>>(emptyList())
    val bookmarkJob: StateFlow<List<JobDetail>> = _bookmarkJob.asStateFlow()

    private val _selectedJob = MutableStateFlow<JobDetail?>(null)
    val selectedJob = _selectedJob.asStateFlow()

    private val _errorChannel = Channel<String>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private var nextPage = 2

    init {
        getJobs()
    }


    fun loadBookmarkJobs() {
        viewModelScope.launch {
            dao.getAllJobDetails().collect { entityList ->
                _bookmarkJob.value = entityList.map { fromEntity(it) }
            }
        }
    }

    fun addBookmark(job: JobDetailEntity) {
        viewModelScope.launch {
            dao.insertJob(job)
        }
    }

    fun bookMarkStatus(id: Int) {
        _isBookmark.value = id
    }

    fun clearBookmark(id: Int) {
        viewModelScope.launch {
            dao.clearJob(id)
        }
    }

    fun getJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val response = repository.getJobs(1)
            if (response.isSuccess) {
                nextPage = 2
                _jobList.value = response.getOrNull() ?: emptyList()
            } else {
                _errorChannel.send(response.exceptionOrNull()?.message ?: "Unknown error")
                _showRetry.value = true
            }
            _isLoading.value = false
        }
    }

    fun getJobsNext() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isLoadingNext.value) {
                _isLoadingNext.value = true
                val response = repository.getJobs(nextPage)
                if (response.isSuccess) {
                    if (response.getOrNull()?.isEmpty() == true) {
                        _errorChannel.send("You have reached the end of the list")
                    } else {
                        _jobList.value += response.getOrNull() ?: emptyList()
                        nextPage += 1
                    }
                } else {
                    _errorChannel.send(response.exceptionOrNull()?.message ?: "Unknown error")
                }
                _isLoadingNext.value = false
            }
        }
    }

    fun selectJob(job: JobDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedJob.value = job
        }
    }

    fun resetSelectedJob() {
        _selectedJob.value = null
    }

    fun resetRetry() {
        _showRetry.value = false
    }
}