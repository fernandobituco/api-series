package com.audax.projetoseries.model

import com.google.gson.annotations.SerializedName

data class TryLoginResponse(
    @SerializedName("access_token")
    val access_token: String
)