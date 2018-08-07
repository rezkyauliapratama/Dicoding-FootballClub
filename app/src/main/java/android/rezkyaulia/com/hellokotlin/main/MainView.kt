package android.rezkyaulia.com.hellokotlin.main

import android.rezkyaulia.com.hellokotlin.base.BaseView
import android.rezkyaulia.com.hellokotlin.data.Team

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */
interface MainView : BaseView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}