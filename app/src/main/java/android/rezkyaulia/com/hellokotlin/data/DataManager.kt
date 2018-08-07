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
        if (apiRepository != null){
            Log.e("datamanager", "repo != null")
        }else{
            Log.e("datamanager", "repo == null")

        }
        return apiRepository
    }
}
