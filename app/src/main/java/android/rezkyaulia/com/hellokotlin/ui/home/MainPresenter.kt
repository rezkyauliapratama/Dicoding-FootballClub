package android.rezkyaulia.com.hellokotlin.ui.home

import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.ApiRepository
import android.rezkyaulia.com.hellokotlin.data.network.api.TheSportDBApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
class MainPresenter(private val view: MainView,
                    private val repositoryApi: RepositoryApi,
                    private val gson: Gson) {
    fun getTeamList(league: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(repositoryApi
                    .doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}