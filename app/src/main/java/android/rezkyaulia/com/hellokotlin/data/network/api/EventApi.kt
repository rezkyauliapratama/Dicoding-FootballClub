package android.rezkyaulia.com.hellokotlin.data.network.api

import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import com.google.gson.Gson
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNREACHABLE_CODE")
class EventApi @Inject constructor(val networkClient: NetworkClient) : AnkoLogger{

    fun eventPastByLeagueId(leagueId: String): Single<EventResponse> {
        return Single.create<EventResponse> { emitter ->
            try {
                val response = getEventPastByLeagueId(leagueId)
                error { Gson().toJson(response) }
                response?.let { emitter.onSuccess(it) }

            } catch (e: Exception) {
                emitter.onError(e)
            }


        }
    }

    private fun getEventPastByLeagueId(leagueId : String) : EventResponse?
    {
        if (networkClient == null) {
            throw NullPointerException("Network client == null")
        }
        try {
            networkClient.cancelAllRequest()
            error { TheSportDBApi.getPastEvent(leagueId) }
            return networkClient.withUrl(TheSportDBApi.getPastEvent(leagueId))
                    .init(EventResponse::class.java)
                    .getSyncFuture()
        } catch (e: Exception) {
            error{ "getEventPastByLeagueId Error "}
        }


        return null
    }

}