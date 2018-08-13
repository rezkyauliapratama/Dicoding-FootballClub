package android.rezkyaulia.com.hellokotlin.data

import android.net.Uri
import android.rezkyaulia.com.hellokotlin.BuildConfig
import android.util.Log

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */

object  TheSportDBApi{
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
}