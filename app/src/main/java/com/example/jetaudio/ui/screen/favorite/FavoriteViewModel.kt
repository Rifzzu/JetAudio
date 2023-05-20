package com.example.jetaudio.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetaudio.data.AudioRepository
import com.example.jetaudio.model.Audio
import com.example.jetaudio.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: AudioRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Audio>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Audio>>>
        get() = _uiState

    fun getAllFavorite() {
        viewModelScope.launch {
            repository.getAllFavorite()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { audioFavoriteList ->
                    _uiState.value = UiState.Success(audioFavoriteList)
                }
        }
    }
}