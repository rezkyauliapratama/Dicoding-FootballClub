package android.rezkyaulia.com.hellokotlin.data

import android.rezkyaulia.com.hellokotlin.data.database.DatabaseRepository
import android.rezkyaulia.com.hellokotlin.data.database.MyDatabaseOpenHelper
import android.rezkyaulia.com.hellokotlin.data.database.manage.ManageFavoriteTeam
import android.rezkyaulia.com.hellokotlin.data.network.ApiRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Singleton
class DataManager @Inject constructor(private val apiRepository: ApiRepository){

    @Inject
    lateinit var db: DatabaseRepository

    fun getRepo(): ApiRepository {

        return apiRepository
    }


}
