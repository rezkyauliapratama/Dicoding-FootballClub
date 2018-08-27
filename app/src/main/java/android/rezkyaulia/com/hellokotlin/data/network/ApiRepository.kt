package android.rezkyaulia.com.hellokotlin.data.network

import android.rezkyaulia.com.hellokotlin.data.network.api.EventApi
import android.rezkyaulia.com.hellokotlin.data.network.api.TeamApi
import android.util.Log
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(){

    @Inject
    lateinit var eventApi: EventApi

    @Inject
    lateinit var teamApi: TeamApi
}