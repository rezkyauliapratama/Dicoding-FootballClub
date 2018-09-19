package android.rezkyaulia.com.hellokotlin.ui.detail.team

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayMap
import android.databinding.ObservableParcelable
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.model.Player
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import java.lang.Exception
import javax.inject.Inject

class DetailTeamPlayerViewModel @Inject constructor(val api: NetworkApi): BaseViewModel(){
    companion object {
        const val CLUB_NAME = "CLUB_NAME"
        const val CLUB_YEAR = "CLUB_YEAR"
        const val CLUB_STADIUM = "CLUB_STADIUM"
        const val CLUB_DETAIL = "CLUB_DETAIL"
        const val CLUB_IMAGE = "CLUB_IMAGE"
    }

    var items: ObservableArrayMap<String, String> = ObservableArrayMap()

    var playersLD : MutableLiveData<List<Player>> = MutableLiveData()
    var teamLD : MutableLiveData<Team> = MutableLiveData()
    var playerIdLD : MutableLiveData<String> = MutableLiveData()

    fun retrieveData(idTeam: String) {
        compositeDisposable.add(api.getSpecificTeam(idTeam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it ->
                    error{"teamresponse : "+ Gson().toJson(it)}
                    if (it.teams.isNotEmpty()){
                        val team = it.teams[0]

                        teamLD.value = team
                        items.put(CLUB_NAME,team.teamName)
                        items.put(CLUB_YEAR,team.teamFormedYear)
                        items.put(CLUB_STADIUM,team.teamStadium)
                        items.put(CLUB_DETAIL,team.teamDescription)
                        items.put(CLUB_IMAGE,team.teamBadge)
                    }

                }
                ,{t: Throwable? ->
                    error { "onrerror team : ${Gson().toJson(t)}" }
                }
                ))

        compositeDisposable.add(api.getAllPlayersTeam(idTeam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it ->
                    error{"player : "+ Gson().toJson(it)}
                    if (it.player.isNotEmpty()){
                        playersLD.value = it.player
                    }

                }
                        ,{t: Throwable? ->
                    error { "onrerror player : ${Gson().toJson(t)}" }
                }
                ))
    }

    
}