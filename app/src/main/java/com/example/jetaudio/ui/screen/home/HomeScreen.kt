package com.example.jetaudio.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetaudio.model.Audio
import com.example.jetaudio.ui.ViewModelFactory
import com.example.jetaudio.ui.common.UiState
import com.example.jetaudio.ui.components.AudioItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Long) -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllAudio()
            }

            is UiState.Success -> {
                HomeContent(
                    audioList = uiState.data,
                    navigateToDetail = navigateToDetail,
                    viewModel = viewModel,
                    modifier = modifier
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent (
    audioList: List<Audio>,
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
){
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .testTag("HomeList")
        ) {
            items(audioList, key = { it.id }) { item ->
                AudioItem(
                    title = item.title,
                    image = item.image,
                    description = item.description,
                    modifier = Modifier.clickable {navigateToDetail(item.id)}
                )
            }
        }
    }
}