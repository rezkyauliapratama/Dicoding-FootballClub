package android.rezkyaulia.com.hellokotlin.data.network

import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.model.PlayerResponse
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rezky Aulia Pratama on 11/9/18.
 */

interface NetworkApi{

        @GET(EndPoints.eventPastLeague)
        fun getEventPastLeague(
                @Query("id") leagueId:String?
                ): Single<EventResponse>

        @GET(EndPoints.eventNextLeague)
        fun getEventNextLeague(
                @Query("id") leagueId:String?
        ): Single<EventResponse>

        @GET(EndPoints.lookupTeam)
        fun getSpecificTeam(
                @Query("id") id:String?
        ): Single<TeamResponse>

        @GET(EndPoints.lookupEvent)
        fun getSpecificEvent(
                @Query("id") id:String?
        ): Single<EventResponse>

        @GET(EndPoints.searchTeam)
        fun getSearchAllTeams(
                @Query("l") id:String?
        ): Single<TeamResponse>

        @GET(EndPoints.allPlayerInTeam)
        fun getAllPlayersTeam(
                @Query("id") teamId:String?
        ): Single<PlayerResponse>

        @GET(EndPoints.lookupPlayer)
        fun getDetailPlayer(
                @Query("id") playerId:String?
        ): Single<PlayerResponse>


        @GET(EndPoints.searchTeamByName)
        fun searchTeamByName(
                @Query("t") name:String?
        ): Observable<TeamResponse>
        @GET(EndPoints.searchEventByName)

        fun searchEventByName(
                @Query("e") name:String?
        ): Observable<EventResponse>


}