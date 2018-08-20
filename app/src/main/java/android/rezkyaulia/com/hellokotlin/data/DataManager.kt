package android.rezkyaulia.com.hellokotlin.data

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Singleton
class DataManager @Inject constructor(private val apiRepository: ApiRepository){

    fun getRepo(): ApiRepository{

        return apiRepository
    }
}
