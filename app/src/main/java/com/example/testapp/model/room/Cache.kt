package com.example.testapp.model.room

import com.example.testapp.model.entity.RoomMessage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Cache(private val database: AppDatabase): ICache {

    override fun insert(message: RoomMessage): Completable = Completable.fromAction {
        database.cacheDao().insert(message)
    }.subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<RoomMessage>> = Single.fromCallable {
        database.cacheDao().getAll()
    }.subscribeOn(Schedulers.io())

    override fun deleteAll(): Completable = Completable.fromAction {
        database.cacheDao().deleteAll()
    }.subscribeOn(Schedulers.io())
}