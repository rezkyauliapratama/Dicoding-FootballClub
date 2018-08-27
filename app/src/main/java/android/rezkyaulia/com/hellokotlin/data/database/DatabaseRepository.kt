package android.rezkyaulia.com.hellokotlin.data.database

import android.rezkyaulia.com.hellokotlin.data.database.manage.ManageFavoriteEvent
import android.rezkyaulia.com.hellokotlin.data.database.manage.ManageFavoriteTeam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 26/8/18.
 */
@Singleton
class DatabaseRepository @Inject constructor() {

    @Inject
    lateinit var manageFavoriteEvent: ManageFavoriteEvent

}