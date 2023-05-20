package com.example.jetaudio.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetaudio.data.AudioRepository
import com.example.jetaudio.model.Audio
import com.example.jetaudio.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AudioRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Audio>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Audio>>
        get() = _uiState

    fun getAudioById(audioId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAudioById(audioId))
        }
    }

    private val _favoriteStatus = MutableStateFlow(false)
    val favoriteStatus: StateFlow<Boolean>
        get() = _favoriteStatus

    fun updateStatus(id: Long) = viewModelScope.launch {
        _favoriteStatus.value = repository.isFavorite(id).first()
    }

    fun changeFavorite(favorite: Audio) {
        viewModelScope.launch {
            if (_favoriteStatus.value) {
                repository.delete(favorite)
            } else {
                repository.insert(favorite)
            }

            _favoriteStatus.value = !_favoriteStatus.value
        }
    }
}