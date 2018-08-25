package android.rezkyaulia.com.hellokotlin.ui.home

import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.api.TheSportDBApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
class TeamDetailPresenter(private val view: TeamDetailView,
                          private val repositoryApi: RepositoryApi,
                          private val gson: Gson) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(repositoryApi
                    .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetail(data.teams)
            }
        }
    }
}