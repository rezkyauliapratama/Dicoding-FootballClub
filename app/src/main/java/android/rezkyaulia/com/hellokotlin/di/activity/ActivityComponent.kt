package android.rezkyaulia.com.hellokotlin.di.activity

import android.rezkyaulia.com.hellokotlin.di.application.ApplicationComponent
import android.rezkyaulia.com.hellokotlin.ui.detail.event.DetailActivity
import android.rezkyaulia.com.hellokotlin.ui.detail.player.DetailPlayerActivity
import android.rezkyaulia.com.hellokotlin.ui.detail.team.DetailTeamActivity
import android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment.DetailTeamFragment
import android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment.DetailTeamPlayerFragment
import android.rezkyaulia.com.hellokotlin.ui.main.MainActivity
import android.rezkyaulia.com.hellokotlin.ui.main.event.EventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.favoriteevent.FavoriteEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.lastevent.LastEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent.NextEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.favorite.FavoriteFragment
import android.rezkyaulia.com.hellokotlin.ui.main.team.TeamFragment
import android.rezkyaulia.com.hellokotlin.ui.main.team.favoriteteam.FavoriteTeamFragment
import dagger.Component

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */
@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent{

    fun inject(activity: MainActivity)
    fun inject(activity: LastEventFragment)
    fun inject(nextEventFragment: NextEventFragment)
    fun inject(detailActivity: DetailActivity)
    fun inject(favoriteEventFragment: FavoriteEventFragment)
    fun inject(eventFragment: EventFragment)
    fun inject(teamFragment: TeamFragment)
    fun inject(detailTeamPlayerFragment: DetailTeamPlayerFragment)
    fun inject(detailTeamFragment: DetailTeamFragment)
    fun inject(detailTeamActivity: DetailTeamActivity)
    fun inject(detailPlayerActivity: DetailPlayerActivity)
    fun inject(favoriteTeamFragment: FavoriteTeamFragment)
    fun inject(favoriteFragment: FavoriteFragment)
}