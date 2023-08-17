package com.example.testapp.model.room

import com.example.testapp.model.entity.RoomMessage

class Cache(private val database: AppDatabase): ICache {

    override fun insert(message: RoomMessage) = database.cacheDao().insert(message)

    override fun getAll(): List<RoomMessage> = database.cacheDao().getAll()

    override fun deleteAll() = database.cacheDao().deleteAll()
}