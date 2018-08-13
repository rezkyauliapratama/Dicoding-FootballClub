package android.rezkyaulia.com.hellokotlin.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */
data class Team(
        @SerializedName("idTeam")
        var teamId : String? = null,

        @SerializedName("strTeam")
        var teamName : String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge : String? = null

)