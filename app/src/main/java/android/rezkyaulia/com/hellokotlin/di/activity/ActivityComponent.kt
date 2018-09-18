package android.rezkyaulia.com.hellokotlin.di.activity

import android.rezkyaulia.com.hellokotlin.di.application.ApplicationComponent
import android.rezkyaulia.com.hellokotlin.ui.detail.DetailActivity
import android.rezkyaulia.com.hellokotlin.ui.main.MainActivity
import android.rezkyaulia.com.hellokotlin.ui.main.event.EventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.favoriteevent.FavoriteEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.lastevent.LastEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent.NextEventFragment
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
}