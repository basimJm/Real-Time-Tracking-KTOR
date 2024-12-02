package com.dg.model

import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val userName: String,
    val sessionId: String,
    val socketSession: WebSocketSession
)
