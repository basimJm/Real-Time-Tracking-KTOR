package com.dg.model.session

import kotlinx.serialization.Serializable

@Serializable
data class TrackingSession(val userName:String, val sessionId: String)
