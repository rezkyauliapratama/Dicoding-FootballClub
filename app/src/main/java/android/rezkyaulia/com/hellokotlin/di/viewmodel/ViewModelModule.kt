package android.rezkyaulia.com.hellokotlin.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.rezkyaulia.com.hellokotlin.ui.detail.event.DetailViewModel
import android.rezkyaulia.com.hellokotlin.ui.detail.player.PlayerViewModel
import android.rezkyaulia.com.hellokotlin.ui.detail.team.DetailTeamPlayerViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.MainViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.event.EventViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.event.favoriteevent.FavoriteEventViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.event.lastevent.LastEventViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent.NextEventViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.favorite.FavoriteViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.team.TeamViewModel
import android.rezkyaulia.com.hellokotlin.ui.main.team.favoriteteam.FavoriteTeamViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */
@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel : MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LastEventViewModel::class)
    abstract fun bindLastEventViewModel(lastEventViewModel: LastEventViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NextEventViewModel::class)
    abstract fun bindNextEventViewModel(nextEventViewModel: NextEventViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FavoriteEventViewModel::class)
    abstract fun bindFavoriteEventViewModel(favoriteEventViewModel: FavoriteEventViewModel) : ViewModel


     @Binds
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    abstract fun bindEventViewModel(eventViewModel: EventViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TeamViewModel::class)
    abstract fun bindTeamViewModel(teamViewModel: TeamViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailTeamPlayerViewModel::class)
    abstract fun bindDetailPlayerViewModel(detailTeamPlayerViewModel: DetailTeamPlayerViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    abstract fun bindPlayerViewModel(playerViewModel: PlayerViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteTeamViewModel::class)
    abstract fun bindFavoriteTeamViewModel(favoriteTeamViewModel: FavoriteTeamViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(favoriteViewModel: FavoriteViewModel) : ViewModel

}