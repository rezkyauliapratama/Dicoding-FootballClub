package android.rezkyaulia.com.hellokotlin.ui.home

import android.rezkyaulia.com.hellokotlin.data.model.Team

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}