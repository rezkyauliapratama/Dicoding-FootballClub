package android.rezkyaulia.com.hellokotlin.ui.home

import android.rezkyaulia.com.hellokotlin.data.model.Team

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}