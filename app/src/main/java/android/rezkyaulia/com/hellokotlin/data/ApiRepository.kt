package android.rezkyaulia.com.hellokotlin.data

import android.util.Log
import java.net.URL
import javax.inject.Inject

class ApiRepository{

    fun doRequest(url: String) : String{

        return URL(url).readText()
    }

}