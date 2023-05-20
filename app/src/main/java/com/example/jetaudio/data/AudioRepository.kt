package com.example.jetaudio.data

import com.example.jetaudio.model.Audio
import com.example.jetaudio.model.FakeAudioDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class AudioRepository(private val database: FavoriteDatabase) {
    private val audioList = mutableListOf<Audio>()

    init {
        if (audioList.isEmpty()) {
            FakeAudioDataSource.dummyAudio.forEach { audio ->
                audioList.add(
                    Audio(
                        id = audio.id,
                        image = audio.image,
                        title = audio.title,
                        description = audio.description,
                        detail = audio.detail,
                    )
                )
            }
        }
    }

    fun getAllAudio(): Flow<List<Audio>> {
        return flowOf(audioList)
    }

    fun getAudioById(audioId: Long): Audio {
        return audioList.first { audio ->
            audio.id == audioId
        }
    }

    suspend fun insert(favorite: Audio) {
        withContext(Dispatchers.IO) {
            database.favoriteDao().insert(favorite)
        }
    }

    suspend fun delete(favorite: Audio) {
        withContext(Dispatchers.IO) {
            database.favoriteDao().delete(favorite)
        }
    }

    fun getAllFavorite(): Flow<List<Audio>> {
        return database.favoriteDao().getAllFavorite()
    }

    fun isFavorite(audioId: Long): Flow<Boolean> {
        return database.favoriteDao().isFavorite(audioId)
    }
}