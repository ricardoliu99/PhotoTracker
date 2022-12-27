package com.example.phototracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)

    @Update
    suspend fun update(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)

    @Query("SELECT * FROM Photo")
    fun getAll(): Flow<List<Photo>>

    @Query("SELECT * FROM Photo WHERE id = :id ORDER BY title")
    fun getPhotoById(id: Int): Flow<Photo>

}