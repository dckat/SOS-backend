package com.sso.ssoapi.dto

import com.querydsl.core.annotations.QueryProjection

data class MySosDetail @QueryProjection constructor(
    val id: Long,
    val content: String,
    val location: String,
    val cost: Long,
)