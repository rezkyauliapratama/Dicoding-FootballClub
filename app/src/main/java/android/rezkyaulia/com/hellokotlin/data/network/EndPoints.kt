package android.rezkyaulia.com.hellokotlin.data.network

import android.rezkyaulia.com.hellokotlin.BuildConfig

/**
 * Created by Rezky Aulia Pratama on 11/9/18.
 */
object EndPoints{
    private const val base_path = "api/v1/json/${BuildConfig.TSDB_API_KEY}"

    const val eventPastLeague = "$base_path/eventspastleague.php"
    const val eventNextLeague = "$base_path/eventsnextleague.php"
    const val lookupTeam = "$base_path/lookupteam.php"
    const val lookupEvent = "$base_path/lookupevent.php"
}