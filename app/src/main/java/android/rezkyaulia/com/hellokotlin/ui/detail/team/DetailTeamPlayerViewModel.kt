package android.rezkyaulia.com.hellokotlin.ui.detail.team

import android.arch.lifecycle.MutableLiveData
import android.database.sqlite.SQLiteConstraintException
import android.databinding.ObservableArrayMap
import android.databinding.ObservableParcelable
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.Player
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import java.lang.Exception
import javax.inject.Inject

class DetailTeamPlayerViewModel @Inject constructor(val dataManager: DataManager): BaseViewModel(){
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
    var nameLD : MutableLiveData<String> = MutableLiveData()
    var uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    val boolFavoriteLD: MutableLiveData<Boolean> = MutableLiveData()

    var team : Team ?= null
    fun retrieveData(idTeam: String) {
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(dataManager.networkApi.getSpecificTeam(idTeam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it ->
                    if (it.teams.isNotEmpty()){
                        team = it.teams[0]

                        teamLD.value = team
                        items.put(CLUB_NAME,team?.teamName)
                        items.put(CLUB_YEAR,team?.teamFormedYear)
                        items.put(CLUB_STADIUM,team?.teamStadium)
                        items.put(CLUB_DETAIL,team?.teamDescription)
                        items.put(CLUB_IMAGE,team?.teamBadge)

                        nameLD.value = team?.teamName
                    }

                    uiStatusLD.value = UiStatus.HIDE_LOADER


                }
                ,{t: Throwable? ->
                    error { "onrerror team : ${Gson().toJson(t)}" }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }
                ))

        compositeDisposable.add(dataManager.networkApi.getAllPlayersTeam(idTeam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it ->
                    if (it.player.isNotEmpty()){
                        playersLD.value = it.player
                    }

                }
                        ,{t: Throwable? ->
                    error { "onrerror player : ${Gson().toJson(t)}" }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }
                ))

        favoriteState(idTeam)

    }


    fun addToFavorite(){
        try {
            if (team != null){
                val b = dataManager.db.manageFavoriteTeam.insert(team)
                if (b)
                    uiStatusLD.value = UiStatus.FAVORITE_ADD
            }

        } catch (e: SQLiteConstraintException){
            error { "sqlite error : ${Gson().toJson(e)}" }
        }
    }

    fun removeFromFavorite(id : String){
        compositeDisposable.add(
                dataManager.db.manageFavoriteTeam.delete(id).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        doOnNext { it ->
                            if (it){
                                uiStatusLD.value = UiStatus.FAVORITE_REMOVE
                            }else{
                                uiStatusLD.value = UiStatus.FAVORITE_NOT_REMOVE

                            }
                        }.
                        subscribe())
    }



    private fun favoriteState(id : String){
        compositeDisposable.add(dataManager.db.manageFavoriteTeam.loadById(id).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                doOnNext { events ->
                    boolFavoriteLD.value = !events.isEmpty()
                }.
                subscribe())

    }
    
}