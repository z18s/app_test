package com.example.testapp.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.model.entity.RoomMessage

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: RoomMessage)

    @Query("SELECT * FROM history")
    fun getAll(): List<RoomMessage>

    @Query("DELETE FROM history")
    fun deleteAll()
}