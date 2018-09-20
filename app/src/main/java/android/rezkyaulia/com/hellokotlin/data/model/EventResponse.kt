package android.rezkyaulia.com.hellokotlin.data.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
        @SerializedName(value = "events", alternate = arrayOf("event"))
        val events: List<Event>
)