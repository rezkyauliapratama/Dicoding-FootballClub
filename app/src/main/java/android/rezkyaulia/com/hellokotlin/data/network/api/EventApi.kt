package android.rezkyaulia.com.hellokotlin.data.network.api

import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import com.google.gson.Gson
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE")
class EventApi @Inject constructor(private val networkClient: NetworkClient) : AnkoLogger{

    fun eventPastByLeagueId(leagueId: String,tag : String): Single<EventResponse> {
        return Single.create<EventResponse> { emitter ->
            try {
                val response = getEventPastByLeagueId(leagueId,tag)
                error { Gson().toJson(response) }
                response?.let { emitter.onSuccess(it) }

            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    fun eventNextByLeagueId(leagueId: String,tag : String): Single<EventResponse> {
        return Single.create<EventResponse> { emitter ->
            try {
                val response = getEventNextByLeagueId(leagueId,tag)
                error { Gson().toJson(response) }
                response?.let { emitter.onSuccess(it) }

            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
    fun eventSpecific(eventId: String): Single<EventResponse> {
            return Single.create<EventResponse> { emitter ->
                try {
                    val response = getSpecificEvent(eventId)
                    error { Gson().toJson(response) }
                    response?.let { emitter.onSuccess(it) }

                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
        }

    private fun getEventPastByLeagueId(leagueId : String, tag: String) : EventResponse?
    {

        try
        {
            networkClient.cancelByTag(tag)
            return networkClient.withUrl(TheSportDBApi.getPastEvent(leagueId))
                    .init(EventResponse::class.java)
                    .setTag(tag)
                    .syncFuture
        } catch (e: Exception) {
            error{ "getEventPastByLeagueId Error "}
        }

        return null
    }

    private fun getEventNextByLeagueId(leagueId : String, tag: String) : EventResponse?
    {
        try
        {
            networkClient.cancelByTag(tag)
            return networkClient.withUrl(TheSportDBApi.getNextEvent(leagueId))
                    .init(EventResponse::class.java)
                    .setTag(tag)
                    .syncFuture
        } catch (e: Exception) {
            error{ "getEventPastByLeagueId Error "}
        }

        return null
    }

    private fun getSpecificEvent(eventId : String) : EventResponse?
    {
        try
        {
            val tag = "specific_event"
            networkClient.cancelByTag(tag)
            error { TheSportDBApi.getSpecificEvent(eventId) }
            return networkClient.withUrl(TheSportDBApi.getSpecificEvent(eventId))
                    .init(EventResponse::class.java)
                    .setTag(tag)
                    .syncFuture
        } catch (e: Exception) {
            error{ "getEventPastByLeagueId Error "}
        }

        return null
    }

}