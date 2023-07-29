package com.example.testapp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(var joke: String = "") : Parcelable