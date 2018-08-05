package android.rezkyaulia.com.hellokotlin

/**
 * Created by Rezky Aulia Pratama on 5/8/18.
 */
interface MainView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}