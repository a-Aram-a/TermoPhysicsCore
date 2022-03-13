package ru.tashchyan.objects

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val login: String, val name: String)