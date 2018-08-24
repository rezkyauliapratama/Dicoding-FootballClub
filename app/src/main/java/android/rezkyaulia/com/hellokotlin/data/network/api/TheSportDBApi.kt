package android.rezkyaulia.com.hellokotlin.data.network.api

import android.net.Uri
import android.rezkyaulia.com.hellokotlin.BuildConfig
import android.util.Log
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */

object  TheSportDBApi : AnkoLogger{
    fun getTeams(league : String): String{
        Log.e("sportsdb","getteams")
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l",league)
                .build()
                .toString()
    }

    fun getAllLeagues(): String{

        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("all_leagues.php")
                .build()
                .toString()
    }

    fun getPastEvent(leagueId : String): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id",leagueId)
                .build()
                .toString()
    }

    fun getNextEvent(leagueId : String): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id",leagueId)
                .build()
                .toString()
    }

    fun getSpecificTeam(teamId : String): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id",teamId)
                .build()
                .toString()
    }

}