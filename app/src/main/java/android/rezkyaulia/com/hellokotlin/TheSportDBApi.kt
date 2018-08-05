package android.rezkyaulia.com.hellokotlin

import android.net.Uri

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */

object  TheSportDBApi{
    fun getTeams(league : String): String{
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
}