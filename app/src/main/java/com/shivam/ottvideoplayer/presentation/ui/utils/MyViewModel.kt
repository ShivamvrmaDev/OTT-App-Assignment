package com.shivam.ottvideoplayer.presentation.ui.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.ottvideoplayer.data.repostiory.AnalyticsRepoImpl
import com.shivam.ottvideoplayer.data.repostiory.DomainRepoImpl
import com.shivam.ottvideoplayer.domain.domain_model.DomainItem
import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel
import com.shivam.ottvideoplayer.domain.domain_model.PlayerEvent
import com.shivam.ottvideoplayer.domain.use_case.GetHomeContentUseCase
import com.shivam.ottvideoplayer.domain.use_case.LogPlayerEventUseCase
import com.shivam.ottvideoplayer.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val domainRepository = DomainRepoImpl(application)
    private val getHomeContentUseCase = GetHomeContentUseCase(domainRepository)
    private val logPlayerEventUseCase = LogPlayerEventUseCase(AnalyticsRepoImpl())

    private val _uiState = MutableStateFlow<UiState<DomainOTTDataModel>>(UiState.Loading)
    val uiState: StateFlow<UiState<DomainOTTDataModel>> = _uiState.asStateFlow()

    private val _selectedItem = MutableStateFlow<DomainItem?>(null)
    val selectedItem: StateFlow<DomainItem?> = _selectedItem.asStateFlow()

    private val _homeData = MutableStateFlow<DomainOTTDataModel?>(null)
    val homeData: StateFlow<DomainOTTDataModel?> = _homeData.asStateFlow()

    init {
        loadData()
    }

    fun setSelectedItem(item: DomainItem) {
        _selectedItem.value = item
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                getHomeContentUseCase().collect { data ->
                    if (data != null) {
                        _homeData.value = data
                        _uiState.value = UiState.Success(data)
                    } else {
                        _uiState.value = UiState.Error("Failed to load content data")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    fun onPlayerEvent(event: PlayerEvent) {
        logPlayerEventUseCase(event)
    }
}