package com.example.jetaudio.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetaudio.model.Audio
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Audio)

    @Delete
    suspend fun delete(favorite: Audio)

    @Query("SELECT * FROM Audio")
    fun getAllFavorite(): Flow<List<Audio>>

    @Query("SELECT EXISTS(SELECT * FROM Audio WHERE id = :id)")
    fun isFavorite(id: Long): Flow<Boolean>
}