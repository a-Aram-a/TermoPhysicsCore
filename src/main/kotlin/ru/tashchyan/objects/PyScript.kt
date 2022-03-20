package ru.tashchyan.objects
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class PyScript(val id: Int, val creatorID: Int, val name: String, val description: String, @Transient val path: String = "")