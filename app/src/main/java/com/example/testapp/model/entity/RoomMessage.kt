package com.example.testapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class RoomMessage(@PrimaryKey(autoGenerate = true) val id: Int, val joke: String)