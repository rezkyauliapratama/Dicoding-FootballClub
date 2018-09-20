package android.rezkyaulia.com.hellokotlin.data.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName(value="player", alternate= arrayOf("players"))
    val player : List<Player>
)