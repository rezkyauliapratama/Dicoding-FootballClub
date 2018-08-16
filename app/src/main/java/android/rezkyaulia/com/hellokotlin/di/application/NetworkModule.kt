package android.rezkyaulia.com.hellokotlin.di.application

import android.rezkyaulia.com.hellokotlin.data.ApiRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Module
class NetworkModule{

    @Provides
    fun providesNetwork() : ApiRepository{
        return ApiRepository()
    }

}