package com.dg.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val role: String,
    val actionType: String,
    val userName: String,
    val assignedTo:String,
    val lat: Double,
    val long: Double,
)
