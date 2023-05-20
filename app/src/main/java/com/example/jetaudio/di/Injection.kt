package com.example.jetaudio.di

import android.content.Context
import com.example.jetaudio.data.AudioRepository
import com.example.jetaudio.data.FavoriteDatabase

object Injection {
    fun provideRepository(context: Context): AudioRepository {
        return AudioRepository(
            database = FavoriteDatabase.getDatabase(context)
        )
    }
}