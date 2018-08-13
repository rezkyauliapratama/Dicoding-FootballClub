package android.rezkyaulia.com.hellokotlin.main

import android.rezkyaulia.com.hellokotlin.base.BasePresenter
import android.rezkyaulia.com.hellokotlin.data.ApiRepository
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.TheSportDBApi
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import org.jetbrains.anko.*
import java.util.logging.Logger
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */
class MainPresenter(view : MainView
                    ) : BasePresenter<MainView>(view){


    fun getTeamList(league: String){
        error { "test" }
        view.showLoading()
        error { "show loading" }

        if (dataManager.getRepo() != null) {
            Log.e("presenter","!= null")
        }else{
            Log.e("presenter","== null")

        }
        doAsync {

            val data = Gson().fromJson(dataManager.getRepo()
                    .doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                error {Gson().toJson(data.teams)}
                view.showTeamList(data.teams)
            }
        }
    }
}