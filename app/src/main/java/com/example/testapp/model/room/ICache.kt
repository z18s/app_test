package com.example.testapp.model.room

import com.example.testapp.model.entity.RoomMessage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ICache {
    fun insert(message: RoomMessage): Completable
    fun getAll(): Single<List<RoomMessage>>
    fun deleteAll(): Completable
}