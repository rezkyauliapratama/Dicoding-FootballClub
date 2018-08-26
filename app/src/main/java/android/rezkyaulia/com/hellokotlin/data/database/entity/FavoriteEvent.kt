package android.rezkyaulia.com.hellokotlin.data.database.entity

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
data class FavoriteEvent(val id: Long?, val eventId: String?, var eventDate: String?,
                         val eventHomeTeam: String?, val eventAwayTeam: String?, val eventHomeScore: String?,
                         val eventAwayScore: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE_EVENT"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_HOME_TEAM: String = "EVENT_HOME_TEAM"
        const val EVENT_AWAY_TEAM: String = "EVENT_AWAY_TEAM"
        const val EVENT_HOME_SCORE: String = "EVENT_HOME_SCORE"
        const val EVENT_AWAY_SCORE: String = "EVENT_AWAY_SCORE"
    }
}