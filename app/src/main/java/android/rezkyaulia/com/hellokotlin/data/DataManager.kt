package android.rezkyaulia.com.hellokotlin.data

import android.rezkyaulia.com.hellokotlin.data.database.DatabaseRepository
import android.rezkyaulia.com.hellokotlin.data.network.ApiRepository
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Singleton
class DataManager @Inject constructor(){

    @Inject
    lateinit var db: DatabaseRepository

    @Inject
    lateinit var api: ApiRepository

    @Inject
    lateinit var networkApi: NetworkApi

}
