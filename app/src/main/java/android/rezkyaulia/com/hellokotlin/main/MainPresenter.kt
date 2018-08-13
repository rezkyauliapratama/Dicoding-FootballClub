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
import com.androidnetworking.error.ANError
import org.json.JSONArray
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.ParsedRequestListener


/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */
class MainPresenter(view : MainView
                    ) : BasePresenter<MainView>(view){


    fun getTeamList(league: String){
        view.showLoading()


        AndroidNetworking.get(TheSportDBApi.getTeams(league))
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(TeamResponse::class.java, object : ParsedRequestListener<TeamResponse>{
                    override fun onResponse(response: TeamResponse) {
                        view.hideLoading()
                        error {Gson().toJson(response.teams)}
                        view.showTeamList(response.teams)
                    }

                    override fun onError(anError: ANError) {
                        error { Gson().toJson(anError) }
                    }

                });


    }
}