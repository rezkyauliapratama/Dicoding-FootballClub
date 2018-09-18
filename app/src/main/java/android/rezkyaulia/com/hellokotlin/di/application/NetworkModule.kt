package android.rezkyaulia.com.hellokotlin.di.application

import android.content.Context
import android.rezkyaulia.com.hellokotlin.BuildConfig
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Module
class NetworkModule{

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL).build()
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    // This function need Retrofit object which we are passing in argument.
    // We will not create Retrofit object in this function.
    // Instead it will be injected/provided by Dagger2.
    // Dagger2 will get Retrofit object from provideRetrofit function declared above.

    @Provides
    fun provideNetworkApi(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }
}