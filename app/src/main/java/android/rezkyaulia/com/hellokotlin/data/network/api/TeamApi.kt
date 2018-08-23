package android.rezkyaulia.com.hellokotlin.data.network.api

import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import com.google.gson.Gson
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import com.rezkyaulia.android.light_optimization_data.NetworkClient.GET
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE")
class TeamApi @Inject constructor(val networkClient: NetworkClient) : AnkoLogger {

    fun teamById(teamId: String?): Single<TeamResponse> {
        return Single.create<TeamResponse> { emitter ->
            try {
                val response : TeamResponse? = getTeam(teamId)
                error { "response" + Gson().toJson(response) }
                if (response != null) {
                    emitter.onSuccess(response)
                }


            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
    private fun getTeam(teamId : String?) : TeamResponse?
    {
        if (networkClient == null) {
            throw NullPointerException("Network client == null")
        }
        try
        {
            error { "link : " + TheSportDBApi.getSpecificTeam(teamId.toString()) }
            return networkClient.withUrl(TheSportDBApi.getSpecificTeam(teamId.toString()))
                    .init(TeamResponse::class.java,GET)
                    .getSyncFuture()
        } catch (e: Exception) {
            error{ "getTeam Error " + Gson().toJson(e)}
        }
    return null
    }

}