package android.rezkyaulia.com.hellokotlin.ui.home

import java.net.URL

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
class RepositoryApi{

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}