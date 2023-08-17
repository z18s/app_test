package com.example.testapp.model.room

import com.example.testapp.model.entity.RoomMessage

interface ICache {
    fun insert(message: RoomMessage)
    fun getAll(): List<RoomMessage>

    fun deleteAll()
}